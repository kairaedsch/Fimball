package sep.fimball.model.handler.light;

import javafx.beans.property.ListProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import org.junit.ClassRule;
import org.junit.Test;
import sep.fimball.JavaFXThreadingRule;
import sep.fimball.general.data.Vector2;
import sep.fimball.model.blueprint.base.BaseElementType;
import sep.fimball.model.game.GameElement;
import sep.fimball.model.handler.*;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.junit.MatcherAssert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class LightHandlerTest
{
    @ClassRule
    public static JavaFXThreadingRule javafxRule = new JavaFXThreadingRule();

    private static final int MAX_TIME_OUT = 8000;
    private boolean statusAsked = false;

    private Object monitor = new Object();

    @Test
    public void activateGameHandlerTest() throws InterruptedException
    {
        HandlerGameSession gameSession = getTestHandlerGameSession();
        List<LightChanger> lightChangers = new ArrayList<>();
        LightChanger testLightChanger = getLightChanger(false);
        lightChangers.add(testLightChanger);
        LightHandler test = new LightHandler(gameSession, lightChangers);
        test.activateGameHandler(GameEvent.BALL_SPAWNED);
        synchronized (monitor)
        {
            monitor.wait(MAX_TIME_OUT);
        }

        assertThat(statusAsked, is(true));

    }

    private HandlerGameSession getTestHandlerGameSession()
    {
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
                return getHandlerWorld();
            }

            @Override
            public ReadOnlyObjectProperty<? extends HandlerBallGameElement> gameBallProperty()
            {
                return null;
            }

            @Override
            public void activateTilt()
            {

            }
        };
    }

    private HandlerWorld getHandlerWorld()
    {
        return () ->
        {
            ListProperty<HandlerGameElement> gameElements = new SimpleListProperty<>(FXCollections.observableArrayList());
            gameElements.add(getLight());
            return new SimpleListProperty<>(gameElements);
        };
    }

    private LightChanger getLightChanger(boolean revertedAnimation)
    {
        return new LightChanger(revertedAnimation)
        {
            @Override
            protected boolean determineLightStatus(Vector2 position, long delta)
            {

                synchronized (monitor)
                {
                    statusAsked = true;
                    System.out.println("determine");
                    monitor.notify();
                }
                return false;
            }
        };
    }

    private GameElement getLight()
    {
        GameElement gameElement = mock(GameElement.class);
        when(gameElement.getElementType()).thenReturn(BaseElementType.LIGHT);
        return gameElement;
    }
}
