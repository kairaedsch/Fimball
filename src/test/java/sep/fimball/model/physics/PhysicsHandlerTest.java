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
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.junit.MatcherAssert.assertThat;
import static org.mockito.Mockito.*;

public class PhysicsHandlerTest
{
    private static final long MAX_TEST_DURATION = 1000;
    PhysicsHandler test;

    boolean leftFlipperRotated = false;

    int numberOfCalls = 0;
    private Object monitor = new Object();
    private boolean ballLost = false;
    double yPosition = 0;
    Vector2 mockedVector;

    @Test
    public void stopReactingToUserInputTest() throws InterruptedException
    {
        init();
        test.startTicking();
        InputManager.getSingletonInstance().addKeyEvent(new KeyEvent(KeyEvent.KEY_PRESSED, "A", KeyCode.A.name(), Settings
                .getSingletonInstance().keyBindingsMapProperty().get(KeyBinding.LEFT_FLIPPER), false, false,
                false, false));
        synchronized (monitor)
        {
            while (!leftFlipperRotated)
            {
                monitor.wait(MAX_TEST_DURATION);
            }
        }
        test.stopTicking();
        assertThat(leftFlipperRotated, is(true));

        test.stopReactingToUserInput();
        leftFlipperRotated = false;
        test.startTicking();
        InputManager.getSingletonInstance().addKeyEvent(new KeyEvent(KeyEvent.KEY_PRESSED, "A", KeyCode.A.name(), Settings
                .getSingletonInstance().keyBindingsMapProperty().get(KeyBinding.LEFT_FLIPPER), false, false,
                false, false));
        synchronized (monitor)
        {
            monitor.wait(MAX_TEST_DURATION);
        }
        test.stopTicking();
        assertThat(leftFlipperRotated, is(false));
    }

    @Test
    public void ballLostTest() throws InterruptedException
    {
        init();
        yPosition = 19;
        test.startTicking();
        synchronized (monitor)
        {
            while (!ballLost)
            {
                monitor.wait(MAX_TEST_DURATION);
            }
        }
        test.stopTicking();
        assertThat(yPosition, greaterThanOrEqualTo(50.0));
        assertThat(ballLost, is(true));
    }

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
            synchronized (monitor)
            {
                ballLost = invocationOnMock.getArgument(0);
                if (ballLost)
                {
                    monitor.notify();
                }
            }
            return null;
        }).when(mockedGameSession).setBallLost(anyBoolean());

        BallPhysicsElement mockedBall = mock(BallPhysicsElement.class);
        doAnswer(invocationOnMock ->
        {
            Vector2 t = (invocationOnMock.getArgument(0));
            yPosition = t.getY();
            return null;
        }).when(mockedBall).setPosition(any(Vector2.class));

        doAnswer(invocationOnMock ->
        {
            yPosition++;
            return null;
        }).when(mockedBall).update(anyDouble());

        mockedVector = mock(Vector2.class);
        when(mockedVector.getY()).thenAnswer(invocationOnMock -> yPosition);
        when(mockedBall.getPosition()).thenReturn(mockedVector);

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

        test = new PhysicsHandler(mockedElements, mockedGameSession, 50, mockedBall, mockedLeftFlippers, mockedRightFlippers);
    }

}
