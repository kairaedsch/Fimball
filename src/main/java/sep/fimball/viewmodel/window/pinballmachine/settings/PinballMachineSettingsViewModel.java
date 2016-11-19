package sep.fimball.viewmodel.window.pinballmachine.settings;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import sep.fimball.model.blueprint.pinballmachine.PinballMachine;
import sep.fimball.viewmodel.window.WindowType;
import sep.fimball.viewmodel.window.WindowViewModel;
import sep.fimball.viewmodel.window.mainmenu.MainMenuViewModel;
import sep.fimball.viewmodel.window.pinballmachine.editor.PinballMachineEditorViewModel;

/**
 * Das PinballMachineEditorViewModel stellt der View Daten über einen Flipperautomaten zu Verfügung und ermöglicht es u.a. Änderungen an diesem zu speichern oder zu löschen.
 */
public class PinballMachineSettingsViewModel extends WindowViewModel
{
    /**
     * Der Flipperautomat, der bearbeitet wird.
     */
    private PinballMachine pinballMachine;


    /**
     * Der Name des Flipperautomaten.
     */
    private StringProperty machineName;

    /**
     * Erstellt ein neues PinballMachineSettingsViewModel.
     *
     * @param pinballMachine Der Flipperautomat, der bearbeitet wird.
     */
    public PinballMachineSettingsViewModel(PinballMachine pinballMachine)
    {
        super(WindowType.TABLE_SETTINGS);
        this.pinballMachine = pinballMachine;

        machineName = new SimpleStringProperty();
        machineName.bindBidirectional(pinballMachine.nameProperty());
    }

    /**
     * Erteilt dem Model den Befehl, die Änderungen am Flipperautomaten zu speichern.
     */
    public void savePinballMachine()
    {
        pinballMachine.setName(machineName.getValue());
        pinballMachine.saveToDisk();
        sceneManager.setWindow(new MainMenuViewModel());
    }

    /**
     * Erteilt dem Model den Befehl, den Flipperautomat zu löschen.
     */
    public void deletePinballMachine()
    {
        pinballMachine.deleteFromDisk();
        sceneManager.setWindow(new MainMenuViewModel());
    }

    /**
     * Führt den Benutzer zurück ins Hauptmenü.
     */
    public void exitWindowToMainMenu()
    {
        sceneManager.setWindow(new MainMenuViewModel());
    }

    /**
     * Führt den Benutzer ins Automat-Editor-Fenster, wo er den Flipperautomat und seine Flipperautomat-Elemente bearbeiten kann.
     */
    public void exitWindowToEditor()
    {
        sceneManager.setWindow(new PinballMachineEditorViewModel(pinballMachine));
    }

    public StringProperty machineNameProperty() {
        return machineName;
    }
}
