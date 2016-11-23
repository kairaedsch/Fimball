package sep.fimball.model.handler;

/**
 * Handler, der bei gewissen Spielereignissen aufgerufen wird.
 */
public class BallLostHandler implements GameHandler
{
    /**
     * Session, die diesen Handler aufruft.
     */
    private HandlerGameSession handlerGameSession;

    /**
     * Erzeugt eine neue Instanz von BallLostHandler und setzt die jeweilige Session.
     *
     * @param handlerGameSession Session, die diesen Handler aufruft.
     */
    public BallLostHandler(HandlerGameSession handlerGameSession)
    {
        this.handlerGameSession = handlerGameSession;
    }

    @Override
    public void activateGameHandler(GameEvent gameEvent)
    {
        if (gameEvent.equals(GameEvent.BALL_LOST))
        {
            //TODO Check if no balls left
            handlerGameSession.getCurrentPlayer().ballsProperty().setValue(handlerGameSession.getCurrentPlayer().ballsProperty().get() - 1);
            handlerGameSession.switchToNextPlayer();
            handlerGameSession.spawnNewBall();
        }
    }
}
