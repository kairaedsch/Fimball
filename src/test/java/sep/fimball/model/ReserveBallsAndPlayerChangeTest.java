package sep.fimball.model;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
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
    private static final long waitingTime = 2000;
    private static final long keyHoldingTime = 1000;
    private volatile boolean ballLost = false;

    @Test(timeout = 12000)
    public void testReserveBalls() {
        new JFXPanel();
        Platform.runLater(()-> initGameSession());
        Platform.runLater(()->usePlunger());
        while (!ballLost) { }
        waitForRoundChange();
        assertEquals(3, game.getCurrentPlayer().ballsProperty().get());
        assertEquals("test", game.getCurrentPlayer().getName());
        ballLost = false;
        Platform.runLater(()->usePlunger());
        while (!ballLost) { }
        waitForRoundChange();
        assertEquals(2, game.getCurrentPlayer().ballsProperty().get());
        assertEquals("tester", game.getCurrentPlayer().getName());
        ballLost = false;
        Platform.runLater(()->usePlunger());
        while (!ballLost) { }
        waitForRoundChange();
        assertEquals(2, game.getCurrentPlayer().ballsProperty().get());
        assertEquals("test", game.getCurrentPlayer().getName());
        Platform.runLater(()->{
            game.stopPhysics();
            game.stopTimeline();
        });
    }

    private void initGameSession() {
        automaton = PinballMachineManager.getInstance().pinballMachinesProperty().stream().filter((PinballMachine machine)->machine.getID().equals("testmachine2")).findFirst().get();
        game = new GameSession(automaton, players);
        List<Handler> triggers = HandlerFactory.generateAllHandlers(game);
        Handler ballLostChecker = new Handler();
        ballLostChecker.setGameHandler((GameEvent gameEvent)->{
            if(gameEvent == GameEvent.BALL_LOST) {
                ballLost = true;
            }
        });
        triggers.add(ballLostChecker);
        game.setTriggers(triggers);
    }

    private void waitForRoundChange() {
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
}
