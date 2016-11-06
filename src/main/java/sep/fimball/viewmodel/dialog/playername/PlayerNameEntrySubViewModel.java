package sep.fimball.viewmodel.dialog.playername;

import javafx.beans.property.*;

/**
 * Created by kaira on 01.11.2016.
 */
public class PlayerNameEntrySubViewModel
{
    private StringProperty playerName;
    private BooleanProperty isDeleteAble;

    public PlayerNameEntrySubViewModel(String name, boolean isDeleteAble)
    {
        this.playerName = new SimpleStringProperty();
        this.isDeleteAble = new SimpleBooleanProperty(isDeleteAble);
    }

    public void deleteClicked()
    {

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
