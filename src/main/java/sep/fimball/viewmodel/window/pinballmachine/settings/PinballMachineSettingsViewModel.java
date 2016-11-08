package sep.fimball.viewmodel.window.pinballmachine.settings;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import sep.fimball.model.blueprint.PinballMachine;
import sep.fimball.viewmodel.window.WindowType;
import sep.fimball.viewmodel.window.WindowViewModel;
import sep.fimball.viewmodel.window.mainmenu.MainMenuViewModel;
import sep.fimball.viewmodel.window.pinballmachine.editor.PinballMachineEditorViewModel;

/**
 * Das PinballMachineEditorViewModel stellt der View Daten über einen FlipperAutomaten zu Verfügung und ermöglicht es u.a. Änderungen an diesen zu speichern oder zu löschen.
 */
public class PinballMachineSettingsViewModel extends WindowViewModel
{
    /**
     * Der FlipperAutomat, welcher editiert wird.
     */
    private PinballMachine pinballMachine;


    /**
     * Der Name das FlipperAutomaten.
     */
    private StringProperty machineName;

    /**
     * Erstellt ein neues PinballMachineSettingsViewModel.
     * @param pinballMachine
     */
    public PinballMachineSettingsViewModel(PinballMachine pinballMachine)
    {
        super(WindowType.TABLE_SETTINGS);
        this.pinballMachine = pinballMachine;

        machineName = new SimpleStringProperty();
        machineName.bind(pinballMachine.nameProperty());
    }

    /**
     * Erteilt dem Model den Befehl, die Änderungen an dem Flipperautomat zu speichern.
     */
    public void saveClicked()
    {
        //TODO save
    }

    /**
     * Erteilt dem Model den Befehl, den Flipperautomat zu löschen.
     */
    public void deleteClicked()
    {
        sceneManager.setWindow(new MainMenuViewModel());
        // TODO delete
    }

    /**
     * Führt den Benutzer zurück ins Hauptmenu.
     */
    public void menuClicked()
    {
        sceneManager.setWindow(new MainMenuViewModel());
    }

    /**
     * Führt den Benutzer ins Automat-Editor-Fenster, wo er den Flipperautomat und seine Flipperautomat-Elemente editieren kann.
     */
    public void editClicked()
    {
        sceneManager.setWindow(new PinballMachineEditorViewModel(pinballMachine));
    }
}
