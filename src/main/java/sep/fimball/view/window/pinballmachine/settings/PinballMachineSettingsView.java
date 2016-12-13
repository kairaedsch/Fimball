package sep.fimball.view.window.pinballmachine.settings;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import sep.fimball.general.data.DesignConfig;
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
     * Das Vorschaubild des Automaten.
     */
    @FXML
    private Pane previewImage;

    /**
     * Das zur PinballMachineSettingsView gehörende PinballMachineSettingsViewModel.
     */
    private PinballMachineSettingsViewModel pinballMachineSettingsViewModel;

    @Override
    public void setViewModel(PinballMachineSettingsViewModel pinballMachineSettingsViewModel)
    {
        this.pinballMachineSettingsViewModel = pinballMachineSettingsViewModel;
        tableName.textProperty().bindBidirectional(pinballMachineSettingsViewModel.machineNameProperty());
        previewImage.styleProperty().bind(DesignConfig.backgroundImageCss(pinballMachineSettingsViewModel.imagePathProperty()));
    }

    /**
     * Benachrichtigt das {@code pinballMachineSettingsViewModel}, dass der Nutzer den Flipperautomaten editieren möchte.
     */
    @FXML
    private void editClicked()
    {
        pinballMachineSettingsViewModel.exitWindowToEditor();
    }

    /**
     * Benachrichtigt das {@code pinballMachineSettingsViewModel}, dass der Nutzer zurück zum Hauptmenü wechseln möchte.
     */
    @FXML
    private void menuClicked()
    {
        pinballMachineSettingsViewModel.exitWindowToMainMenu();
    }

    /**
     * Benachrichtigt das {@code pinballMachineSettingsViewModel}, dass der Nutzer den aktuellen Stand der PinballMachine speichern möchte.
     */
    @FXML
    private void saveClicked()
    {
        pinballMachineSettingsViewModel.savePinballMachine();
    }

    /**
     * Benachrichtigt das {@code pinballMachineSettingsViewModel}, dass der Nutzer die PinballMachine löschen möchte.
     */
    @FXML
    private void deleteClicked()
    {
        pinballMachineSettingsViewModel.deletePinballMachine();
    }


}
