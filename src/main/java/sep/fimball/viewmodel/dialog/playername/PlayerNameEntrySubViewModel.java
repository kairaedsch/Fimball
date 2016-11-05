package sep.fimball.viewmodel.dialog.playername;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.ReadOnlyIntegerProperty;

/**
 * Created by kaira on 01.11.2016.
 */
public class PlayerNameEntrySubViewModel
{
    private IntegerProperty playerName;
    private BooleanProperty isDeleteAble;

    public void deleteClicked()
    {

    }

    public ReadOnlyIntegerProperty playerNameProperty()
    {
        return playerName;
    }

    public ReadOnlyBooleanProperty isDeleteAbleProperty()
    {
        return isDeleteAble;
    }
}
