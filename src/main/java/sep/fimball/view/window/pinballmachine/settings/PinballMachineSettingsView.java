package sep.fimball.view.window.pinballmachine.settings;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import sep.fimball.view.window.WindowView;
import sep.fimball.viewmodel.window.pinballmachine.settings.PinballMachineSettingsViewModel;

/**
 * Die PinballMachineSettingsView ist für die Darstellung der Editoreinstellungen zuständig und ermöglicht dem Nutzer u.a. Änderungen am Flipperautomaten zu speichern.
 */
public class PinballMachineSettingsView extends WindowView<PinballMachineSettingsViewModel>
{
    /**
     * Zeigt den Namen des Flipperautomaten an und ermöglicht dessen Änderung.
     */
    @FXML
    private TextField tableName;

    /**
     * Das zur PinballMachineSettingsView gehörende PinballMachineSettingsViewModel.
     */
    private PinballMachineSettingsViewModel pinballMachineSettingsViewModel;

    /**
     * Setzt das zur PinballMachineSettingsView gehörende PinballMachineSettingsViewModel.
     *
     * @param pinballMachineSettingsViewModel Das zu setzende PinballMachineSettingsViewModel.
     */
    @Override
    public void setViewModel(PinballMachineSettingsViewModel pinballMachineSettingsViewModel)
    {
        this.pinballMachineSettingsViewModel = pinballMachineSettingsViewModel;
        tableName.textProperty().bindBidirectional(pinballMachineSettingsViewModel.machineNameProperty());
    }

    /**
     * Benachrichtigt das PinballMachineSelectorSubViewModel, dass der Nutzer die PinballMachine-Elemente editieren möchte.
     */
    @FXML
    private void editClicked()
    {
        pinballMachineSettingsViewModel.exitWindowToEditor();
    }

    /**
     * Benachrichtigt das PinballMachineSelectorSubViewModel, dass der Nutzer zurück zum Hauptmenü wechseln möchte.
     */
    @FXML
    private void menuClicked()
    {
        pinballMachineSettingsViewModel.exitWindowToMainMenu();
    }

    /**
     * Benachrichtigt das PinballMachineSelectorSubViewModel, dass der Nutzer den aktuellen Stand der PinballMachine speichern möchte.
     */
    @FXML
    private void saveClicked()
    {
        pinballMachineSettingsViewModel.savePinballMachine();
    }

    /**
     * Benachrichtigt das PinballMachineSelectorSubViewModel, dass der Nutzer die PinballMachine löschen möchte.
     */
    @FXML
    private void deleteClicked()
    {
        pinballMachineSettingsViewModel.deletePinballMachine();
    }



}
