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
 * Repräsentiert einen JUnit-Test, der prüft, ob die Anzahl der verbleibenden Bälle richtig gesetzt wird und der Spielerwechsel richtig von statten geht.
 */
public class ReserveBallsAndPlayerChangeTest
{
    private static String[] players = new String[]{"tester", "test"};
    private static final long MAX_TEST_DURATION = 20000;    // Die Zeit in Millisekunden, nach der der Test abgebrochen wird.
    private static final long KEY_HOLDING_DURATION = 1000;     // Die Zeit, die der Plunger gespannt wird.
    private static Object monitor = new Object();
    private volatile boolean ballIsLost = false;

    private TestGameSession session;

    /**
     * Testet, ob bei Ballverlust in einem Mehrspielerspiel der Spieler gewechselt und die Anzahl der verbleibenden Kugeln ordnungsgemäß gesenkt wird.
     *
     * @throws InterruptedException Falls ein Interrupt auftritt.
     */
    @Test(timeout = MAX_TEST_DURATION)
    public void testReserveBalls() throws InterruptedException
    {

        initGameSession();      // Start des Spiels
        usePlunger();
        waitTillBallIsLost();

        // Hat der zweite Spieler noch alle Bälle und ist er am Zug?
        assertEquals(3, session.getCurrentPlayer().ballsProperty().get());
        assertEquals("test", session.getCurrentPlayer().getName());

        usePlunger();
        waitTillBallIsLost();

        // Hat der erste Spieler einen Ball verloren und ist der erste Spieler am Zug?
        assertEquals(2, session.getCurrentPlayer().ballsProperty().get());
        assertEquals("tester", session.getCurrentPlayer().getName());

        usePlunger();
        waitTillBallIsLost();

        // Hat auch der zweite Spieler einen Ball verloren und ist der zweite Spieler am Zug?
        assertEquals(2, session.getCurrentPlayer().ballsProperty().get());
        assertEquals("test", session.getCurrentPlayer().getName());
    }

    /**
     * Hält den Physik- und Regelauswertung-Thread an.
     */
    @After
    public void stopThreads()
    {
        session.stopPhysics();
        session.stopGameLoop();
    }

    /**
     * Lädt den Automaten von der Festplatte, erstellt einen Handler, der den Test bei Verlust der Kugel benachrichtigt und initialisiert eine neue GameSession.
     */
    private void initGameSession()
    {
        PinballMachine automat = PinballMachineManager.getInstance().pinballMachinesProperty().stream().filter((PinballMachine machine) -> machine.getID().equals("0")).findFirst().get();
        session = new TestGameSession(automat, players);
        List<Handler> handlers = HandlerFactory.generateAllHandlers(session);
        Handler ballLostChecker = new Handler();
        ballLostChecker.setGameHandler((GameEvent gameEvent) ->
        {
            if (gameEvent == GameEvent.BALL_LOST)
            {
                ballIsLost = true;
                synchronized (monitor)
                {
                    monitor.notify();
                }
            }
        });
        handlers.add(ballLostChecker);
        session.addHandlers(handlers);
    }

    /**
     * Hält die Plunger-Taste für {@code HOLD_KEY_DURATION} gedrückt und lässt sie anschließend wieder los.
     *
     * @throws InterruptedException Falls ein Interrupt auftritt, während der Thread schläft, da dann nicht mehr der geplante Testverlauf gewährleistet werden kann.
     */
    private void usePlunger() throws InterruptedException
    {
        KeyCode plungerKey = Settings.getSingletonInstance().keyBindingsMapProperty().get(KeyBinding.PLUNGER);
        InputManager.getSingletonInstance().addKeyEvent(new KeyEvent(KeyEvent.KEY_PRESSED, " ", plungerKey.name(), plungerKey, false, false, false, false));
        Thread.sleep(KEY_HOLDING_DURATION);
        InputManager.getSingletonInstance().addKeyEvent(new KeyEvent(KeyEvent.KEY_RELEASED, " ", plungerKey.name(), plungerKey, false, false, false, false));
    }

    /**
     * Lässt den Test warten, bis der Ball verloren wurde.
     *
     * @throws InterruptedException Falls ein Interrupt aufgetreten ist.
     */
    private void waitTillBallIsLost() throws InterruptedException
    {
        while (!ballIsLost)
        {
            synchronized (monitor)
            {
                monitor.wait(MAX_TEST_DURATION);
            }
        }
        ballIsLost = false;
    }
}
