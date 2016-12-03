package sep.fimball.model;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import org.junit.After;
import org.junit.Test;
import sep.fimball.general.data.Vector2;
import sep.fimball.model.blueprint.base.BaseElementManager;
import sep.fimball.model.blueprint.pinballmachine.PinballMachine;
import sep.fimball.model.blueprint.pinballmachine.PinballMachineManager;
import sep.fimball.model.blueprint.pinballmachine.PlacedElement;
import sep.fimball.model.blueprint.settings.Settings;
import sep.fimball.model.game.GameElement;
import sep.fimball.model.game.TestGameSession;
import sep.fimball.model.handler.*;
import sep.fimball.model.input.data.KeyBinding;
import sep.fimball.model.input.manager.InputManager;

import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Diese Klasse repräsentiert einen Test, der prüft, ob ein Ball den erwarteten Verlauf im angegebenen Automaten nimmt.
 */
public class GameTest
{
    private static final long MAX_TEST_DURATION = 20000;    // Die Zeit in Millisekunden, nach der der Test abgebrochen wird.
    private static final long HOLD_KEY_DURATION = 1000;     // Die Zeit, die der Plunger gespannt wird.
    private boolean ballIsLost = false;

    private static final String WALL_ID = "hinderniss_linie_schraeg_2";
    private static final String BUMPER_ID = "bumper_blue";
    private static final String PLUNGER_ID = "plunger";
    private static final String BALL_SPAWN_ID = "ball";
    private static Object monitor = new Object();

    private Deque<GameElement> collidedGameElements = new ArrayDeque<>();    // Speichert Kollisionen, die während des Tests auftreten
    private TestGameSession session;
    private PinballMachine pinballMachine;

    /**
     * Baut einen Testautomaten und testet, ob die Kugel mit der wand kollidiert, mit dem Bumper kollidiert und anschließend verloren geht.
     *
     * @throws InterruptedException Bricht den Test ab, falls im Test unvorhergesehenerweise ein Interrupt aufgetreten ist.
     */
    @Test(timeout = MAX_TEST_DURATION)
    public void gameCollisionTest() throws InterruptedException, IOException
    {
        // Aufbau des Automaten.
        pinballMachine = PinballMachineManager.getInstance().createNewMachine();
        pinballMachine.nameProperty().setValue("GameTest PinballMachine");

        // Einfügen von Plunger, Ball, Wand und Bumper.
        pinballMachine.addElement(new PlacedElement(BaseElementManager.getInstance().getElement(PLUNGER_ID), new Vector2(0, 20), 0, 0, 0));
        pinballMachine.addElement(new PlacedElement(BaseElementManager.getInstance().getElement(BALL_SPAWN_ID), new Vector2(0, -5), 0, 0, 0));
        pinballMachine.addElement(new PlacedElement(BaseElementManager.getInstance().getElement(WALL_ID), new Vector2(0, -20), 0, 0, 0));
        pinballMachine.addElement(new PlacedElement(BaseElementManager.getInstance().getElement(BUMPER_ID), new Vector2(7, -12), 0, 0, 0));

        session = new TestGameSession(pinballMachine, new String[]{"TestSpieler"});        // Start des Spiels

        // Erstellung des Handler, der Kollisionen aufzeichnet.
        Handler collisionHandler = new Handler();
        collisionHandler.setElementHandler(new CollisionHandler());

        // Erstellung des Handler, der bei Ballverlust ballLost() aufruft.
        Handler ballLostHandler = new Handler();
        ballLostHandler.setGameHandler(new BallLostHandler());

        // Registrierung der Handler.
        List<Handler> handlerList = HandlerFactory.generateAllHandlers(session);
        handlerList.add(collisionHandler);
        handlerList.add(ballLostHandler);
        session.addHandlers(handlerList);

        session.startAll();

        usePlunger();

        // Warten, bis der Ball verloren gegangen ist.
        synchronized (monitor)
        {
            while (!ballIsLost)
            {
                monitor.wait(MAX_TEST_DURATION);
            }
        }

        //Auswertung der Aufzeichnungen.
        assertEquals(collidedGameElements.pop().getPlacedElement().getBaseElement().getId(), BUMPER_ID);
        assertEquals(collidedGameElements.pop().getPlacedElement().getBaseElement().getId(), WALL_ID);
        assertEquals(collidedGameElements.pop().getPlacedElement().getBaseElement().getId(), PLUNGER_ID);
        assertTrue(collidedGameElements.isEmpty());
    }

    /**
     * Stoppt die Ausführung des Physik- und des Regelwerk-Threads.
     */
    @After
    public void cleanup()
    {
        session.stopPhysics();
        session.stopGameLoop();
    }

    /**
     * Speichert die Kollision des Balls mit einem Spielelement für die spätere Überprüfung.
     *
     * @param gameElement Das Spielelement, mit dem die Kugel kollidiert ist.
     */
    private void addCollidedGameElement(HandlerGameElement gameElement)
    {
        collidedGameElements.push((GameElement) gameElement);
    }

    /**
     * Benachrichtigt den Test, dass der Ball nun aus dem Spiel ist.
     */
    public void ballLost()
    {
        ballIsLost = true;
        synchronized (monitor)
        {
            monitor.notify();
        }
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
        Thread.sleep(HOLD_KEY_DURATION);
        InputManager.getSingletonInstance().addKeyEvent(new KeyEvent(KeyEvent.KEY_RELEASED, " ", plungerKey.name(), plungerKey, false, false, false, false));
    }

    /**
     * Gibt bei einer Kollision das Spielelement, mit dem die Kugel kollidiert ist, in die Deque.
     */
    private class CollisionHandler implements ElementHandler
    {

        /**
         * Gibt das Spielelement, mit dem die Kugel kollidiert ist, in die Deque.
         */
        @Override
        public void activateElementHandler(HandlerGameElement element, int colliderID)
        {
            addCollidedGameElement(element);
        }
    }

    /**
     * Benachrichtigt den Test, dass der Ball verloren gegangen ist.
     */
    private class BallLostHandler implements GameHandler
    {
        /**
         * Benachrichtigt den Test, dass der Ball verloren gegangen ist.
         *
         * @param gameEvent Beinhaltet die Information, was geschehen ist.
         */
        @Override
        public void activateGameHandler(GameEvent gameEvent)
        {
            if (gameEvent == GameEvent.BALL_LOST)
            {
                ballLost();
            }
        }
    }
}
