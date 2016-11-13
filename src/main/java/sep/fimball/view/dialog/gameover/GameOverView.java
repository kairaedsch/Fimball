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
     * Das Pane zur Anzeige der Highscores, die am Flipperautomaten erreicht wurden.
     */
    @FXML
    private GridPane highscores;

    /**
     * Das Pane zur Anzeige der in der gerade gespielten Partie erreichten Punktestände.
     */
    @FXML
    private GridPane playerScores;

    /**
     * Das zur GameOverView gehörende GameOverViewModel.
     */
    private GameOverViewModel gameOverViewModel;

    /**
     * Setzt das zur GameOverView gehörende GameOverViewModel.
     *
     * @param gameOverViewModel Das zu setzende GameOverViewModel.
     */
    @Override
    public void setViewModel(GameOverViewModel gameOverViewModel)
    {
        this.gameOverViewModel = gameOverViewModel;
    }

    /**
     * Benachrichtigt das GameOverViewModel, dass der Nutzer das GameOverView schließen möchte.
     */
    @FXML
    private void okClicked()
    {

    }

    /**
     * Benachrichtigt das GameOverViewModel, dass der Nutzer noch eine Partie mit dem selben Flipperautomaten und den selben Spielern spielen möchte.
     */
    @FXML
    private void restartClicked()
    {

    }
}
