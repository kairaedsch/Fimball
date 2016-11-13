package sep.fimball.view;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import sep.fimball.general.data.Highscore;
import sep.fimball.view.ViewBoundToViewModel;

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

    /**
     * Bindet die HighscoreSubView an den zugehörigen Highscore.
     *
     * @param highscore Der Highscore, an den diese HighscoreSubView gebunden werden soll.
     */
    public void setViewModel(Highscore highscore)
    {
        //highscoreEntry.textProperty().bind(Bindings.concat(highscore.playerNameProperty(), " ", highscore.scoreProperty().asString()));
    }
}
