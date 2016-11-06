package sep.fimball.viewmodel.dialog.pause;

import javafx.beans.property.ListProperty;
import javafx.beans.property.ReadOnlyListProperty;
import sep.fimball.general.data.Highscore;

/**
 * Created by kaira on 05.11.2016.
 */
public class PauseViewModel
{
    private ListProperty<Highscore> playerHighscores;

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
