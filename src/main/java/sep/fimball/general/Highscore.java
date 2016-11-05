package sep.fimball.general;

import javafx.beans.property.*;

public class Highscore
{
    private LongProperty score;
    private StringProperty playerName;

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