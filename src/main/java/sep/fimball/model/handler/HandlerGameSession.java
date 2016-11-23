package sep.fimball.model.handler;

import sep.fimball.model.game.Player;

/**
 * TODO
 */
public interface HandlerGameSession
{
    /**
     * TODO
     * @return
     */
    Player getCurrentPlayer();

    /**
     * TODO
     */
    void switchToNextPlayer();

    /**
     * TODO
     */
    void spawnNewBall();

    /**
     * TODO
     * @return
     */
    HandlerWorld getWorld();
}
