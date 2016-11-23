package sep.fimball.model.handler;

/**
 * Der GameHandler nimmt GameEvents entgegen und reagiert auf diese.
 */
public interface GameHandler
{
    /**
     * Benachrichtigt den Handler über ein eingetretenes GameEvent.
     * @param gameEvent Das GameEvent, welches aufgetreten ist.
     */
    void activateGameHandler(GameEvent gameEvent);
}
