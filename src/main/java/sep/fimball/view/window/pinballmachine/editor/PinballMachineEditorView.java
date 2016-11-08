package sep.fimball.view.window.pinballmachine.editor;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import sep.fimball.view.window.WindowView;
import sep.fimball.viewmodel.window.pinballmachine.editor.PinballMachineEditorViewModel;

/**
 * Created by kaira on 01.11.2016.
 */
public class PinballMachineEditorView extends WindowView<PinballMachineEditorViewModel>
{

    private PinballMachineEditorViewModel pinballMachineEditorViewModel;

      @Override
    public void setViewModel(PinballMachineEditorViewModel pinballMachineEditorViewModel)
    {
        this.pinballMachineEditorViewModel = pinballMachineEditorViewModel;
    }

    @FXML
    private void playClicked() {

    }

    @FXML
    private void settingsClicked() {

    }

}
