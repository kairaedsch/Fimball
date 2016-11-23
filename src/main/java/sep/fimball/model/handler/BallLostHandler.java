package sep.fimball.model.handler;

/**
 * Der BallLostHandler reagiert auf GameEvent.BALL_LOST Events und kümmert sich um das Wechseln zwischen zwei Spielern bei einer Partie.
 */
public class BallLostHandler implements GameHandler
{
    /**
     * Die GameSession, welche von dem handler beeinflusst wird.
     */
    private HandlerGameSession handlerGameSession;

    /**
     * Erstellt ein neuen BallLostHandler.
     *
     * @param handlerGameSession Die GameSession, welche von dem handler beeinflusst werden soll.
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
            handlerGameSession.getCurrentPlayer().removeOneReserveBall();
            handlerGameSession.switchToNextPlayer();
            handlerGameSession.spawnNewBall();
        }
    }
}
