package sep.fimball.model.handler;

/**
 * Created by alexcekay on 22.11.16.
 */
public class BallLostHandler implements GameHandler
{
    private HandlerGameSession handlerGameSession;

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
