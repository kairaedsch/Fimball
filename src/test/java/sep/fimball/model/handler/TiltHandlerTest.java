package sep.fimball.model.handler;

import javafx.beans.property.ReadOnlyIntegerProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.junit.MatcherAssert.assertThat;

public class TiltHandlerTest
{
    Map<HandlerPlayer, Boolean> tiltActivated;
    HandlerPlayer player1 = getTestHandlerPlayer();
    HandlerPlayer player2 = getTestHandlerPlayer();

    @Test
    public void activateGameHandlerTest() {
        tiltActivated = new HashMap<>();
        tiltActivated.put(player1, false);
        tiltActivated.put(player2, false);
        HandlerGameSession gameSession = getTestHandlerGameSession();
        TiltHandler test = new TiltHandler(gameSession);
        test.activateGameHandler(GameEvent.NUDGE);
        assertThat(tiltActivated.get(player1), is(false));
        test.activateGameHandler(GameEvent.NUDGE);
        assertThat(tiltActivated.get(player1), is(false));
        test.activateGameHandler(GameEvent.NUDGE);
        assertThat(tiltActivated.get(player1), is(false));
        test.activateGameHandler(GameEvent.NUDGE);
        assertThat(tiltActivated.get(player1), is(false));
        test.activateGameHandler(GameEvent.NUDGE);
        assertThat(tiltActivated.get(player1), is(false));
        test.activateGameHandler(GameEvent.NUDGE);
        assertThat(tiltActivated.get(player1), is(true));
        gameSession.switchToNextPlayer();
        assertThat(tiltActivated.get(player2), is(false));
    }

    private HandlerGameSession getTestHandlerGameSession() {
        return new HandlerGameSession()
        {
            HandlerPlayer currentPlayer = player1;
            @Override
            public HandlerPlayer getCurrentPlayer()
            {
                return currentPlayer;
            }

            @Override
            public void switchToNextPlayer()
            {
                if (currentPlayer == player1) {
                    currentPlayer = player2;
                } else {
                    currentPlayer = player1;
                }
            }

            @Override
            public void spawnNewBall()
            {

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
                tiltActivated.put(currentPlayer, true);
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
                return false;
            }
        };
    }
}
