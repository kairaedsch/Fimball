package sep.fimball.view.window.pinballmachine.settings;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import sep.fimball.view.window.WindowView;
import sep.fimball.viewmodel.window.pinballmachine.settings.PinballMachineSettingsViewModel;

/**
 * Created by kaira on 01.11.2016.
 */
public class PinballMachineSettingsView extends WindowView<PinballMachineSettingsViewModel>
{
    @FXML
    private TextField tableName;

    private PinballMachineSettingsViewModel pinballMachineSettingsViewModel;

    @Override
    public void setViewModel(PinballMachineSettingsViewModel pinballMachineSettingsViewModel)
    {
        this.pinballMachineSettingsViewModel = pinballMachineSettingsViewModel;
    }

    @FXML
    private void editClicked()
    {
        pinballMachineSettingsViewModel.exitWindowToEditor();
    }

    @FXML
    private void menuClicked()
    {
        pinballMachineSettingsViewModel.exitWindowToMainMenu();
    }

    @FXML
    private void saveClicked()
    {
        pinballMachineSettingsViewModel.savePinballMachine();
    }

    @FXML
    private void deleteClicked()
    {
        pinballMachineSettingsViewModel.deletePinballMachine();
    }
}
