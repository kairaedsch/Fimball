package sep.fimball.model.game;

import javafx.beans.property.*;
import sep.fimball.model.handler.HandlerPlayer;

/**
 * Stellt einen Spieler dar welcher an einer Partie teilnimmt.
 */
public class Player implements HandlerPlayer
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

    @Override
    public void addPoints(int pointReward)
    {
        points.set(points.get() + pointReward);
    }

    @Override
    public boolean removeOneReserveBall()
    {
        if (balls.get() > 0)
        {
            balls.set(balls.get() - 1);
            return true;
        }
        else
        {
            return false;
        }
    }

    /**
     * Gibt die Punkte des Spielers zurück.
     *
     * @return Der Punktestand des Spielers.
     */
    public ReadOnlyIntegerProperty pointsProperty()
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
     * Gibt den Namen des Spielers zurück.
     *
     * @return Der Name des Spielers.
     */
    public ReadOnlyStringProperty nameProperty()
    {
        return name;
    }

    /**
     * Gibt die Kugel des aktiven Spielers zurück.
     * @return Die Kugel des aktiven Spielers.
     */
    public ReadOnlyIntegerProperty ballsProperty()
    {
        return balls;
    }
}