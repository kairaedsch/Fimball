package sep.fimball.viewmodel.window.pinballmachine.editor;

import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import org.junit.Before;
import org.junit.Test;
import sep.fimball.general.data.Vector2;
import sep.fimball.model.blueprint.base.BaseElementManager;
import sep.fimball.model.blueprint.pinballmachine.PinballMachine;
import sep.fimball.model.blueprint.pinballmachine.PlacedElement;

import java.util.Optional;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.junit.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Tests für die Klasse PinballMachineEditorViewModel.
 */
public class PinballMachineEditorViewModelTest
{
    private PinballMachine pinballMachineMock;
    private PlacedElement ball;
    private PinballMachineEditorViewModel pinballMachineEditorViewModel;

    @Before
    public void initialize()
    {
        pinballMachineMock = mock(PinballMachine.class);
        ball = new PlacedElement(BaseElementManager.getInstance().getElement("ball"), new Vector2(), 0, 1, 0);

        ListProperty<PlacedElement> elementList = new SimpleListProperty<>(FXCollections.observableArrayList());
        elementList.add(ball);
        when(pinballMachineMock.elementsProperty()).thenReturn(elementList);
        when(pinballMachineMock.nameProperty()).thenReturn(new SimpleStringProperty(""));

        pinballMachineEditorViewModel = new PinballMachineEditorViewModel(pinballMachineMock);
    }

    @Test
    public void cameraDraggedTest()
    {
        pinballMachineEditorViewModel.dragged(0, 0, 1, 1, new Vector2(0, 0), MouseButton.MIDDLE);

        assertEquals(pinballMachineEditorViewModel.cameraPositionProperty().get().getX(), -0.09, 0.01);
        assertEquals(pinballMachineEditorViewModel.cameraPositionProperty().get().getY(), -0.09, 0.01);
    }

    @Test
    public void selectSingleElementTest()
    {

        final Vector2 pos = new Vector2(0, 0);
        when(pinballMachineMock.getElementAt(pos)).thenReturn(Optional.of(ball));

        MouseEvent mouseEventMock = mock(MouseEvent.class);
        when(mouseEventMock.getButton()).thenReturn(MouseButton.PRIMARY);

        pinballMachineEditorViewModel.mousePressedOnCanvas(mouseEventMock, pos);

        assertThat(pinballMachineEditorViewModel.getSelection().size(), is(1));
        assertThat(pinballMachineEditorViewModel.getSelection().get(0), equalTo(ball));
    }

    @Test
    public void deselectElementTest()
    {
        final Vector2 pos = new Vector2(0, 0);
        when(pinballMachineMock.getElementAt(pos)).thenReturn(Optional.empty());

        MouseEvent mouseEventMock = mock(MouseEvent.class);
        when(mouseEventMock.getButton()).thenReturn(MouseButton.PRIMARY);

        // Ein Element wird manuell zur Auswahl hinzugefügt.
        ListProperty<PlacedElement> selection = (ListProperty<PlacedElement>) pinballMachineEditorViewModel.getSelection();
        selection.add(ball);

        pinballMachineEditorViewModel.mouseEnteredCanvas(pos);
        pinballMachineEditorViewModel.mouseReleased(mouseEventMock);

        assertThat(pinballMachineEditorViewModel.getSelection().isEmpty(), is(true));
    }

    @Test
    public void placeElementTest()
    {
        final Vector2 pos = new Vector2(0, 0);
        when(pinballMachineMock.getElementAt(pos)).thenReturn(Optional.of(ball));

        MouseEvent mouseEventMock = mock(MouseEvent.class);
        when(mouseEventMock.getButton()).thenReturn(MouseButton.PRIMARY);

        pinballMachineEditorViewModel.mouseEnteredCanvas(pos);
        pinballMachineEditorViewModel.mousePressedOnCanvas(mouseEventMock, pos);
        pinballMachineEditorViewModel.mouseReleased(mouseEventMock);

        verify(pinballMachineMock).addElement(ball);
    }

    @Test
    public void removeElementTest()
    {
        KeyEvent keyEventMock = mock(KeyEvent.class);
        when(keyEventMock.getCode()).thenReturn(KeyCode.DELETE);
        when(keyEventMock.getEventType()).thenReturn(KeyEvent.KEY_PRESSED);

        // Ein Element wird manuell zur Auswahl hinzugefügt.
        ListProperty<PlacedElement> selection = (ListProperty<PlacedElement>) pinballMachineEditorViewModel.getSelection();
        selection.add(ball);

        pinballMachineEditorViewModel.handleKeyEvent(keyEventMock);

        verify(pinballMachineMock).removeElement(ball);
    }
}
