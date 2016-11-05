package sep.fimball.viewmodel.dialog.pause;

import javafx.beans.property.ReadOnlyListProperty;
import javafx.beans.property.SimpleListProperty;
import sep.fimball.general.Highscore;

/**
 * Created by kaira on 05.11.2016.
 */
public class PauseViewModel
{
    private SimpleListProperty<Highscore> playerHighscores;

    public void abortClicked()
    {

    }

    public void okClicked()
    {

    }

    public ReadOnlyListProperty<Highscore> playerHighscoresProperty()
    {
        return playerHighscores;
    }
}
