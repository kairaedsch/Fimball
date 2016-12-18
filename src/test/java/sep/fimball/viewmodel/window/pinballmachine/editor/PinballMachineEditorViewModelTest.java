package sep.fimball.viewmodel.window.pinballmachine.editor;

import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.scene.input.MouseButton;
import org.junit.Ignore;
import org.junit.Test;
import sep.fimball.general.data.Vector2;
import sep.fimball.model.blueprint.base.BaseElementManager;
import sep.fimball.model.blueprint.pinballmachine.PinballMachine;
import sep.fimball.model.blueprint.pinballmachine.PlacedElement;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Tests für die Klasse PinballMachineEditorViewModel.
 */
@Ignore
public class PinballMachineEditorViewModelTest
{
    @Test
    public void draggedTest()
    {
        PinballMachine pinballMachineMock = mock(PinballMachine.class);
        PlacedElement ball = new PlacedElement(BaseElementManager.getInstance().getElement("ball"), new Vector2(), 0, 0, 0);

        ListProperty<PlacedElement> elementList = new SimpleListProperty<>(FXCollections.observableArrayList());
        elementList.add(ball);
        when(pinballMachineMock.elementsProperty()).thenReturn(elementList);
        when(pinballMachineMock.nameProperty()).thenReturn(new SimpleStringProperty(""));

        PinballMachineEditorViewModel pinballMachineEditorViewModel = new PinballMachineEditorViewModel(pinballMachineMock);

        pinballMachineEditorViewModel.dragged(0, 0, 1, 1, new Vector2(0, 0), MouseButton.MIDDLE);

        assertEquals(pinballMachineEditorViewModel.cameraPositionProperty().get().getX(), -0.09, 0.01);
        assertEquals(pinballMachineEditorViewModel.cameraPositionProperty().get().getY(), -0.09, 0.01);
    }
}
