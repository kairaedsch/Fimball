package sep.fimball.model.handler;

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
