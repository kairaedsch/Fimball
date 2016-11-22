package sep.fimball.model;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import org.junit.After;
import org.junit.Ignore;
import org.junit.Test;
import sep.fimball.model.blueprint.pinballmachine.PinballMachine;
import sep.fimball.model.blueprint.pinballmachine.PinballMachineManager;
import sep.fimball.model.blueprint.settings.Settings;
import sep.fimball.model.game.GameSession;
import sep.fimball.model.handler.*;
import sep.fimball.model.input.InputManager;
import sep.fimball.model.input.KeyBinding;

import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by marc on 16.11.16.
 */
@Ignore
public class ReserveBallsAndPlayerChangeTest {
    private static String[] players = new String[] {"tester", "test"};
    private PinballMachine automaton;
    private GameSession game;
    private static final long WAITING_TIME = 2000;
    private static final long KEY_HOLDING_TIME = 1000;
    private static final long MAX_TEST_TIME = 120000;
    private static Object monitor = new Object();

    @Test(timeout = MAX_TEST_TIME)
    public void testReserveBalls() throws InterruptedException {
        new JFXPanel();
        Platform.runLater(()-> initGameSession());
        Platform.runLater(()->usePlunger());
        synchronized (monitor) {
            monitor.wait(MAX_TEST_TIME);
        }
        waitForRoundChange();
        assertEquals(3, game.getCurrentPlayer().ballsProperty().get());
        assertEquals("test", game.getCurrentPlayer().getName());
        Platform.runLater(()->usePlunger());
        synchronized (monitor) {
            monitor.wait(MAX_TEST_TIME);
        }
        waitForRoundChange();
        assertEquals(2, game.getCurrentPlayer().ballsProperty().get());
        assertEquals("tester", game.getCurrentPlayer().getName());
        Platform.runLater(()->usePlunger());
        synchronized (monitor) {
            monitor.wait(MAX_TEST_TIME);
        }
        waitForRoundChange();
        assertEquals(2, game.getCurrentPlayer().ballsProperty().get());
        assertEquals("test", game.getCurrentPlayer().getName());
    }

    @After
    public void stopThreads () {
        Platform.runLater(()->{
            game.stopPhysics();
            game.stopTimeline();
        });
        Platform.exit();
    }

    private void initGameSession() {
        automaton = PinballMachineManager.getInstance().pinballMachinesProperty().stream().filter((PinballMachine machine)->machine.getID().equals("0")).findFirst().get();
        game = new GameSession(automaton, players);
        List<Handler> triggers = HandlerFactory.generateAllHandlers(game);
        Handler ballLostChecker = new Handler();
        ballLostChecker.setGameHandler((GameEvent gameEvent)->{
            if(gameEvent == GameEvent.BALL_LOST) {
                synchronized (monitor) {
                    monitor.notify();
                }
            }
        });
        triggers.add(ballLostChecker);
        game.setTriggers(triggers);
    }

    private void waitForRoundChange() throws InterruptedException {
        Thread.sleep(WAITING_TIME);
    }

    private void usePlunger() {
        KeyCode plungerKey = Settings.getSingletonInstance().keyBindingsMapProperty().get(KeyBinding.PLUNGER);
        InputManager.getSingletonInstance().addKeyEvent(new KeyEvent(KeyEvent.KEY_PRESSED, " ", plungerKey.name(), plungerKey, false, false, false, false));
        try {
            Thread.sleep(KEY_HOLDING_TIME);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        InputManager.getSingletonInstance().addKeyEvent(new KeyEvent(KeyEvent.KEY_RELEASED, " ", plungerKey.name(), plungerKey, false, false, false, false));
    }
}
