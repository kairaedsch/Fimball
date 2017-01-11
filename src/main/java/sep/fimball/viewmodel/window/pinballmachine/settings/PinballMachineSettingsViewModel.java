package sep.fimball.viewmodel.window.pinballmachine.settings;

import javafx.beans.property.ReadOnlyStringProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import sep.fimball.model.blueprint.pinballmachine.PinballMachine;
import sep.fimball.viewmodel.dialog.message.MessageViewModel;
import sep.fimball.viewmodel.dialog.question.QuestionViewModel;
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
     * Der Pfad zum Vorschaubild des Automaten.
     */
    private StringProperty imagePathProperty;

    /**
     * Erstellt ein neues PinballMachineSettingsViewModel.
     *
     * @param pinballMachine Der Flipperautomat, der bearbeitet wird.
     */
    public PinballMachineSettingsViewModel(PinballMachine pinballMachine)
    {
        super(WindowType.MACHINE_SETTINGS);
        this.pinballMachine = pinballMachine;

        machineName = new SimpleStringProperty();
        machineName.bindBidirectional(pinballMachine.nameProperty());
        imagePathProperty = new SimpleStringProperty();
        imagePathProperty.bind(pinballMachine.absolutePreviewImagePathProperty());
    }

    /**
     * Gibt den Pfad zum Vorschaubild des Automaten zurück.
     *
     * @return Der Pfad zum Vorschaubild des Automaten.
     */
    public ReadOnlyStringProperty imagePathProperty()
    {
        return imagePathProperty;
    }

    /**
     * Erteilt dem Model den Befehl, die Änderungen am Flipperautomaten zu speichern.
     */
    public void savePinballMachine()
    {
        boolean success = pinballMachine.saveToDisk();
        String titleKey, messageKey;
        if(success)
        {
            titleKey = "editor.settings.saveMessage.success.title.key";
            messageKey = "editor.settings.saveMessage.success.message.key";
        }
        else
        {
            titleKey = "editor.settings.saveMessage.fail.title.key";
            messageKey = "editor.settings.saveMessage.fail.message.key";
        }
        sceneManager.pushDialog(new MessageViewModel(titleKey, messageKey));
    }

    /**
     * Erteilt dem Model den Befehl, den Flipperautomat zu löschen, falls der Nutzer den Dialog annimmt.
     */
    public void deletePinballMachine()
    {
        sceneManager.pushDialog(new QuestionViewModel("mainmenu.exitQuestion.title.key", "mainmenu.exitQuestion.message.key", () ->
        {
            pinballMachine.deleteFromDisk();
            sceneManager.setWindow(new MainMenuViewModel());
        }));
    }

    /**
     * Führt den Benutzer zurück ins Hauptmenü.
     */
    public void exitWindowToMainMenu()
    {
        if(machineName.get().isEmpty()) {
            sceneManager.pushDialog(new MessageViewModel("editor.settings.emptyNameTitle.key", "editor.settings.emptyNameMessage.key"));
        } else
        {
            pinballMachine.unloadElements();
            sceneManager.setWindow(new MainMenuViewModel());
        }
    }

    /**
     * Führt den Benutzer ins Automat-Editor-Fenster, wo er den Flipperautomat und seine Flipperautomat-Elemente bearbeiten kann.
     */
    public void exitWindowToEditor()
    {
        if(machineName.get().isEmpty()) {
            sceneManager.pushDialog(new MessageViewModel("editor.settings.emptyNameTitle.key", "editor.settings.emptyNameMessage.key"));
        } else
        {
            sceneManager.setWindow(new PinballMachineEditorViewModel(pinballMachine));
        }
    }

    /**
     * Stellt der View den Namen des Automaten zur Verfügung.
     *
     * @return Der Name des Automaten.
     */
    public StringProperty machineNameProperty()
    {
        return machineName;
    }
}
