package sep.fimball.model.handler;

import javafx.beans.property.ReadOnlyIntegerProperty;

/**
 * Der Player aus der Sicht der Handler.
 */
public interface HandlerPlayer
{
    /**
     * Gibt die Punkte, die ein Spieler erreicht hat, zurück.
     *
     * @return Die Punkte, die ein Spieler erreicht hat,
     */
    ReadOnlyIntegerProperty pointsProperty();

    /**
     * Gibt die Anzahl der Reservekugeln des Spielers zurück.
     *
     * @return Die Anzahl der Reservekugeln des Spielers.
     */
    ReadOnlyIntegerProperty ballsProperty();

    /**
     * Erhöht die Punkte des Spielers um den übergebenen Wert.
     *
     * @param pointReward Die Punkte, welche der Spieler bekommt.
     */
    void addPoints(int pointReward);

    /**
     * Reduziert die Reservekugeln des Spielers um eins falls er noch Bälle hat.
     */
    void removeOneReserveBall();
}
