package sep.fimball.view.window.mainmenu;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import sep.fimball.general.Highscore;

/**
 * Created by kaira on 01.11.2016.
 */
public class MainMenuDetailedPreviewHighscoreEntryFxController
{
    @FXML
    public Label playerName;
    @FXML
    public Label score;

    public void bindToViewModel(Highscore highscore)
    {
        playerName.textProperty().bind(highscore.playerNameProperty());
        score.textProperty().bind(highscore.scoreProperty().asString());
    }
}
