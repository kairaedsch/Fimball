package sep.fimball.viewmodel.dialog.playername;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;

/**
 * Created by kaira on 01.11.2016.
 */
public class PlayerNameEntrySubViewModel
{
    private SimpleIntegerProperty playerName;
    private SimpleBooleanProperty isDeleteAble;

    public void deleteClicked()
    {

    }

    public SimpleIntegerProperty playerNameProperty()
    {
        return playerName;
    }

    public SimpleBooleanProperty isDeleteAbleProperty()
    {
        return isDeleteAble;
    }
}
