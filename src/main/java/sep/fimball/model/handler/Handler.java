package sep.fimball.model.handler;

import sep.fimball.model.input.manager.KeyEventArgs;
import sep.fimball.model.physics.game.CollisionEventType;

import java.util.Optional;

/**
 * Wird bei verschiedenen Ereignissen im Spiel informiert und kann auf diese reagieren.
 */
public class Handler implements ElementHandler, GameHandler, UserHandler
{
    /**
     * Reagiert auf Kollisionen zwischen Ball und Spielelementen.
     */
    private Optional<ElementHandler> elementHandler = Optional.empty();

    /**
     * Reagiert auf gewisse Spielereignisse.
     */
    private Optional<GameHandler> gameHandler = Optional.empty();

    /**
     * Reagiert auf Aktionen des Spielers.
     */
    private Optional<UserHandler> userHandler = Optional.empty();

    /**
     * Erstellt einen neuen Handler aus einem someHandler.
     *
     * @param someHandler Den zu wrappenden someHandler.
     */
    public Handler(SomeHandler someHandler)
    {
        if (someHandler instanceof ElementHandler)
            setElementHandler((ElementHandler) someHandler);
        if (someHandler instanceof GameHandler)
            setGameHandler((GameHandler) someHandler);
        if (someHandler instanceof UserHandler)
            setUserHandler((UserHandler) someHandler);
    }

    /**
     * Setzt den ElementHandler, der auf Kollisionen zwischen Ball und Spielelementen reagiert.
     *
     * @param elementHandler Der neue ElementHandler.
     */
    private void setElementHandler(ElementHandler elementHandler)
    {
        this.elementHandler = Optional.of(elementHandler);
    }

    /**
     * Setzt den GameHandler, der auf Spielereignisse reagiert.
     *
     * @param gameHandler Der neue GameHandler.
     */
    private void setGameHandler(GameHandler gameHandler)
    {
        this.gameHandler = Optional.of(gameHandler);
    }

    /**
     * Setzt den UserHandler, der auf Spielereingaben reagiert.
     *
     * @param userHandler Der neue UserHandler.
     */
    private void setUserHandler(UserHandler userHandler)
    {
        this.userHandler = Optional.of(userHandler);
    }

    @Override
    public void activateElementHandler(HandlerGameElement element, CollisionEventType collisionEventType, int colliderId)
    {
        elementHandler.ifPresent(elementHandler -> elementHandler.activateElementHandler(element, collisionEventType, colliderId));
    }

    @Override
    public void activateGameHandler(GameEvent gameEvent)
    {
        gameHandler.ifPresent(gameHandler -> gameHandler.activateGameHandler(gameEvent));
    }

    @Override
    public void activateUserHandler(KeyEventArgs keyEventType)
    {
        userHandler.ifPresent(userHandler -> userHandler.activateUserHandler(keyEventType));
    }
}
