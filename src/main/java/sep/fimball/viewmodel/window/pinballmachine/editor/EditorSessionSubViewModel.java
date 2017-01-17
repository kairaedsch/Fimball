package sep.fimball.viewmodel.window.pinballmachine.editor;

import sep.fimball.model.blueprint.pinballmachine.PinballMachine;
import sep.fimball.model.game.EditorSession;
import sep.fimball.model.game.GameSession;
import sep.fimball.viewmodel.dialog.message.MessageViewModel;
import sep.fimball.viewmodel.dialog.question.QuestionViewModel;
import sep.fimball.viewmodel.pinballcanvas.PinballCanvasEditorViewModel;
import sep.fimball.viewmodel.window.game.GameViewModel;
import sep.fimball.viewmodel.window.pinballmachine.settings.PinballMachineSettingsViewModel;

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

    private PinballMachineEditorViewModel editorViewModel;

    /**
     * Erzeugt eine neue Instanz von EditorSessionSubViewModel.
     * @param viewModel Informationen über die geladene PinballMachine.
     * @param machine Was im Editor bearbeitet wrird.
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
    public void saveAndShowSettingsDialog()
    {
        editorSession.stopUpdateLoop();
        editorSession.stopAutoSaveLoop(true);
        pinballMachine.savePreviewImage(pinballCanvasViewModel.createScreenshot());
        boolean success = pinballMachine.saveToDisk();
        editorViewModel.getSceneManagerViewModel().pushDialog(new MessageViewModel("editor.settings.saveMessage." + (success ? "success" : "fail")));
        editorViewModel.getSceneManagerViewModel().setWindow(new PinballMachineSettingsViewModel(pinballMachine));
    }

    /**
     * Führt den Benutzer zu dem Automateneinstellungsfenster, falls er das QuestionViewModel annimmt.
     */
    public void showSettingsDialog()
    {
        editorViewModel.getSceneManagerViewModel().pushDialog(new QuestionViewModel("editor.editor.discardQuestion", () ->
        {
            editorSession.stopUpdateLoop();
            editorSession.stopAutoSaveLoop(true);
            pinballMachine.unloadElements();
            editorViewModel.getSceneManagerViewModel().setWindow(new PinballMachineSettingsViewModel(pinballMachine));
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
