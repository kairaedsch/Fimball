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
import static org.hamcrest.junit.MatcherAssert.assertThat;
import static org.mockito.Mockito.*;

public class PhysicsHandlerTest
{
    private static final long MAX_TEST_DURATION =1000 ;
    PhysicsHandler test;

    boolean leftFlipperRotated = false;

    int numberOfCalls = 0;
    boolean listsEmpty = true;
    private Object monitor = new Object();

    @Test
    public void stopReactingToUserInputTest () throws InterruptedException
    {
        init();
        /*test.startTicking();
        InputManager.getSingletonInstance().addKeyEvent(new KeyEvent(KeyEvent.KEY_PRESSED, "A", KeyCode.A.name(), Settings.getSingletonInstance().keyBindingsMapProperty().get(KeyBinding
                .LEFT_FLIPPER), false, false,
                false, false));
        synchronized (monitor)
        {
            while (!leftFlipperRotated)
            {
                monitor.wait(MAX_TEST_DURATION);
            }
        }
        test.stopTicking();
        assertThat(leftFlipperRotated, is(true));*/

        test.stopReactingToUserInput();
        leftFlipperRotated = false;
        InputManager.getSingletonInstance().addKeyEvent(new KeyEvent(KeyEvent.KEY_PRESSED, "A", KeyCode.A.name(), Settings.getSingletonInstance().keyBindingsMapProperty().get(KeyBinding
                .LEFT_FLIPPER), false, false,
                false, false));

        test.startTicking();
        synchronized (monitor)
        {
            monitor.wait(MAX_TEST_DURATION);
        }
        test.stopTicking();
        assertThat(leftFlipperRotated, is(false));
    }

    @Test
    public void tickingTest() throws InterruptedException
    {
        init();
        test.startTicking();
        synchronized (monitor)
        {
            monitor.wait(MAX_TEST_DURATION);
        }
        test.stopTicking();
        assertThat(numberOfCalls, is(63));
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

        BallPhysicsElement mockedBall = mock(BallPhysicsElement.class);
        when(mockedBall.getPosition()).thenReturn(new Vector2(0,0));

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

        test =  new PhysicsHandler(mockedElements, mockedGameSession, 0, mockedBall,mockedLeftFlippers, mockedRightFlippers);
    }

}
