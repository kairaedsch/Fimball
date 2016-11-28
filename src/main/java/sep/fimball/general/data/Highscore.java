package sep.fimball.general.data;

import javafx.beans.property.*;

/**
 * Highscore speichert den Namen und den erreichten Punktestand eines Highscores.
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
     * Erzeugt einen neuen Highscore mit gegebenen Punkten und Spielernamen.
     *
     * @param score      Der erreichte Punktestand.
     * @param playerName Der Name des Spielers, der den zu erzeugenden Highscore erreicht hat.
     */
    public Highscore(long score, String playerName)
    {
        this.score = new SimpleLongProperty(score);
        this.playerName = new SimpleStringProperty(playerName);
    }

    /**
     * Gibt die erreichten Punkte des Highscores zurück.
     * @return Die erreichten Punkte.
     */
    public ReadOnlyLongProperty scoreProperty()
    {
        return score;
    }

    /**
     * Gibt den Namen des Spielers im Highscore zurück.
     * @return Der Name des Spielers.
     */
    public ReadOnlyStringProperty playerNameProperty()
    {
        return playerName;
    }
}