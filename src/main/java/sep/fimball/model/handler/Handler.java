package sep.fimball.model.handler;

import sep.fimball.model.game.GameElement;
import sep.fimball.model.input.KeyBinding;
import sep.fimball.model.input.KeyEventType;

/**
 * Wird bei verschiedenen Ereignissen im Spiel informiert und kann auf diese reagieren.
 */
public class Handler implements ElementHandler, UserHandler, GameHandler
{
    /**
     * Reagiert auf Kollisionen zwischen Ball und Spielelementen.
     */
    private ElementHandler elementHandler = null;

    /**
     * TODO
     */
    private GameHandler gameHandler = null;

    /**
     * Reagiert auf Aktionen des Spielers.
     */
    private UserHandler userHandler = null;

    /**
     * Setzt den ElementHandler, der auf Kollisionen zwischen Ball und Spielelementen reagiert.
     * @param elementHandler Der neue ElementHandler.
     */
    public void setElementHandler(ElementHandler elementHandler)
    {
        this.elementHandler = elementHandler;
    }

    public void setGameHandler(GameHandler gameHandler)
    {
        this.gameHandler = gameHandler;
    }

    public void setUserHandler(UserHandler userHandler)
    {
        this.userHandler = userHandler;
    }

    @Override
    public void activateElementHandler(GameElement element, int colliderId)
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
    public void activateUserHandler(KeyBinding keyBinding, KeyEventType keyEventType)
    {
        if (userHandler != null)
            userHandler.activateUserHandler(keyBinding, keyEventType);
    }
}
