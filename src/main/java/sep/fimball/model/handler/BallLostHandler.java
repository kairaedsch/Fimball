package sep.fimball.model.handler;

import static sep.fimball.general.data.Config.BALL_LOST_TOLERANCE;

/**
 * Der BallLostHandler reagiert auf GameEvent.BALL_LOST Events und kümmert sich um das Wechseln zwischen zwei Spielern bei einer Partie.
 */
public class BallLostHandler implements GameHandler
{
    /**
     * Die GameSession, welche von dem Handler beeinflusst wird.
     */
    private HandlerGameSession handlerGameSession;

    /**
     * Gibt an, ob das ballLost-Event getriggert wurde.
     */
    private boolean ballLostTriggered;

    /**
     * Erstellt ein neuen BallLostHandler.
     *
     * @param handlerGameSession Die GameSession, welche von dem Handler beeinflusst werden soll.
     */
    BallLostHandler(HandlerGameSession handlerGameSession)
    {
        this.handlerGameSession = handlerGameSession;
        ballLostTriggered = false;

        handlerGameSession.gameBallProperty().addListener((x, xx, newBall) -> setupBallListener(handlerGameSession));

        setupBallListener(handlerGameSession);
    }

    /**
     * Richtet den Listener für den Ball ein, damit erkannt werden kann, wenn der Ball zu tief gefallen ist.
     *
     * @param handlerGameSession Die aktuelle GameSession.
     */
    private void setupBallListener(HandlerGameSession handlerGameSession)
    {
        handlerGameSession.gameBallProperty().get().positionProperty().addListener((x, xx, newPosition) ->
        {
            boolean ballLeftField = newPosition.getY() > handlerGameSession.getWorld().getMaximumYPosition() + BALL_LOST_TOLERANCE;

            if (!ballLostTriggered && ballLeftField)
            {
                ballLostTriggered = true;
                handlerGameSession.ballLost();
            }
            if (!ballLeftField)
            {
                ballLostTriggered = false;
            }
        });
    }

    @Override
    public void activateGameHandler(GameEvent gameEvent)
    {
        if (gameEvent.equals(GameEvent.BALL_LOST))
        {
            handlerGameSession.getCurrentPlayer().removeOneReserveBall();
            handlerGameSession.switchToNextPlayer();
            handlerGameSession.spawnNewBall();
        }
    }
}
