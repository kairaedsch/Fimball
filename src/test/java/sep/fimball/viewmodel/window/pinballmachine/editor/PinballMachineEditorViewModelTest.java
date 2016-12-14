package sep.fimball.viewmodel.window.pinballmachine.editor;

import javafx.beans.property.SimpleStringProperty;
import org.junit.Ignore;
import org.junit.Test;
import sep.fimball.model.blueprint.pinballmachine.PinballMachine;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@Ignore
public class PinballMachineEditorViewModelTest
{
    @Test
    public void draggedTest()
    {
        PinballMachine pinballMachineMock = mock(PinballMachine.class);
        when(pinballMachineMock.nameProperty()).thenReturn(new SimpleStringProperty(""));

        PinballMachineEditorViewModel pinballMachineEditorViewModel = new PinballMachineEditorViewModel(pinballMachineMock);


    }
}
