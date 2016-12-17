package sep.fimball.model.handler;

import sep.fimball.model.input.manager.KeyEventArgs;

/**
 * Wird bei verschiedenen Ereignissen im Spiel informiert und kann auf diese reagieren.
 */
public class Handler extends UserHandler implements ElementHandler, GameHandler
{
    /**
     * Reagiert auf Kollisionen zwischen Ball und Spielelementen.
     */
    private ElementHandler elementHandler = null;

    /**
     * Reagiert auf gewisse Spielereignisse.
     */
    private GameHandler gameHandler = null;

    /**
     * Reagiert auf Aktionen des Spielers.
     */
    private UserHandler userHandler = null;

    /**
     * Setzt den ElementHandler, der auf Kollisionen zwischen Ball und Spielelementen reagiert.
     *
     * @param elementHandler Der neue ElementHandler.
     */
    public void setElementHandler(ElementHandler elementHandler)
    {
        this.elementHandler = elementHandler;
    }

    /**
     * Setzt den GameHandler, der auf Spielereignisse reagiert.
     *
     * @param gameHandler Der neue GameHandler.
     */
    public void setGameHandler(GameHandler gameHandler)
    {
        this.gameHandler = gameHandler;
    }

    /**
     * Setzt den UserHandler, der auf Spielereingaben reagiert.
     *
     * @param userHandler Der neue UserHandler.
     */
    public void setUserHandler(UserHandler userHandler)
    {
        this.userHandler = userHandler;
    }

    @Override
    public void activateElementHandler(HandlerGameElement element, int colliderId)
    {
        if (elementHandler != null)
            elementHandler.activateElementHandler(element, colliderId);
    }

    @Override
    public void activateGameHandler(GameEvent gameEvent)
    {
        if (gameHandler != null)
            gameHandler.activateGameHandler(gameEvent);
    }

    @Override
    public void activateUserHandler(KeyEventArgs keyEventType)
    {
        if (userHandler != null)
            userHandler.activateUserHandler(keyEventType);
    }
}
