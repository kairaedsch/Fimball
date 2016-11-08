package sep.fimball.view.dialog.gameover;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import sep.fimball.view.dialog.DialogView;
import sep.fimball.viewmodel.dialog.gameover.GameOverViewModel;

/**
 * Created by kaira on 01.11.2016.
 */
public class GameOverView extends DialogView<GameOverViewModel>
{
    @FXML
    private GridPane highscores;
    @FXML
    private GridPane playerScores;

    private GameOverViewModel gameOverViewModel;

    @Override
    public void setViewModel(GameOverViewModel gameOverViewModel)
    {
        this.gameOverViewModel = gameOverViewModel;
    }

    @FXML
    private void okClicked()
    {

    }

    @FXML
    private void restartClicked()
    {

    }
}
