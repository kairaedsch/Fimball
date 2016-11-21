package sep.fimball.general.data;

import javafx.beans.property.*;

/**
 * Highscore speichert den Namen und den erreichten Punktestand, der zu einem Highscore gehört.
 */
public class Highscore
{
    /**
     * Die erreichten Punkte.
     */
    private LongProperty score;

    /**
     * Der Name des Spielers.
     */
    private StringProperty playerName;

    /**
     * Erzeugt einen neuen Highscore mit gegebenen Punkten und Namen.
     *
     * @param score      Der erreichte Punktestand.
     * @param playerName Der Name des Spielers, der den zu erzeugenden Highscore erreicht hat.
     */
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