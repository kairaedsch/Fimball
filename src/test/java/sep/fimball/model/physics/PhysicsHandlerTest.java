package sep.fimball.model.physics;

import org.junit.Test;
import sep.fimball.general.data.Vector2;
import sep.fimball.model.game.GameSession;
import sep.fimball.model.physics.element.BallPhysicsElement;
import sep.fimball.model.physics.element.PhysicsElement;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.junit.MatcherAssert.assertThat;
import static org.mockito.Mockito.*;

/**
 * Testet die Klasse PhysicsHandler.
 */
public class PhysicsHandlerTest
{
    /**
     * Die maximale Test-Dauer.
     */
    private static final long MAX_TEST_DURATION = 1000;

    /**
     * Der PhysicsHandler, der getestet wird.
     */
    private PhysicsHandler test;

    /**
     * Der Monitor, über den die Events an die GameSession synchronisiert werden..
     */
    private final Object eventMonitor = new Object();

    /**
     * Der Monitor, über den Informationen über den Verlust der Kugel synchronisiert werden.
     */
    private final Object ballLostMonitor = new Object();

    /**
     * Gibt an, ob die Kugel als verloren gilt.
     */
    private boolean ballLost = false;

    /**
     * Die Kugel-Position.
     */
    private Vector2 ballPosition = new Vector2(0, 19);

    /**
     * Gibt an, ob ein Element die Collision mit dem Ball geprüft hat.
     */
    private boolean collisionCheckWithBall = false;

    /**
     * Gibt an, ob sich an der Kugel etwas verändert hat.
     */
    private boolean ballChanged = false;

    /**
     * Gibt an, ob die ElementEventArgs-Liste, die an die Game-Session übergeben wird, leer ist.
     */
    private boolean elementEventArgsEmpty = true;

    /**
     * Testet, ob erkannt wird, dass die Kugel verloren ist.
     *
     * @throws InterruptedException wenn der Monitor unterbrochen wird.
     */
    @Test
    public void ballLostTest() throws InterruptedException
    {
        init();
        test.startTicking();
        synchronized (ballLostMonitor)
        {
            ballLostMonitor.wait(MAX_TEST_DURATION);
        }
        test.stopTicking();
        assertThat("Die Kugel ist am unteren Rand des Spielfelds", ballPosition.getY(), greaterThanOrEqualTo(50.0));
        assertThat("Die Kugel wird als verloren angesehen", ballLost, is(true));
    }

    /**
     * Testet, ob Kollisionen überprüft werden.
     *
     * @throws InterruptedException wenn der Monitor unterbrochen wird.
     */
    @Test
    public void checkCollisionTest() throws InterruptedException
    {
        init();
        test.startTicking();
        synchronized (eventMonitor)
        {
            eventMonitor.wait(MAX_TEST_DURATION);
        }
        test.stopTicking();
        assertThat("Das Element im Spiel hat auf eine Kollision mit der Kugel geprüft", collisionCheckWithBall, is(true));
        assertThat("Die ElementEventArgs wurden an die GameSession übergeben", elementEventArgsEmpty, is(false));
    }

    /**
     * Initialisiert die Test-Werte.
     */
    private void init()
    {
        GameSession mockedGameSession = getGameSession();
        BallPhysicsElement mockedBall = getBall();
        PhysicsElement mockedElement = getElement(mockedBall);

        List<PhysicsElement> mockedElements = new ArrayList<>();
        mockedElements.add(mockedBall);
        mockedElements.add(mockedElement);

        test = new PhysicsHandler();
        test.init(mockedElements, mockedGameSession, 50, mockedBall);
    }

    /**
     * Gibt ein Test-PhysicsElement zurück.
     *
     * @param mockedBall Ein Test-BallPhysicsElement.
     * @return Ein Test-PhysicsElement.
     */
    private PhysicsElement getElement(BallPhysicsElement mockedBall)
    {
        PhysicsElement mockedElement = mock(PhysicsElement.class);

        doAnswer(invocationOnMock ->
        {
            collisionCheckWithBall = true;
            return null;
        }).when(mockedElement).checkCollision(anyList(), eq(mockedBall));
        return mockedElement;
    }

    /**
     * Gibt eine Test-GameSession zurück.
     *
     * @return Eine Test-GameSession.
     */
    private GameSession getGameSession()
    {
        GameSession mockedGameSession = mock(GameSession.class);
        doAnswer(invocationOnMock ->
        {
            List eventArgs = invocationOnMock.getArgument(1);
            elementEventArgsEmpty = eventArgs.isEmpty();
            synchronized (eventMonitor)
            {
                eventMonitor.notify();
            }
            return null;
        }).when(mockedGameSession).addEventArgs(anyList(), anyList());

        doAnswer(invocationOnMock ->
        {
            ballLost = true;
                synchronized (ballLostMonitor)
                {
                    ballLostMonitor.notify();
                }
            return null;
        }).when(mockedGameSession).setBallLost();
        return mockedGameSession;
    }

    /**
     * Gibt ein Test-BallPhysicsElement zurück.
     *
     * @return Ein Test-BallPhysicsElement.
     */
    private BallPhysicsElement getBall()
    {
        BallPhysicsElement mockedBall = mock(BallPhysicsElement.class);
        doAnswer(invocationOnMock ->
        {
            ballPosition = invocationOnMock.getArgument(0);
            return null;
        }).when(mockedBall).setPosition(any(Vector2.class));

        doAnswer(invocationOnMock ->
        {
            mockedBall.setPosition(mockedBall.getPosition().plus(new Vector2(0, 1)));
            ballChanged = true;
            return null;
        }).when(mockedBall).update(anyDouble());

        doAnswer(invocationOnMock -> ballChanged).when(mockedBall).hasChanged();

        doAnswer(invocationOnMock -> ballPosition).when(mockedBall).getPosition();
        return mockedBall;
    }

}
