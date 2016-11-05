package sep.fimball.viewmodel.dialog.playername;

import javafx.beans.property.ListProperty;
import javafx.beans.property.ReadOnlyListProperty;

/**
 * Created by kaira on 05.11.2016.
 */
public class PlayerNameViewModel
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
