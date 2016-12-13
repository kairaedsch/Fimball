package sep.fimball.model.handler;

import javafx.beans.property.ReadOnlyIntegerProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.junit.MatcherAssert.assertThat;

public class BallLostHandlerTest
{
    int currentPlayer = 0;
    boolean newBallSpawned = false;
    boolean reserveBallRemoved = false;

    @Test
    public void activateGameHandlerTest() {
        BallLostHandler test = new BallLostHandler(getTestHandlerGameSession());
        GameEvent event = GameEvent.BALL_LOST;
        test.activateGameHandler(event);
        assertThat(currentPlayer, is(1));
        assertThat(newBallSpawned, is(true));
        assertThat(reserveBallRemoved, is(true));
    }

    private HandlerGameSession getTestHandlerGameSession() {
        return new HandlerGameSession()
        {
            @Override
            public HandlerPlayer getCurrentPlayer()
            {
                return getTestHandlerPlayer();
            }

            @Override
            public void switchToNextPlayer()
            {
                currentPlayer++;
            }

            @Override
            public void spawnNewBall()
            {
                newBallSpawned = true;
            }

            @Override
            public HandlerWorld getWorld()
            {
                return null;
            }

            @Override
            public ReadOnlyObjectProperty<? extends HandlerGameElement> gameBallProperty()
            {
                return null;
            }

            @Override
            public void activateTilt()
            {

            }
        };
    }

    private HandlerPlayer getTestHandlerPlayer() {
        return new HandlerPlayer()
        {
            @Override
            public ReadOnlyIntegerProperty pointsProperty()
            {
                return null;
            }

            @Override
            public ReadOnlyIntegerProperty ballsProperty()
            {
                return null;
            }

            @Override
            public void addPoints(int pointReward)
            {

            }

            @Override
            public boolean removeOneReserveBall()
            {
                reserveBallRemoved = true;
                return false;
            }
        };
    }

}
