package sep.fimball.model;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import org.junit.After;
import org.junit.Test;
import sep.fimball.model.blueprint.pinballmachine.PinballMachine;
import sep.fimball.model.blueprint.pinballmachine.PinballMachineManager;
import sep.fimball.model.blueprint.settings.Settings;
import sep.fimball.model.game.TestGameSession;
import sep.fimball.model.handler.GameEvent;
import sep.fimball.model.handler.Handler;
import sep.fimball.model.handler.HandlerFactory;
import sep.fimball.model.input.InputManager;
import sep.fimball.model.input.KeyBinding;

import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by marc on 16.11.16.
 */
public class ReserveBallsAndPlayerChangeTest
{
    private static String[] players = new String[]{"tester", "test"};
    // Wie lange wird der Plunger gespannt
    private static final long KEY_HOLDING_TIME = 200;
    // Nach wie vielen Millisekunden wird abgebrochen
    private static final long MAX_TEST_TIME = 120000;
    private static Object monitor = new Object();

    private TestGameSession session;

    @Test(timeout = MAX_TEST_TIME)
    public void testReserveBalls() throws InterruptedException
    {
        // Starten des Spiels
        initGameSession();
        // Wegschießen der Kugel durch den Plunger
        usePlunger();
        // Warten, bis der Ball verloren gegangen ist und zum zweiten Spieler gewechselt wurde
        synchronized (monitor)
        {
            monitor.wait(MAX_TEST_TIME);
        }

        // Hat der zweite Spieler noch alle Bälle?
        assertEquals(3, session.getCurrentPlayer().ballsProperty().get());
        // Ist der zweite Spieler am Zug?
        assertEquals("test", session.getCurrentPlayer().getName());

        usePlunger();
        // Warten, bis der Ball verloren gegangen ist und zum ersten Spieler zurück gewechselt wurde
        synchronized (monitor)
        {
            monitor.wait(MAX_TEST_TIME);
        }

        // Hat der erste Spieler einen Ball verloren?
        assertEquals(2, session.getCurrentPlayer().ballsProperty().get());
        // Ist der erste Spieler am Zug?
        assertEquals("tester", session.getCurrentPlayer().getName());

        usePlunger();
        // Warten, bis der Ball verloren gegangen ist und zum zweiten Spieler gewechselt wurde
        synchronized (monitor)
        {
            monitor.wait(MAX_TEST_TIME);
        }
        // Hat auch der zweite Spieler einen Ball verloren?
        assertEquals(2, session.getCurrentPlayer().ballsProperty().get());
        // Ist der zweite Spieler am Zug?
        assertEquals("test", session.getCurrentPlayer().getName());
    }

    @After
    public void stopThreads()
    {
        session.stopPhysics();
        session.stopGameLoop();
    }

    private void initGameSession()
    {
        PinballMachine automat = PinballMachineManager.getInstance().pinballMachinesProperty().stream().filter((PinballMachine machine) -> machine.getID().equals("0")).findFirst().get();
        session = new TestGameSession(automat, players);
        List<Handler> triggers = HandlerFactory.generateAllHandlers(session);
        Handler ballLostChecker = new Handler();
        ballLostChecker.setGameHandler((GameEvent gameEvent) ->
        {
            if (gameEvent == GameEvent.BALL_LOST)
            {
                synchronized (monitor)
                {
                    monitor.notify();
                }
            }
        });
        triggers.add(ballLostChecker);
        session.setTriggers(triggers);
    }

    private void usePlunger()
    {
        KeyCode plungerKey = Settings.getSingletonInstance().keyBindingsMapProperty().get(KeyBinding.PLUNGER);
        InputManager.getSingletonInstance().addKeyEvent(new KeyEvent(KeyEvent.KEY_PRESSED, " ", plungerKey.name(), plungerKey, false, false, false, false));
        try
        {
            Thread.sleep(KEY_HOLDING_TIME);
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
        InputManager.getSingletonInstance().addKeyEvent(new KeyEvent(KeyEvent.KEY_RELEASED, " ", plungerKey.name(), plungerKey, false, false, false, false));
    }
}
