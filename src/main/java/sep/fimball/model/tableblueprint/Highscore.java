package sep.fimball.model.tableblueprint;

import javafx.beans.property.ReadOnlyLongProperty;
import javafx.beans.property.ReadOnlyStringProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;

public class Highscore
{
    private SimpleLongProperty score;
    private SimpleStringProperty playerName;

    public Highscore(long score, String playerName)
    {
        this.score = new SimpleLongProperty(score);
        this.playerName = new SimpleStringProperty(playerName);
    }

    public ReadOnlyLongProperty scoreProperty()
    {
        return score;
    }

    public ReadOnlyStringProperty playerNameProperty()
    {
        return playerName;
    }
}