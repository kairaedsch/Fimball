package sep.fimball.model.handler;

import sep.fimball.model.game.Player;

/**
 * Die HandlerGameSession stellt die GameSession aus der Sicht der Trigger dar.
 */
public interface HandlerGameSession
{
    /**
     * Gibt den aktiven Spieler zurück.
     *
     * @return Der aktive Spieler.
     */
    Player getCurrentPlayer();

    /**
     * Wechselt zum nächsten Spieler.
     */
    void switchToNextPlayer();

    /**
     * Spawnt einen neuen Ball auf dem Spielfeld.
     */
    void spawnNewBall();

    /**
     * Gibt die zu dieser GameSession gehörende World zurück.
     *
     * @return Die zu dieser GameSession gehörende World.
     */
    HandlerWorld getWorld();
}
