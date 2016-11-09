package sep.fimball.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Stellt einen Spieler welcher an einer Partie teilnimmt dar.
 */
public class Player
{
    /*
     * Die Anzahl an Punkten welcher der Spieler in der aktuellen Partie hat.
     */
    private IntegerProperty points;
    /*
     * Der Name des Spielers.
     */
    private StringProperty name;
    /*
     * Die Anzahl an Reservekugeln die dem Spieler verbleiben.
     */
    private IntegerProperty balls;

    /**
     * Erzeugt einen neuen Spieler.
     */
    public Player(String name)
    {
        this.points = new SimpleIntegerProperty();
        this.name = new SimpleStringProperty();
        this.balls = new SimpleIntegerProperty(3);
    }

    public int getPoints()
    {
        return points.get();
    }

    public IntegerProperty pointsProperty()
    {
        return points;
    }

    public String getName()
    {
        return name.get();
    }

    public StringProperty nameProperty()
    {
        return name;
    }

    public int getBalls()
    {
        return balls.get();
    }

    public IntegerProperty ballsProperty()
    {
        return balls;
    }
}