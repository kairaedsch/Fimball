package sep.fimball.model.handler;

import sep.fimball.model.game.Player;

/**
 * Created by kaira on 22.11.2016.
 */
public interface HandlerGameSession
{
    public Player getCurrentPlayer();

    HandlerWorld getWorld();
}
