package sep.fimball.viewmodel.dialog.gameover;

import javafx.beans.property.ListProperty;
import javafx.beans.property.ReadOnlyListProperty;
import javafx.beans.property.SimpleListProperty;
import sep.fimball.general.data.Highscore;
import sep.fimball.model.blueprint.PinballMachine;
import sep.fimball.viewmodel.SceneManagerViewModel;
import sep.fimball.viewmodel.dialog.DialogType;
import sep.fimball.viewmodel.dialog.DialogViewModel;
import sep.fimball.viewmodel.dialog.playername.PlayerNameViewModel;
import sep.fimball.viewmodel.window.mainmenu.MainMenuViewModel;

/**
 * Created by kaira on 05.11.2016.
 */
public class GameOverViewModel extends DialogViewModel
{
    private PinballMachine pinballMachine;
    private ListProperty<Highscore> machineHighscores;
    private ListProperty<Highscore> playerHighscores;

    public GameOverViewModel(PinballMachine pinballMachine)
    {
        super(DialogType.GAME_OVER);
        this.pinballMachine = pinballMachine;

        machineHighscores = new SimpleListProperty<>();
        playerHighscores = new SimpleListProperty<>();

        machineHighscores.bind(pinballMachine.highscoreListProperty());
        //TODO bind playerHighscores
    }

    public void playAgainClicked()
    {
        SceneManagerViewModel.getInstance().setWindow(new MainMenuViewModel());
        SceneManagerViewModel.getInstance().setDialog(new PlayerNameViewModel(pinballMachine));
    }

    public void okClicked()
    {
        SceneManagerViewModel.getInstance().setWindow(new MainMenuViewModel());
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
