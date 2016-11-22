package sep.fimball.model.handler;

/**
 * Created by alexcekay on 22.11.16.
 */
public class BallLostHandler implements GameHandler
{
    @Override
    public void activateGameHandler(GameEvent gameEvent, HandlerGameSession handlerGameSession)
    {
        if (gameEvent.equals(GameEvent.BALL_LOST))
        {
            handlerGameSession.switchToNextPlayer();
            handlerGameSession.spawnNewBall();
        }
    }
}
