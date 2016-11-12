package sep.fimball.view.dialog.pause;

import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;
import sep.fimball.view.dialog.DialogView;
import sep.fimball.viewmodel.dialog.pause.PauseViewModel;

/**
 * Die PauseView ist für die Darstellung des aktuellen Standes der Partie, während sie pausiert ist, zuständig und ermöglicht es dem Nutzer, weiterzuspielen oder die Partie abzubrechen.
 */
public class PauseView extends DialogView<PauseViewModel>
{
    /**
     * Das Pane zur Darstellung der Highscore-Einträge der einzelnen Spieler dieser Partie.
     */
    @FXML
    private GridPane playerScores;

    /**
     * Das zur PauseView gehörende PauseViewModel.
     */
    private PauseViewModel pauseViewModel;

    /**
     * Setzt das zum PauseView gehörende PauseViewModel.
     *
     * @param pauseViewModel Das zu setzende PauseViewModel.
     */
    @Override
    public void setViewModel(PauseViewModel pauseViewModel)
    {
        this.pauseViewModel = pauseViewModel;
    }

    /**
     * Benachrichtigt das PauseViewModel, dass der Nutzer das Spiel abbrechen möchte.
     */
    @FXML
    private void abortClicked()
    {
        pauseViewModel.exitDialogToMainMenu();
    }

    /**
     * Benachrichtigt das PauseViewModel, dass der Nutzer weiterspielen möchte.
     */
    @FXML
    private void okClicked()
    {
        pauseViewModel.resumeGame();
    }
}
