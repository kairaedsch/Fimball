package sep.fimball.model.handler;

/**
 * Der GameHandler nimmt GameEvents entgegen und reagiert auf diese.
 */
public interface GameHandler
{
    /**
     * Benachrichtigt den handler über ein passiertes GameEvent.
     * @param gameEvent Das GameEvent, welches aufgetreten ist.
     */
    void activateGameHandler(GameEvent gameEvent);
}
