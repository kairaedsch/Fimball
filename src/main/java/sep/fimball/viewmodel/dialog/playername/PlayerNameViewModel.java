package sep.fimball.viewmodel.dialog.playername;

import javafx.beans.property.ListProperty;
import javafx.beans.property.ReadOnlyListProperty;
import sep.fimball.model.GameSession;
import sep.fimball.model.blueprint.PinballMachine;
import sep.fimball.viewmodel.SceneManagerViewModel;
import sep.fimball.viewmodel.dialog.DialogType;
import sep.fimball.viewmodel.dialog.DialogViewModel;
import sep.fimball.viewmodel.dialog.none.NoneViewModel;
import sep.fimball.viewmodel.window.game.GameViewModel;

/**
 * Created by kaira on 05.11.2016.
 */
public class PlayerNameViewModel extends DialogViewModel
{
    private ListProperty<PlayerNameEntrySubViewModel> playerNameEntrys;
    private PinballMachine pinballMachine;

    public PlayerNameViewModel(PinballMachine pinballMachine)
    {
        super(DialogType.PLAYER_NAMES);
        this.pinballMachine = pinballMachine;
    }

    public void addPlayerClicked()
    {

    }

    public void startClicked()
    {
        SceneManagerViewModel.getInstance().setWindow(new GameViewModel(new GameSession()));
    }

    public void abortClicked()
    {
        SceneManagerViewModel.getInstance().setDialog(new NoneViewModel());
    }

    public ReadOnlyListProperty<PlayerNameEntrySubViewModel> playerNameEntrysProperty()
    {
        return playerNameEntrys;
    }
}
