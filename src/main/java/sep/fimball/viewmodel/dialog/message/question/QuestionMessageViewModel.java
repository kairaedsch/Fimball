package sep.fimball.viewmodel.dialog.message.question;

import sep.fimball.general.data.Action;
import sep.fimball.viewmodel.dialog.message.MessageViewModel;

/**
 * Das PauseViewModel stellt der View Daten über eine Frage zur Verfügung und ermöglicht die Ausführung der Aktion, falls sich der Nutzer dafür entscheidet.
 */
public class QuestionMessageViewModel extends MessageViewModel
{
    /**
     * Die Aktion, die bei Zustimmung ausgeführt werden soll.
     */
    private Action action;

    /**
     * Erzeugt ein neues QuestionViewModel.
     *
     * @param dialogKey Ein Ressourcen Key welcher zum setzen des Titels sowie der Nachricht in unterschiedlichen Sprachen genutzt wird.
     * @param action Die Aktion die beim Klicken des OK Button des Dialogs ausgeführt werden soll.
     */
    public QuestionMessageViewModel(String dialogKey, Action action)
    {
        super(dialogKey);

        this.action = action;
    }

    /**
     * Führt die Aktion die ausgeführt werden soll aus und schließt den Dialog.
     */
    public void rightButtonClicked()
    {
        sceneManager.popDialog();
        action.perform();
    }

    /**
     * Beendet den Dialog.
     */
    public void leftButtonClicked()
    {
        sceneManager.popDialog();
    }
}
