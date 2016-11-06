package sep.fimball.viewmodel.dialog.gameover;

import javafx.beans.property.ListProperty;
import javafx.beans.property.ReadOnlyListProperty;
import sep.fimball.general.data.Highscore;

/**
 * Created by kaira on 05.11.2016.
 */
public class GameOverViewModel
{
    private ListProperty<Highscore> machineHighscores;
    private ListProperty<Highscore> playerHighscores;

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

    public ReadOnlyListProperty<Highscore> playerHighscoresProperty()
    {
        return playerHighscores;
    }
}
