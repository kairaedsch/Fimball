package sep.fimball.viewmodel.dialog.playername;

import javafx.beans.property.*;

/**
 * Created by kaira on 01.11.2016.
 */
public class PlayerNameEntrySubViewModel
{
    private PlayerNameViewModel playerNameViewModel;

    private StringProperty playerName;
    private BooleanProperty isDeleteAble;

    public PlayerNameEntrySubViewModel(PlayerNameViewModel playerNameViewModel, String name, boolean isDeleteAble)
    {
        this.playerNameViewModel = playerNameViewModel;

        this.playerName = new SimpleStringProperty(name);
        this.isDeleteAble = new SimpleBooleanProperty(isDeleteAble);
    }

    public void deleteClicked()
    {
        if(isDeleteAble.get()) playerNameViewModel.removePlayerNameEntry(this);
    }

    // TODO bind bidirekt
    public StringProperty playerNameProperty()
    {
        return playerName;
    }

    public ReadOnlyBooleanProperty isDeleteAbleProperty()
    {
        return isDeleteAble;
    }
}
