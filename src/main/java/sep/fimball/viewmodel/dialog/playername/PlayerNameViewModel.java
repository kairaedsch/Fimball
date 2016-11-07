package sep.fimball.viewmodel.dialog.playername;

import javafx.beans.property.ListProperty;
import javafx.beans.property.ReadOnlyListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import sep.fimball.model.GameSession;
import sep.fimball.model.blueprint.PinballMachine;
import sep.fimball.viewmodel.SceneManagerViewModel;
import sep.fimball.viewmodel.dialog.DialogType;
import sep.fimball.viewmodel.dialog.DialogViewModel;
import sep.fimball.viewmodel.dialog.none.EmptyViewModel;
import sep.fimball.viewmodel.window.game.GameViewModel;

/**
 * Created by kaira on 05.11.2016.
 */
public class PlayerNameViewModel extends DialogViewModel
{
    private PinballMachine pinballMachine;

    private ListProperty<PlayerNameEntrySubViewModel> playerNameEntrys;

    public PlayerNameViewModel(PinballMachine pinballMachine)
    {
        super(DialogType.PLAYER_NAMES);
        this.pinballMachine = pinballMachine;

        playerNameEntrys = new SimpleListProperty<>(FXCollections.observableArrayList());
        playerNameEntrys.add(new PlayerNameEntrySubViewModel(this, "Player 1", true));
    }

    public void removePlayerNameEntry(PlayerNameEntrySubViewModel playerNameEntrySubViewModel)
    {
        playerNameEntrys.remove(playerNameEntrySubViewModel);
    }

    public void addPlayerClicked()
    {
        playerNameEntrys.add(new PlayerNameEntrySubViewModel(this, "Player " + (playerNameEntrys.size() + 1), false));
    }

    public void startClicked()
    {
        sceneManager.setWindow(new GameViewModel(new GameSession()));
    }

    public void abortClicked()
    {
        sceneManager.setDialog(new EmptyViewModel());
    }

    public ReadOnlyListProperty<PlayerNameEntrySubViewModel> playerNameEntrysProperty()
    {
        return playerNameEntrys;
    }
}
