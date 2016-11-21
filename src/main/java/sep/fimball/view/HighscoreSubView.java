package sep.fimball.view;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import sep.fimball.general.data.Highscore;

/**
 * Die HighscoreSubView ist für die Darstellung eines Highscores zuständig.
 */
public class HighscoreSubView implements ViewBoundToViewModel<Highscore>
{
    /**
     * Zeigt den Namen des Spielers, der den Highscore erreicht hat.
     */
    @FXML
    public Label playerName;

    /**
     * Zeigt die Höhe des Highscores.
     */
    @FXML
    public Label score;

    @Override
    public void setViewModel(Highscore highscore)
    {
        playerName.textProperty().bind(highscore.playerNameProperty());
        score.textProperty().bind(highscore.scoreProperty().asString());
    }
}
