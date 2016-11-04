package sep.fimball.view.dialog.gameover;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import sep.fimball.view.dialog.Dialog;

/**
 * Created by kaira on 01.11.2016.
 */
public class GameOverDialogFxController extends Dialog
{
    @FXML
    private Button okButton;
    @FXML
    private Button abortButton;
    @FXML
    private GridPane highscores;
    @FXML
    private GridPane playerScores;

    @FXML
    public void endGame()
    {

    }

    @FXML
    public void restart()
    {

    }
}
