package sep.fimball.model.handler;

import javafx.beans.property.ReadOnlyIntegerProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import org.junit.Test;
import sep.fimball.general.data.Vector2;
import sep.fimball.model.blueprint.base.BaseElementType;
import sep.fimball.model.input.data.KeyBinding;
import sep.fimball.model.input.manager.KeyEventArgs;
import sep.fimball.model.media.Animation;
import sep.fimball.model.media.BaseMediaElement;

import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.junit.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;

//TODO - Muss an den neuen TiltHandler angepasst werden.
/**
 * Testet die Klasse TiltHandler.
 */
public class TiltHandlerTest
{
    /**
     * Ein Test-Spieler.
     */
    private HandlerPlayer player1 = getPlayer();

    private boolean tiltActivated;

    /**
     * Testet, ob das Aktivieren des TiltHandlers funktioniert.
     */
    @Test
    public void activateTiltHandlerTest()
    {
        HandlerGameSession gameSession = getTestHandlerGameSession();
        InputModifier inputModifier = mock(InputModifier.class);
        doAnswer(invocationOnMock -> {
            tiltActivated = true;
            return null;
        }).when(inputModifier).setKeyEventsActivated(anyBoolean());

        TiltHandler test = new TiltHandler(gameSession, inputModifier);

        test.activateUserHandler(new KeyEventArgs(KeyBinding.NUDGE_LEFT, KeyEventArgs.KeyChangedToState.DOWN, true));
        assertThat("Es wurde noch kein Tilt aktiviert", tiltActivated, is(false));
        test.activateUserHandler(new KeyEventArgs(KeyBinding.NUDGE_LEFT, KeyEventArgs.KeyChangedToState.DOWN, true));
        assertThat("Es wurde noch kein Tilt aktiviert", tiltActivated, is(false));
        test.activateUserHandler(new KeyEventArgs(KeyBinding.NUDGE_LEFT, KeyEventArgs.KeyChangedToState.DOWN, true));
        assertThat("Es wurde noch kein Tilt aktiviert", tiltActivated, is(false));
        test.activateUserHandler(new KeyEventArgs(KeyBinding.NUDGE_LEFT, KeyEventArgs.KeyChangedToState.DOWN, true));
        assertThat("Es wurde noch kein Tilt aktiviert", tiltActivated, is(false));
        test.activateUserHandler(new KeyEventArgs(KeyBinding.NUDGE_LEFT, KeyEventArgs.KeyChangedToState.DOWN, true));
        assertThat("Es wurde noch kein Tilt aktiviert", tiltActivated, is(false));
        test.activateUserHandler(new KeyEventArgs(KeyBinding.NUDGE_LEFT, KeyEventArgs.KeyChangedToState.DOWN, true));
        assertThat("Der Tilt wurde für Spieler 1 aktiviert", tiltActivated, is(true));
        tiltActivated = false;
        assertThat("Das Zählen des Anstoßens wurde zurückgesetzt", tiltActivated, is(false));
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
            public void setBallLost()
            {

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
                return new SimpleIntegerProperty(0);
            }

            @Override
            public void addPoints(int pointReward)
            {

            }

            @Override
            public void removeOneReserveBall()
            {

            }
        };
    }
}
