package sep.fimball.model.handler.light;

import javafx.beans.property.ReadOnlyObjectProperty;
import org.junit.Ignore;
import org.junit.Test;
import sep.fimball.model.handler.*;

public class LightHandlerTest
{
    @Ignore
    @Test
    public void activateGameHandlerTest() {
        HandlerGameSession gameSession = getTestHandlerGameSession();
        //TODO
        //LightHandler test = new LightHandler(gameSession);
        //test.activateGameHandler(GameEvent.BALL_SPAWNED);

    }

    private HandlerGameSession getTestHandlerGameSession() {
        return new HandlerGameSession()
        {
            @Override
            public HandlerPlayer getCurrentPlayer()
            {
                return null;
            }

            @Override
            public void switchToNextPlayer()
            {

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

            }
        };
    }
}
