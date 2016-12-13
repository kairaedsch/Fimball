package sep.fimball.model.handler;

import javafx.beans.property.ReadOnlyIntegerProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import org.junit.Test;
import sep.fimball.general.data.Vector2;
import sep.fimball.model.blueprint.base.BaseElementType;
import sep.fimball.model.media.Animation;
import sep.fimball.model.media.BaseMediaElement;

import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.junit.MatcherAssert.assertThat;

public class ScoreHandlerTest
{


    @Test
    public void activateElementHandlerTest() {
        HandlerGameSession gameSession = getTestHandlerGameSession();
        ScoreHandler test = new ScoreHandler(gameSession);
        test.activateElementHandler(getTestHandlerGameElement(), 0);
        assertThat(gameSession.getCurrentPlayer().pointsProperty().get(), is(12));
        test.activateElementHandler(getTestHandlerGameElement(), 1);
        assertThat(gameSession.getCurrentPlayer().pointsProperty().get(), is(24));

    }

    private HandlerGameSession getTestHandlerGameSession() {
        return new HandlerGameSession()
        {
            HandlerPlayer handlerPlayer = getTestHandlerPlayer();

            @Override
            public HandlerPlayer getCurrentPlayer()
            {
                return handlerPlayer;
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

    private HandlerPlayer getTestHandlerPlayer() {
        return new HandlerPlayer()
        {
            int points;
            @Override
            public ReadOnlyIntegerProperty pointsProperty()
            {
                return new SimpleIntegerProperty(points);
            }

            @Override
            public ReadOnlyIntegerProperty ballsProperty()
            {
                return null;
            }

            @Override
            public void addPoints(int pointReward)
            {
                points += pointReward;
            }

            @Override
            public boolean removeOneReserveBall()
            {
                return false;
            }
        };
    }

    private HandlerGameElement getTestHandlerGameElement() {
        return new HandlerGameElement()
        {
            @Override
            public ReadOnlyObjectProperty<Vector2> positionProperty()
            {
                return null;
            }

            @Override
            public void setCurrentAnimation(Optional<Animation> animation)
            {

            }

            @Override
            public void setHitCount(int hitCount)
            {

            }

            @Override
            public int getHitCount()
            {
                return 0;
            }

            @Override
            public int getPointReward()
            {
                return 12;
            }

            @Override
            public BaseMediaElement getMediaElement()
            {
                return null;
            }

            @Override
            public BaseRuleElement getRuleElement()
            {
                return null;
            }

            @Override
            public BaseElementType getElementType()
            {
                return null;
            }
        };
    }
}
