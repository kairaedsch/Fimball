package sep.fimball.viewmodel.dialog.playername;

import javafx.beans.property.ReadOnlyListProperty;
import javafx.beans.property.SimpleListProperty;

/**
 * Created by kaira on 05.11.2016.
 */
public class PlayerNameViewModel
{
    private SimpleListProperty<PlayerNameEntrySubViewModel> playerNameEntrys;

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
