package sep.fimball.viewmodel.dialog.playername;

import javafx.beans.property.ListProperty;
import javafx.beans.property.ReadOnlyListProperty;
import sep.fimball.model.blueprint.PinballMachine;
import sep.fimball.viewmodel.ViewModel;

/**
 * Created by kaira on 05.11.2016.
 */
public class PlayerNameViewModel extends ViewModel
{
    private ListProperty<PlayerNameEntrySubViewModel> playerNameEntrys;
    private PinballMachine pinballMachine;

    public PlayerNameViewModel(PinballMachine pinballMachine)
    {
        this.pinballMachine = pinballMachine;
    }

    public void addPlayerClicked()
    {

    }

    public void okClicked()
    {

    }

    public void abortClicked()
    {

    }

    public ReadOnlyListProperty<PlayerNameEntrySubViewModel> playerNameEntrysProperty()
    {
        return playerNameEntrys;
    }
}
