package sep.fimball.model.physics;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import org.junit.Test;
import sep.fimball.general.data.Vector2;
import sep.fimball.model.blueprint.settings.Settings;
import sep.fimball.model.game.GameSession;
import sep.fimball.model.input.data.KeyBinding;
import sep.fimball.model.input.manager.InputManager;
import sep.fimball.model.physics.element.BallPhysicsElement;
import sep.fimball.model.physics.element.FlipperPhysicsElement;
import sep.fimball.model.physics.element.PhysicsElement;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.lessThan;
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
     * Gibt an, ob der linke Flipper gedreht wurde.
     */
    private boolean leftFlipperRotated = false;

    /**
     * Der Monitor, über den synchronisiert wird.
     */
    private final Object monitor = new Object();

    /**
     * Gibt an, ob die Kugel als verloren gilt.
     */
    private boolean ballLost = false;

    Vector2 ballPosition = new Vector2(0,19);

    Vector2 ballVelocity = new Vector2(0,0);

    /**
     * Testet, ob das An- und Ausschalten des Reagierens auf UserInput funktioniert.
     *
     * @throws InterruptedException wenn der Monitor unterbrochen wird.
     */
    @Test
    public void stopReactingToUserInputTest() throws InterruptedException
    {
        init();
        test.startTicking();
        InputManager.getSingletonInstance().addKeyEvent(new KeyEvent(KeyEvent.KEY_PRESSED, "A", KeyCode.A.name(), Settings
                .getSingletonInstance().getKeyCode(KeyBinding.LEFT_FLIPPER), false, false,
                false, false));
        synchronized (monitor)
        {
            monitor.wait(MAX_TEST_DURATION);
        }
        test.stopTicking();
        assertThat(leftFlipperRotated, is(true));

        test.stopReactingToUserInput();
        leftFlipperRotated = false;
        test.startTicking();
        InputManager.getSingletonInstance().addKeyEvent(new KeyEvent(KeyEvent.KEY_PRESSED, "A", KeyCode.A.name(), Settings
                .getSingletonInstance().getKeyCode(KeyBinding.RIGHT_FLIPPER), false, false,
                false, false));
        synchronized (monitor)
        {
            monitor.wait(MAX_TEST_DURATION);
        }
        test.stopTicking();
        assertThat(leftFlipperRotated, is(false));
    }

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
        synchronized (monitor)
        {
            while (!ballLost)
            {
                monitor.wait(MAX_TEST_DURATION);
            }
        }
        test.stopTicking();
        assertThat(ballPosition.getY(), greaterThanOrEqualTo(50.0));
        assertThat(ballLost, is(true));
    }

    @Test
    public void nudgeTest() throws InterruptedException
    {
        init();
        test.startTicking();
        InputManager.getSingletonInstance().addKeyEvent(new KeyEvent(KeyEvent.KEY_PRESSED, "A", KeyCode.A.name(), Settings
                .getSingletonInstance().getKeyCode(KeyBinding.NUDGE_LEFT), false, false,
                false, false));
        synchronized (monitor) {
            monitor.wait(MAX_TEST_DURATION);
        }
        test.stopTicking();
        assertThat(ballVelocity.getX(), greaterThan(0.0));

        ballVelocity = new Vector2(0,0);

        test.startTicking();
        InputManager.getSingletonInstance().addKeyEvent(new KeyEvent(KeyEvent.KEY_PRESSED, "A", KeyCode.A.name(), Settings
                .getSingletonInstance().getKeyCode(KeyBinding.NUDGE_RIGHT), false, false,
                false, false));
        synchronized (monitor) {
            monitor.wait(MAX_TEST_DURATION);
        }
        test.stopTicking();
        assertThat(ballVelocity.getX(), lessThan(0.0));
    }

    /**
     * Initialisiert die Test-Werte.
     * TODO comments.
     */
    private void init()
    {
        PhysicsElement mockedElement = mock(PhysicsElement.class);
        List<PhysicsElement> mockedElements = new ArrayList<>();
        mockedElements.add(mockedElement);
        GameSession mockedGameSession = mock(GameSession.class);
        doAnswer(invocationOnMock ->
        {
            //TODO
            return null;
        }).when(mockedGameSession).addEventArgs(anyList(), anyList());

        doAnswer(invocationOnMock ->
        {
            ballLost = invocationOnMock.getArgument(0);
            if(ballLost)
            {
                synchronized (monitor)
                {
                    monitor.notify();
                }
            }
            return null;
        }).when(mockedGameSession).setBallLost(anyBoolean());

        BallPhysicsElement mockedBall = mock(BallPhysicsElement.class);
        doAnswer(invocationOnMock ->
        {
            ballPosition = invocationOnMock.getArgument(0);
            return null;
        }).when(mockedBall).setPosition(any(Vector2.class));

        doAnswer(invocationOnMock ->
        {
            mockedBall.setPosition(mockedBall.getPosition().plus(new Vector2(0,1)));
            return null;
        }).when(mockedBall).update(anyDouble());

        doAnswer(invocationOnMock ->
                ballPosition).when(mockedBall).getPosition();

        doAnswer(invocationOnMock ->
        {
            ballVelocity = invocationOnMock.getArgument(0);
            synchronized (monitor)
            {
                monitor.notify();
            }
            return null;
        }).when(mockedBall).setVelocity(any(Vector2.class));

        doAnswer(invocationOnMock ->
                ballVelocity).when(mockedBall).getVelocity();

        FlipperPhysicsElement mockedLeftFlipper = mock(FlipperPhysicsElement.class);
        FlipperPhysicsElement mockedRightFlipper = mock(FlipperPhysicsElement.class);

        doAnswer(invocationOnMock ->
        {
            leftFlipperRotated = true;
            synchronized (monitor)
            {
                monitor.notify();
            }
            return null;
        }).when(mockedLeftFlipper).rotateDown();

        List<FlipperPhysicsElement> mockedLeftFlippers = new ArrayList<>();
        List<FlipperPhysicsElement> mockedRightFlippers = new ArrayList<>();

        mockedLeftFlippers.add(mockedLeftFlipper);
        mockedRightFlippers.add(mockedRightFlipper);

        test = new PhysicsHandler();
        test.init(mockedElements, mockedGameSession, 50, mockedBall);
    }

}
