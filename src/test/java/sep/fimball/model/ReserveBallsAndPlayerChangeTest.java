package sep.fimball.model;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import org.junit.Before;
import org.junit.Test;
import sep.fimball.model.blueprint.pinballmachine.PinballMachine;
import sep.fimball.model.blueprint.pinballmachine.PinballMachineManager;
import sep.fimball.model.blueprint.settings.Settings;
import sep.fimball.model.game.GameSession;
import sep.fimball.model.input.InputManager;
import sep.fimball.model.input.KeyBinding;
import sep.fimball.model.trigger.GameHandler;
import sep.fimball.model.trigger.Handler;
import sep.fimball.model.trigger.HandlerFactory;

import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by marc on 16.11.16.
 */
//@Ignore
public class ReserveBallsAndPlayerChangeTest {
    private static String[] players = new String[] {"tester", "test"};
    private PinballMachine automaton;
    private GameSession game;
    private static final long waitingTime = 2000;
    private static final long keyHoldingTime = 1000;
    private boolean ballLost = false;

    @Before
    public void initGame() {
        automaton = PinballMachineManager.getInstance().pinballMachinesProperty().stream().filter((PinballMachine machine)->machine.getID().equals("testmachine-2")).findFirst().get();
        game = new GameSession(automaton, players);
        List<Handler> triggers = HandlerFactory.generateAllHandlers(game);
        Handler ballLostChecker = new Handler();
        ballLostChecker.setGameHandler(new BallLostHandler(game));
        triggers.add(ballLostChecker);
    }

    @Test(timeout = 120000)
    public void testReserveBalls() {
        usePlunger();
        while (!ballLost) { }
        waitForStartOfNewSession();
        assertEquals(3, game.getCurrentPlayer().ballsProperty().get());
        assertEquals("test", game.getCurrentPlayer().getName());
        ballLost = false;
        usePlunger();
        while (!ballLost) { }
        waitForStartOfNewSession();
        assertEquals(2, game.getCurrentPlayer().ballsProperty().get());
        assertEquals("tester", game.getCurrentPlayer().getName());
        ballLost = false;
        usePlunger();
        while (!ballLost) { }
        waitForStartOfNewSession();
        assertEquals(2, game.getCurrentPlayer().ballsProperty().get());
        assertEquals("test", game.getCurrentPlayer().getName());
    }

    private void waitForStartOfNewSession() {
        try {
            Thread.sleep(waitingTime);
        } catch (InterruptedException e) { }
    }

    private void usePlunger() {
        KeyCode plungerKey = Settings.getSingletonInstance().keyBindingsMapProperty().get(KeyBinding.PLUNGER);
        InputManager.getSingletonInstance().addKeyEvent(new KeyEvent(KeyEvent.KEY_PRESSED, " ", plungerKey.name(), plungerKey, false, false, false, false));
        try {
            Thread.sleep(keyHoldingTime);
        } catch (InterruptedException e) { }
        InputManager.getSingletonInstance().addKeyEvent(new KeyEvent(KeyEvent.KEY_RELEASED, " ", plungerKey.name(), plungerKey, false, false, false, false));
    }

    private class BallLostHandler implements GameHandler
    {
        private GameSession game;

        public BallLostHandler(GameSession game)
        {
            this.game = game;
        }

        @Override
        public void activateGameHandler()
        {
            ballLost = true;
        }
    }
}
