package sep.fimball.viewmodel.window.pinballmachine.editor;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import org.junit.Ignore;
import org.junit.Test;
import sep.fimball.model.blueprint.base.BaseElement;
import sep.fimball.model.blueprint.pinballmachine.PinballMachine;
import sep.fimball.model.blueprint.pinballmachine.PlacedElement;
import sep.fimball.model.media.BaseMediaElement;

import java.util.Optional;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@Ignore
public class SelectedElementSubViewModelTest
{/*
    @Test
    public void removeTest()
    {
        // Initialisierung
        PinballMachine pinballMachineMock = mock(PinballMachine.class);
        PlacedElement placedElementMock = mock(PlacedElement.class);

        BaseElement baseElementMock = mock(BaseElement.class);
        BaseMediaElement baseMediaElementMock = mock(BaseMediaElement.class);
        when(baseMediaElementMock.getName()).thenReturn("");
        when(baseMediaElementMock.getDescription()).thenReturn("");

        when(baseElementMock.getMedia()).thenReturn(baseMediaElementMock);
        when(placedElementMock.getBaseElement()).thenReturn(baseElementMock);

        when(placedElementMock.multiplierProperty()).thenReturn(new SimpleDoubleProperty(0));
        when(placedElementMock.pointsProperty()).thenReturn(new SimpleIntegerProperty(0));

        SelectedElementSubViewModel selectedElementSubViewModel= new SelectedElementSubViewModel(pinballMachineMock);

        // remove() testen
        selectedElementSubViewModel.setPlacedElement(Optional.of(placedElementMock));
        selectedElementSubViewModel.remove();

        // Auswertung
        verify(pinballMachineMock).removeElement(placedElementMock);
    }

    @Test
    public void rotateClockwiseTest()
    {
        // Initialisierung
        PinballMachine pinballMachineMock = mock(PinballMachine.class);
        PlacedElement placedElementMock = mock(PlacedElement.class);

        BaseElement baseElementMock = mock(BaseElement.class);
        BaseMediaElement baseMediaElementMock = mock(BaseMediaElement.class);
        when(baseMediaElementMock.getName()).thenReturn("");
        when(baseMediaElementMock.getDescription()).thenReturn("");

        when(baseElementMock.getMedia()).thenReturn(baseMediaElementMock);
        when(placedElementMock.getBaseElement()).thenReturn(baseElementMock);

        when(placedElementMock.multiplierProperty()).thenReturn(new SimpleDoubleProperty(0));
        when(placedElementMock.pointsProperty()).thenReturn(new SimpleIntegerProperty(0));

        SelectedElementSubViewModel selectedElementSubViewModel= new SelectedElementSubViewModel(pinballMachineMock);

        // remove() testen
        selectedElementSubViewModel.setPlacedElement(Optional.of(placedElementMock));
        selectedElementSubViewModel.rotateClockwise();

        // Auswertung
        verify(placedElementMock).rotateClockwise();
    }
*/}
