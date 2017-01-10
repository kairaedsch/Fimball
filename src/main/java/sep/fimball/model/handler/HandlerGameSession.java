package sep.fimball.model.handler;

import javafx.beans.property.ReadOnlyObjectProperty;

/**
 * Die HandlerGameSession stellt die GameSession aus der Sicht der Handler dar.
 */
public interface HandlerGameSession
{
    /**
     * Gibt den aktiven Spieler zurück.
     *
     * @return Der aktive Spieler.
     */
    HandlerPlayer getCurrentPlayer();

    /**
     * Wechselt zum nächsten Spieler.
     */
    void switchToNextPlayer();

    /**
     * Spawned einen neuen Ball auf dem Spielfeld.
     */
    void spawnNewBall();

    /**
     * Gibt die zu dieser GameSession gehörende World zurück.
     *
     * @return Die zu dieser GameSession gehörende World.
     */
    HandlerWorld getWorld();

    /**
     * Gibt das Ball-Element zurück.
     *
     * @return Das Ball-Element.
     */
    ReadOnlyObjectProperty<? extends HandlerBallGameElement> gameBallProperty();

    /**
     * Lässt den Ball als verloren gelten.
     */
    void ballLost();
}
