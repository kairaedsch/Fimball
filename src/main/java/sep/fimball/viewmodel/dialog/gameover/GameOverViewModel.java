package sep.fimball.viewmodel.dialog.gameover;

import javafx.beans.property.ReadOnlyListProperty;
import javafx.beans.property.SimpleListProperty;
import sep.fimball.general.Highscore;

/**
 * Created by kaira on 05.11.2016.
 */
public class GameOverViewModel
{
    private SimpleListProperty<Highscore> machineHighscores;
    private SimpleListProperty<Highscore> playerHighscores;

    public void playAgainClicked()
    {

    }

    public void okClicked()
    {

    }

    public ReadOnlyListProperty<Highscore> machineHighscoresProperty()
    {
        return machineHighscores;
    }

    public SimpleListProperty<Highscore> playerHighscoresProperty()
    {
        return playerHighscores;
    }
}
