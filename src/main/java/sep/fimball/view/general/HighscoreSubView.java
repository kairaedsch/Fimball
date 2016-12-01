package sep.fimball.view.general;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import sep.fimball.general.data.Highscore;
import sep.fimball.view.ViewBoundToViewModel;

/**
 * Die HighscoreSubView ist für die Darstellung eines {@link Highscore}s zuständig.
 */
public class HighscoreSubView implements ViewBoundToViewModel<Highscore>
{
    /**
     * Zeigt den Namen des Spielers, der den Highscore erreicht hat.
     */
    @FXML
    private Label playerName;

    /**
     * Zeigt die Höhe des Highscores.
     */
    @FXML
    private Label score;

    @Override
    public void setViewModel(Highscore highscore)
    {
        playerName.textProperty().bind(highscore.playerNameProperty());
        score.textProperty().bind(highscore.scoreProperty().asString());
    }
}
