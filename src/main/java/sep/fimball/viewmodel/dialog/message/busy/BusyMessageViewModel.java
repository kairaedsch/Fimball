package sep.fimball.viewmodel.dialog.message.busy;

import javafx.animation.PauseTransition;
import javafx.util.Duration;
import sep.fimball.general.data.Action;
import sep.fimball.viewmodel.dialog.message.MessageViewModel;

/**
 * Das BusyMessageViewModel stellt der View Daten über eine aktuelle Aktion zur Verfügung und verschwindet, wenn die Aktion abgeschlossen ist.
 */
public class BusyMessageViewModel extends MessageViewModel
{
    /**
     * Erstellt ein neues BusyMessageViewModel.
     *
     * @param dialogKey Ein Ressourcen Key welcher zum setzen des Titels sowie der Nachricht in unterschiedlichen Sprachen genutzt wird.
     * @param action    Die automatisch auszuführende Aktion.
     */
    public BusyMessageViewModel(String dialogKey, Action action)
    {
        this(dialogKey, action, () -> {});
    }

    /**
     * Erstellt ein neues BusyMessageViewModel.
     *
     * @param dialogKey Ein Ressourcen Key welcher zum setzen des Titels sowie der Nachricht in unterschiedlichen Sprachen genutzt wird.
     * @param longAction Die automatisch auszuführende Aktion.
     * @param afterAction Die automatisch auszuführende Aktion, nachdem der BusyDialog wieder weg ist.
     */
    public BusyMessageViewModel(String dialogKey, Action longAction, Action afterAction)
    {
        super(dialogKey, false, false);

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
     * Macht nichts.
     */
    public void rightButtonClicked()
    {

    }

    /**
     * Macht nichts.
     */
    public void leftButtonClicked()
    {

    }
}