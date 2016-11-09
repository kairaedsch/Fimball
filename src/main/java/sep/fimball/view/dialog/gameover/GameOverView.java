package sep.fimball.view.dialog.gameover;

import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;
import sep.fimball.view.dialog.DialogView;
import sep.fimball.viewmodel.dialog.gameover.GameOverViewModel;

/**
 * Die GameOverView ist für die Darstellung des Game-Over-Screens zuständig.
 */
public class GameOverView extends DialogView<GameOverViewModel>
{
    /**
     * Das Pane zur Anzeige der Highscores, welche an dem Flipperautomaten erreicht wurden.
     */
    @FXML
    private GridPane highscores;

    /**
     * Das Pane zur Anzeige der im gerade gespielten Partie erreichten Punktestände.
     */
    @FXML
    private GridPane playerScores;

    /**
     * Das zur GameOverView gehörende GameOverViewModel.
     */
    private GameOverViewModel gameOverViewModel;

    /**
     * Setzt das zur GameOverView gehörende GameOverViewModel.
     * @param gameOverViewModel
     */
    @Override
    public void setViewModel(GameOverViewModel gameOverViewModel)
    {
        this.gameOverViewModel = gameOverViewModel;
    }

    /**
     * Erteilt dem GameOverViewModel den Befehl, dass der Nutzer das GameOverView schließen möchte.
     */
    @FXML
    private void okClicked()
    {

    }

    /**
     * Erteilt dem GameOverViewModel den Befehl, dass der Nutzer noch eine Partie mit dem selben Flipperautomat und dem selben Spielern spielen möchte.
     */
    @FXML
    private void restartClicked()
    {

    }
}
