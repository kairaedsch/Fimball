package sep.fimball.model.game;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Stellt einen Spieler dar welcher an einer Partie teilnimmt.
 */
public class Player
{
    /**
     * Die Anzahl an Punkten welcher der Spieler in der aktuellen Partie hat.
     */
    private IntegerProperty points;
    /**
     * Der Name des Spielers.
     */
    private StringProperty name;
    /**
     * Die Anzahl an Reservekugeln die dem Spieler verbleiben.
     */
    private IntegerProperty balls;

    /**
     * Erzeugt einen neuen Spieler.
     *
     * @param name Name des Spielers.
     */
    public Player(String name)
    {
        this.points = new SimpleIntegerProperty();
        this.name = new SimpleStringProperty(name);
        this.balls = new SimpleIntegerProperty(3);
    }

    /**
     * Gibt die vom Spieler erreichten Punkte zurück.
     *
     * @return Die vom Spieler erreichten Punkte.
     */
    public int getPoints()
    {
        return points.get();
    }

    /**
     * Gibt die Punkte, die ein Spieler erreicht hat, zurück.
     *
     * @return Die Punkte, die ein Spieler erreicht hat,
     */
    public IntegerProperty pointsProperty()
    {
        return points;
    }

    /**
     * Gibt den Namen des Spielers zurück.
     *
     * @return Der Name des Spielers.
     */
    public String getName()
    {
        return name.get();
    }

    /**
     * Gibt den Mamen des Spielers zurück.
     *
     * @return Der Name des Spielers.
     */
    public StringProperty nameProperty()
    {
        return name;
    }

    /**
     * Gibt die Anzahl der Reservebälle des Spielers zurück.
     *
     * @return Die Anzahl der Reservebälle des Spielers.
     */
    public IntegerProperty ballsProperty()
    {
        return balls;
    }
}