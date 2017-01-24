package sep.fimball.model.handler;

import javafx.beans.property.*;
import org.junit.Test;
import sep.fimball.general.data.Vector2;
import sep.fimball.model.blueprint.base.BaseElementType;
import sep.fimball.model.media.BaseMediaElement;
import sep.fimball.model.media.ElementImage;

import java.util.Optional;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.junit.MatcherAssert.assertThat;

/**
 * Testet die Klasse BallLostHandler.
 */
public class BallLostHandlerTest
{
    /**
     * Ein Test-Spieler.
     */
    private HandlerPlayer player1 = getTestPlayer();

    /**
     * Ein Test-Spieler.
     */
    private HandlerPlayer player2 = getTestPlayer();

    /**
     * Gibt an, ob ein neuer Ball gespawned wurde.
     */
    private boolean newBallSpawned = false;

    /**
     * Testet, ob das Aktivieren des BallLostHandlers funktioniert.
     */
    @Test
    public void activateBallLostHandlerTest()
    {
        HandlerGameSession gameSession = getTestGameSession();
        BallLostHandler test = new BallLostHandler(gameSession);
        GameEvent event = GameEvent.BALL_LOST;
        test.activateGameHandler(event);
        assertThat("Es wurde der Spieler gewechselt", gameSession.getCurrentPlayer(), equalTo(player2));
        assertThat("Es wurde eine neue Kugel gespawned", newBallSpawned, is(true));
        assertThat("Der erste Spieler hat eine Reserve-Kugel weniger", player1.ballsProperty().get(), is(2));
        assertThat("Der zweite Spieler hat noch alle Kugeln", player2.ballsProperty().get(), is(3));
    }

    /**
     * Gibt eine Test-HandlerGameSession zurück.
     *
     * @return Eine Test-HandlerGameSession.
     */
    private HandlerGameSession getTestGameSession()
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
                }
                else
                {
                    currentPlayer = player1;
                }
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
            public ReadOnlyObjectProperty<? extends HandlerBallGameElement> gameBallProperty()
            {
                return new SimpleObjectProperty<>(new HandlerBallGameElement()
                {
                    @Override
                    public void nudge(boolean left)
                    {

                    }

                    @Override
                    public ReadOnlyObjectProperty<Vector2> positionProperty()
                    {
                        return new SimpleObjectProperty<>();
                    }

                    @Override
                    public void setCurrentAnimation(Optional<ElementImage> animation)
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
                });
            }

            @Override
            public void ballLost()
            {

            }
        };
    }

    /**
     * Gibt einen Test-HandlerPlayer zurück.
     *
     * @return Ein Test-HandlerPlayer.
     */
    private HandlerPlayer getTestPlayer()
    {
        return new HandlerPlayer()
        {
            /**
             * Die Reserve-Kugeln des Spielers.
             */
            IntegerProperty balls = new SimpleIntegerProperty(3);

            @Override
            public ReadOnlyIntegerProperty pointsProperty()
            {
                return null;
            }

            @Override
            public ReadOnlyIntegerProperty ballsProperty()
            {
                return balls;
            }

            @Override
            public void addPoints(int pointReward)
            {

            }

            @Override
            public void removeOneReserveBall()
            {
                balls.set(balls.get() - 1);
            }
        };
    }

}
