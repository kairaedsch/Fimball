package sep.fimball.viewmodel.window.pinballmachine.editor;

import sep.fimball.model.blueprint.pinballmachine.PinballMachine;
import sep.fimball.model.game.EditorSession;
import sep.fimball.model.game.GameSession;
import sep.fimball.viewmodel.dialog.message.MessageViewModel;
import sep.fimball.viewmodel.dialog.question.QuestionViewModel;
import sep.fimball.viewmodel.pinballcanvas.PinballCanvasEditorViewModel;
import sep.fimball.viewmodel.window.game.GameViewModel;
import sep.fimball.viewmodel.window.mainmenu.MainMenuViewModel;

/**
 * Verwaltet die EditorSession aus dem Model.
 */
public class EditorSessionSubViewModel
{
    /**
     * Die zugehörige GameSession.
     */
    private EditorSession editorSession;

    /**
     * Der Flipperautomat, welcher editiert wird.
     */
    private PinballMachine pinballMachine;

    /**
     * Das PinballCanvasViewModel des angezeigten Spielfelds.
     */
    private PinballCanvasEditorViewModel pinballCanvasViewModel;

    /**
     * Das zugehörige PinballMachineEditorViewModel.
     */
    private PinballMachineEditorViewModel editorViewModel;

    /**
     * Erzeugt eine neue Instanz von EditorSessionSubViewModel.
     * @param viewModel Das Haupt-Viewmodel des Editors.
     * @param machine Die im Editor bearbeitete PinballMachine.
     */
    public EditorSessionSubViewModel(PinballMachineEditorViewModel viewModel, PinballMachine machine)
    {
        editorViewModel = viewModel;
        pinballMachine = machine;
        editorSession = new EditorSession(pinballMachine);
        pinballCanvasViewModel = new PinballCanvasEditorViewModel(editorSession, viewModel);
    }

    /**
     * Führt den Benutzer zu dem Spielfenster, wo der gerade vom Nutzer bearbeitete Flipper-Automat getestet werden
     * kann.
     */
    public void startPinballMachine()
    {
        editorSession.stopUpdateLoop();
        editorSession.stopAutoSaveLoop(false);
        editorViewModel.getSceneManagerViewModel().setWindow(new GameViewModel(GameSession.generateGameSession(pinballMachine, new String[]{"Editor Player"}, true)));
    }

    /**
     * Führt den Benutzer zu dem Automateneinstellungsfenster und speichert den Automaten.
     */
    public void exitToMainMenu()
    {
        editorViewModel.getSceneManagerViewModel().pushDialog(new QuestionViewModel("editor.settings.exitToMainMenuQuestion", () ->
        {
            editorSession.stopUpdateLoop();
            editorSession.stopAutoSaveLoop(true);
            pinballMachine.unloadElements();
            editorViewModel.getSceneManagerViewModel().setWindow(new MainMenuViewModel(pinballMachine));
        }));
    }

    /**
     * Erteilt dem Model den Befehl, die Änderungen am Flipperautomaten zu speichern.
     */
    public void savePinballMachine()
    {
        boolean success = pinballMachine.saveToDisk(false);
        if(success) {pinballMachine.savePreviewImage(pinballCanvasViewModel.createScreenshot());}
        editorViewModel.getSceneManagerViewModel().pushDialog(new MessageViewModel("editor.settings.saveMessage." + (success ? "success" : "fail")));
    }

    /**
     * Erteilt dem Model den Befehl, den Flipperautomat zu löschen, falls der Nutzer den Dialog annimmt.
     */
    public void deletePinballMachine()
    {
        editorViewModel.getSceneManagerViewModel().pushDialog(new QuestionViewModel("editor.settings.deleteQuestion", () ->
        {
            boolean success = pinballMachine.deleteFromDisk();
            editorViewModel.getSceneManagerViewModel().pushDialog(new MessageViewModel("editor.settings.deleteMessage." + (success ? "success" : "fail")));
            if (success)
                editorViewModel.getSceneManagerViewModel().setWindow(new MainMenuViewModel());
        }));
    }

    /**
     * Stellt der View das PinballCanvasViewModel des angezeigten Spielfelds zur Verfügung.
     *
     * @return Das PinballCanvasViewModel des angezeigten Spielfelds.
     */
    public PinballCanvasEditorViewModel getPinballCanvasViewModel()
    {
        return pinballCanvasViewModel;
    }
}
