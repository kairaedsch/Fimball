package sep.fimball.viewmodel.window.pinballmachine.editor;

import sep.fimball.model.blueprint.pinballmachine.PinballMachine;
import sep.fimball.model.game.EditorSession;
import sep.fimball.model.game.GameSession;
import sep.fimball.viewmodel.SceneManagerViewModel;
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
     * Zeigt Fenster und Dialoge an.
     */
    private SceneManagerViewModel sceneManagerViewModel;

    /**
     * Das PinballCanvasViewModel des angezeigten Spielfelds.
     */
    private PinballCanvasEditorViewModel pinballCanvasViewModel;

    /**
     * Erzeugt eine neue Instanz von EditorSessionSubViewModel.
     * @param viewModel Informationen über die geladene PinballMachine.
     * @param sceneManagerViewModel Zum Anzeigen von Dialogen und Fenstern.
     * @param machine Was im Editor bearbeitet wrird.
     */
    public EditorSessionSubViewModel(PinballMachineEditorViewModel viewModel, SceneManagerViewModel sceneManagerViewModel, PinballMachine machine)
    {
        this.sceneManagerViewModel = sceneManagerViewModel;
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
        sceneManagerViewModel.setWindow(new GameViewModel(GameSession.generateGameSession(pinballMachine, new String[]{"Editor Player"}, true)));
    }

    /**
     * Führt den Benutzer zu dem Automateneinstellungsfenster und speichert den Automaten.
     */
    public void saveAndShowSettingsDialog()
    {
        editorSession.stopUpdateLoop();
        editorSession.stopAutoSaveLoop();
        pinballMachine.savePreviewImage(pinballCanvasViewModel.createScreenshot());
        boolean success = pinballMachine.saveToDisk();
        sceneManagerViewModel.pushDialog(new MessageViewModel("editor.settings.saveMessage." + (success ? "success" : "fail")));
        sceneManagerViewModel.setWindow(new PinballMachineSettingsViewModel(pinballMachine));
    }

    /**
     * Führt den Benutzer zu dem Automateneinstellungsfenster, falls er das QuestionViewModel annimmt.
     */
    public void showSettingsDialog()
    {
        sceneManagerViewModel.pushDialog(new QuestionViewModel("editor.editor.discardQuestion", () ->
        {
            editorSession.stopUpdateLoop();
            editorSession.stopAutoSaveLoop();
            pinballMachine.unloadElements();
            sceneManagerViewModel.setWindow(new PinballMachineSettingsViewModel(pinballMachine));
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
