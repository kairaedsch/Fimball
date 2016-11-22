package sep.fimball.model.trigger;

import sep.fimball.model.game.GameElement;
import sep.fimball.model.input.KeyBinding;
import sep.fimball.model.input.KeyEventType;

/**
 * Wird bei verschiedenen Geschehnissen im Spiel informiert, und kann auf diese Reagieren.
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
    public void activateGameHandler()
    {
        if (gameHandler != null)
            gameHandler.activateGameHandler();
    }

    @Override
    public void activateUserHandler(KeyBinding keyBinding, KeyEventType keyEventType)
    {
        if (userHandler != null)
            userHandler.activateUserHandler(keyBinding, keyEventType);
    }
}
