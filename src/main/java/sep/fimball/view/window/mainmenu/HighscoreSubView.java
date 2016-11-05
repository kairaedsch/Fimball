package sep.fimball.view.window.mainmenu;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import sep.fimball.general.Highscore;
import sep.fimball.view.SimpleBoundToViewModel;

/**
 * Created by kaira on 01.11.2016.
 */
public class HighscoreSubView implements SimpleBoundToViewModel<Highscore>
{
    @FXML
    public Label highscoreEntry;

    public void bindToViewModel(Highscore highscore)
    {
        highscoreEntry.textProperty().bind(Bindings.concat(highscore.playerNameProperty(), " ", highscore.scoreProperty().asString()));
    }
}
