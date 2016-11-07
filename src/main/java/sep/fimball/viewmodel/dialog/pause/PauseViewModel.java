package sep.fimball.viewmodel.dialog.pause;

import javafx.beans.property.ListProperty;
import javafx.beans.property.ReadOnlyListProperty;
import sep.fimball.general.data.Highscore;
import sep.fimball.viewmodel.dialog.DialogType;
import sep.fimball.viewmodel.dialog.DialogViewModel;
import sep.fimball.viewmodel.dialog.none.EmptyViewModel;
import sep.fimball.viewmodel.window.mainmenu.MainMenuViewModel;

/**
 * Created by kaira on 05.11.2016.
 */
public class PauseViewModel extends DialogViewModel
{
    private ListProperty<Highscore> playerHighscores;

    public PauseViewModel()
    {
        super(DialogType.PAUSE);
    }

    public void abortClicked()
    {
        sceneManager.setWindow(new MainMenuViewModel());
    }

    public void okClicked()
    {
        sceneManager.setDialog(new EmptyViewModel());
    }

    public ReadOnlyListProperty<Highscore> playerHighscoresProperty()
    {
        return playerHighscores;
    }
}
