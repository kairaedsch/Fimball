package sep.fimball.viewmodel.dialog.playername;

import javafx.beans.property.ListProperty;
import javafx.beans.property.ReadOnlyListProperty;
import sep.fimball.viewmodel.ViewModel;

/**
 * Created by kaira on 05.11.2016.
 */
public class PlayerNameViewModel extends ViewModel
{
    private ListProperty<PlayerNameEntrySubViewModel> playerNameEntrys;

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
