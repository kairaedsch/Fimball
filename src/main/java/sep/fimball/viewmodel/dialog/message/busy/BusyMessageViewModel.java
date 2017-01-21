package sep.fimball.viewmodel.dialog.message.busy;

import javafx.animation.PauseTransition;
import javafx.util.Duration;
import sep.fimball.general.data.Action;
import sep.fimball.viewmodel.dialog.message.MessageViewModel;

/**
 * Das PauseViewModel stellt der View Daten über eine Frage zur Verfügung und ermöglicht die Ausführung der Aktion, falls sich der Nutzer dafür entscheidet.
 */
public class BusyMessageViewModel extends MessageViewModel
{
    public BusyMessageViewModel(String dialogKey, Action action)
    {
        this(dialogKey, action, () -> {});
    }

    /**
     * Erzeugt ein neues QuestionViewModel.
     *
     * @param dialogKey Ein Ressourcen Key welcher zum setzen des Titels sowie der Nachricht in unterschiedlichen Sprachen genutzt wird.
     */
    public BusyMessageViewModel(String dialogKey, Action longAction, Action afterAction)
    {
        super(dialogKey);
        showleftButton.setValue(false);
        showRightButton.setValue(false);

        PauseTransition resetTransition = new PauseTransition(Duration.millis(50));
        resetTransition.setOnFinished(e ->
        {
            longAction.perform();
            sceneManager.popDialog();
            afterAction.perform();
        });
        resetTransition.play();
    }

    /**
     * Führt die Aktion die ausgeführt werden soll aus und schließt den Dialog.
     */
    public void rightButtonClicked()
    {

    }

    /**
     * Beendet den Dialog.
     */
    public void leftButtonClicked()
    {

    }
}