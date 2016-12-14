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

/**
 * Testet die Klasse ScoreHandler.
 */
public class ScoreHandlerTest
{
    /**
     * Testet, ob das Aktivieren des HitHandlers funktioniert.
     */
    @Test
    public void activateScoreHandlerTest()
    {
        HandlerGameSession gameSession = getGameSession();
        ScoreHandler test = new ScoreHandler(gameSession);
        test.activateElementHandler(getElement(), 0);
        assertThat(gameSession.getCurrentPlayer().pointsProperty().get(), is(12));
        test.activateElementHandler(getElement(), 1);
        assertThat(gameSession.getCurrentPlayer().pointsProperty().get(), is(24));

    }

    /**
     * Gibt eine Test-HandlerGameSession zurück.
     *
     * @return Eine Test-HandlerGameSession.
     */
    private HandlerGameSession getGameSession()
    {
        return new HandlerGameSession()
        {
            /**
             * Der aktuelle Spieler.
             */
            HandlerPlayer currentPlayer = getPlayer();

            @Override
            public HandlerPlayer getCurrentPlayer()
            {
                return currentPlayer;
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

    /**
     * Gibt einen Test-HandlerPlayer zurück.
     * @return Einen Test-HandlerPlayer.
     */
    private HandlerPlayer getPlayer()
    {
        return new HandlerPlayer()
        {
            /**
             * Die Punkte des Spielers.
             */
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

    /**
     * Gibt ein Test-HandlerGameElement zurück.
     * @return Ein Test-HandlerGameElement.
     */
    private HandlerGameElement getElement()
    {
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
