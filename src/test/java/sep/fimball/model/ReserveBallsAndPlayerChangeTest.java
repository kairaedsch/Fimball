package sep.fimball.model;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import sep.fimball.general.data.Highscore;
import sep.fimball.model.blueprint.pinballmachine.PinballMachine;
import sep.fimball.model.blueprint.pinballmachine.PinballMachineManager;
import sep.fimball.model.blueprint.settings.Settings;
import sep.fimball.model.input.InputManager;
import sep.fimball.model.input.KeyBinding;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

/**
 * Created by marc on 16.11.16.
 */
@Ignore
public class ReserveBallsAndPlayerChangeTest {
    private static String[] players = new String[] {"tester", "test"};
    private static PinballMachine automaton;
    private static GameSession game;
    private static final long waitingTime = 30000;
    private static final long keyHoldingTime = 1000;

    @Before
    public static void initGame() {
        // TODO ID_IN_DEN_KONSTRUKTOR_EINFÜGEN_ODER_FRÜHER_IM_LADEPROZESS_ANSETZEN automaton = new PinballMachine("Testautomat", "", new ArrayList<Highscore>());
        // TODO IST_DAS_DANN_EIN_VALIDER_ZUSTAND_UND_GINGE_DAS_VIELLEICHT_ELEGANTER? PinballMachineManager.getInstance().loadMachineElements(automaton);
        game = new GameSession(automaton, players);
    }

    @Test
    public static void testReserveBalls() {
        KeyCode plungerKey = Settings.getSingletonInstance().keyBindingsMapProperty().get(KeyBinding.PLUNGER);
        InputManager.getSingletonInstance().addKeyEvent(new KeyEvent(KeyEvent.KEY_PRESSED, " ", plungerKey.name(), plungerKey, false, false, false, false));
        try {
            Thread.sleep(keyHoldingTime);
        } catch (InterruptedException e) { }
        InputManager.getSingletonInstance().addKeyEvent(new KeyEvent(KeyEvent.KEY_RELEASED, " ", plungerKey.name(), plungerKey, false, false, false, false));
        try {
            Thread.sleep(waitingTime);
        } catch (InterruptedException e) { }
        assertEquals(3, game.getCurrentPlayer().ballsProperty().get());
        assertEquals("test", game.getCurrentPlayer().getName());
        InputManager.getSingletonInstance().addKeyEvent(new KeyEvent(KeyEvent.KEY_PRESSED, " ", plungerKey.name(), plungerKey, false, false, false, false));
        try {
            Thread.sleep(keyHoldingTime);
        } catch (InterruptedException e) { }
        InputManager.getSingletonInstance().addKeyEvent(new KeyEvent(KeyEvent.KEY_RELEASED, " ", plungerKey.name(), plungerKey, false, false, false, false));
        try {
            Thread.sleep(waitingTime);
        } catch (InterruptedException e) { }
        assertEquals(2, game.getCurrentPlayer().ballsProperty().get());
        assertEquals("tester", game.getCurrentPlayer().getName());
        InputManager.getSingletonInstance().addKeyEvent(new KeyEvent(KeyEvent.KEY_PRESSED, " ", plungerKey.name(), plungerKey, false, false, false, false));
        try {
            Thread.sleep(keyHoldingTime);
        } catch (InterruptedException e) { }
        InputManager.getSingletonInstance().addKeyEvent(new KeyEvent(KeyEvent.KEY_RELEASED, " ", plungerKey.name(), plungerKey, false, false, false, false));
        try {
            Thread.sleep(waitingTime);
        } catch (InterruptedException e) { }
        assertEquals(2, game.getCurrentPlayer().ballsProperty().get());
        assertEquals("test", game.getCurrentPlayer().getName());
    }
}
