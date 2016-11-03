package sep.fimball.viewmodel.mainmenu;

import javafx.beans.property.*;

/**
 * Created by kaira on 01.11.2016.
 */
public class TableBlueprintDetailedPreviewHighscoreEntry
{
    SimpleStringProperty playerName;
    SimpleLongProperty score;

    public TableBlueprintDetailedPreviewHighscoreEntry()
    {

    }

    public ReadOnlyStringProperty playerNameProperty()
    {
        return playerName;
    }

    public ReadOnlyLongProperty scoreProperty()
    {
        return score;
    }
}
