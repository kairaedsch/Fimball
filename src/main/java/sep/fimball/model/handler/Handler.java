package sep.fimball.model.handler;

import sep.fimball.model.input.manager.KeyEventArgs;

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

    public Handler(SomeHandler someHandler)
    {
        if(someHandler instanceof ElementHandler) setElementHandler((ElementHandler) someHandler);
        if(someHandler instanceof GameHandler) setGameHandler((GameHandler) someHandler);
        if(someHandler instanceof UserHandler) setUserHandler((UserHandler) someHandler);
    }

    /**
     * Setzt den ElementHandler, der auf Kollisionen zwischen Ball und Spielelementen reagiert.
     *
     * @param elementHandler Der neue ElementHandler.
     */
    public void setElementHandler(ElementHandler elementHandler)
    {
        this.elementHandler = Optional.of(elementHandler);
    }

    /**
     * Setzt den GameHandler, der auf Spielereignisse reagiert.
     *
     * @param gameHandler Der neue GameHandler.
     */
    public void setGameHandler(GameHandler gameHandler)
    {
        this.gameHandler = Optional.of(gameHandler);
    }

    /**
     * Setzt den UserHandler, der auf Spielereingaben reagiert.
     *
     * @param userHandler Der neue UserHandler.
     */
    public void setUserHandler(UserHandler userHandler)
    {
        this.userHandler = Optional.of(userHandler);
    }

    @Override
    public void activateElementHandler(HandlerGameElement element, int colliderId)
    {
        elementHandler.ifPresent(elementHandler -> elementHandler.activateElementHandler(element, colliderId));
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
