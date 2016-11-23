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
     * Gibt die Anzahl der Reservebälle des Spielers zurück.
     *
     * @return Die Anzahl der Reservebälle des Spielers.
     */
    ReadOnlyIntegerProperty ballsProperty();

    /**
     * Erhöht die Punkte des Spielers um den übergebenen Wert.
     *
     * @param pointReward Die Punkte, welche der Spieler bekommt.
     */
    void addPoints(int pointReward);

    /**
     * Reduziert die Reservebälle des Spielers um eins.
     *
     * @return Gibt zurück ob der Spieler vor dem Entfernen einer Kugel noch mindestens eine Reservekugel hatte
     */
    boolean removeOneReserveBall();
}
