package sep.fimball.model.trigger;

import sep.fimball.model.Player;

/**
 * Created by kaira on 22.11.2016.
 */
public interface TriggerGameSession
{
    public Player getCurrentPlayer();

    TriggerWorld getWorld();
}
