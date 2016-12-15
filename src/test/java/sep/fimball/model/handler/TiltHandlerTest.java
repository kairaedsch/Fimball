package sep.fimball.model.handler;

import javafx.beans.property.ReadOnlyIntegerProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import org.junit.Test;
import sep.fimball.general.data.Vector2;
import sep.fimball.model.blueprint.base.BaseElementType;
import sep.fimball.model.input.data.KeyBinding;
import sep.fimball.model.input.data.KeyEventType;
import sep.fimball.model.media.Animation;
import sep.fimball.model.media.BaseMediaElement;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.junit.MatcherAssert.assertThat;

/**
 * Testet die Klasse TiltHandler.
 */
public class TiltHandlerTest
{
    /**
     * Gibt an, ob für die jeweiligen Spieler der Tilt aktiviert wurde.
     */
    private Map<HandlerPlayer, Boolean> tiltActivated;

    /**
     * Ein Test-Spieler.
     */
    private HandlerPlayer player1 = getPlayer();

    /**
     * Ein Test-Spieler.
     */
    private HandlerPlayer player2 = getPlayer();

    /**
     * Testet, ob das Aktivieren des TiltHandlers funktioniert.
     */
    @Test
    public void activateTiltHandlerTest()
    {
        tiltActivated = new HashMap<>();
        tiltActivated.put(player1, false);
        tiltActivated.put(player2, false);
        HandlerGameSession gameSession = getTestHandlerGameSession();
        TiltHandler test = new TiltHandler(gameSession);

        test.activateUserHandler(KeyBinding.NUDGE_LEFT, KeyEventType.DOWN);
        assertThat("Es wurde noch kein Tilt aktiviert", tiltActivated.get(player1), is(false));
        test.activateUserHandler(KeyBinding.NUDGE_LEFT, KeyEventType.DOWN);
        assertThat("Es wurde noch kein Tilt aktiviert", tiltActivated.get(player1), is(false));
        test.activateUserHandler(KeyBinding.NUDGE_LEFT, KeyEventType.DOWN);
        assertThat("Es wurde noch kein Tilt aktiviert", tiltActivated.get(player1), is(false));
        test.activateUserHandler(KeyBinding.NUDGE_LEFT, KeyEventType.DOWN);
        assertThat("Es wurde noch kein Tilt aktiviert", tiltActivated.get(player1), is(false));
        test.activateUserHandler(KeyBinding.NUDGE_LEFT, KeyEventType.DOWN);
        assertThat("Es wurde noch kein Tilt aktiviert", tiltActivated.get(player1), is(false));
        test.activateUserHandler(KeyBinding.NUDGE_LEFT, KeyEventType.DOWN);
        assertThat("Der Tilt wurde für Spieler 1 aktiviert", tiltActivated.get(player1), is(true));
        gameSession.switchToNextPlayer();
        assertThat("Der Tilt wurde nicht für Spieler 2 aktiviert", tiltActivated.get(player2), is(false));
    }

    /**
     * Gibt eine Test-HandlerGameSession zurück.
     *
     * @return Eine Test-HandlerGameSession.
     */
    private HandlerGameSession getTestHandlerGameSession()
    {
        return new HandlerGameSession()
        {
            /**
             * Der aktuelle Spieler.
             */
            HandlerPlayer currentPlayer = player1;

            @Override
            public HandlerPlayer getCurrentPlayer()
            {
                return currentPlayer;
            }

            @Override
            public void switchToNextPlayer()
            {
                if (currentPlayer == player1)
                {
                    currentPlayer = player2;
                } else
                {
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
            public ReadOnlyObjectProperty<? extends HandlerBallGameElement> gameBallProperty()
            {
                return new SimpleObjectProperty<>(getBall());
            }

            @Override
            public void activateTilt()
            {
                tiltActivated.put(currentPlayer, true);
            }
        };
    }


    /**
     * Gibt ein Test-HandlerBallGameElement zurück.
     *
     * @return Ein Test-HandlerBallGameElement.
     */
    private HandlerBallGameElement getBall()
    {
        return new HandlerBallGameElement()
        {
            @Override
            public void nudge(boolean left)
            {

            }

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
                return 0;
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

    /**
     * Gibt einen Test-HandlerPlayer zurück.
     *
     * @return Ein Test-HandlerPlayer.
     */
    private HandlerPlayer getPlayer()
    {
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
