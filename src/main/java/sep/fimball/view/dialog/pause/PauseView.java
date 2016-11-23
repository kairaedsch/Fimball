package sep.fimball.view.dialog.pause;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import sep.fimball.view.dialog.DialogView;
import sep.fimball.viewmodel.dialog.pause.PauseViewModel;

/**
 * Die PauseView ist für die Darstellung des aktuellen Standes der Partie, während sie pausiert ist, zuständig und ermöglicht es dem Nutzer, weiterzuspielen oder die Partie abzubrechen.
 */
public class PauseView extends DialogView<PauseViewModel>
{
    /**
     * Der Behälter zur Darstellung der Highscore-Einträge der einzelnen Spieler dieser Partie.
     */
    @FXML
    private VBox playerScores;

    /**
     * Das zur PauseView gehörende PauseViewModel.
     */
    private PauseViewModel pauseViewModel;

    /**
     * Der Button, der zurück in das Hauptmenü führt.
     */
    @FXML
    private Button exitToMainMenuButton;

    @Override
    public void setViewModel(PauseViewModel pauseViewModel)
    {
        this.pauseViewModel = pauseViewModel;
    }

    /**
     * Benachrichtigt das {@code pauseViewModel}, dass der Nutzer das Spiel abbrechen möchte.
     */
    @FXML
    private void abortClicked()
    {
        pauseViewModel.exitDialog();
    }

    /**
     * Benachrichtigt das {@code pauseViewModel}, dass der Nutzer weiterspielen möchte.
     */
    @FXML
    private void okClicked()
    {
        pauseViewModel.resumeGame();
    }
}
