package sep.fimball.model;

import org.junit.After;
import org.junit.Test;
import sep.fimball.general.data.Vector2;
import sep.fimball.model.blueprint.base.BaseElementManager;
import sep.fimball.model.blueprint.pinballmachine.PinballMachine;
import sep.fimball.model.blueprint.pinballmachine.PinballMachineManager;
import sep.fimball.model.blueprint.pinballmachine.PlacedElement;
import sep.fimball.model.game.GameElement;
import sep.fimball.model.game.TestGameSession;
import sep.fimball.model.handler.*;
import sep.fimball.model.input.data.KeyBinding;
import sep.fimball.model.input.manager.KeyEventArgs;
import sep.fimball.model.physics.game.CollisionEventType;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Diese Klasse repräsentiert einen Test, der prüft, ob ein Ball den erwarteten Verlauf im angegebenen Automaten nimmt.
 */
public class GameTest
{
    private static final long MAX_TEST_DURATION = 5000;    // Die Zeit in Millisekunden, nach der der Test abgebrochen wird.
    private static final long HOLD_KEY_DURATION = 700;     // Die Zeit, die der Plunger gespannt wird.
    private boolean ballIsLost = false;

    private static final String WALL_ID = "hinderniss_linie_schraeg_4";
    private static final String BUMPER_ID = "bumper_blue";
    private static final String PLUNGER_ID = "plunger";
    private static final String BALL_SPAWN_ID = "ball";
    private static final Object monitor = new Object();

    private PinballMachine pinballMachine;

    private Deque<GameElement> collidedGameElements = new ArrayDeque<>();    // Speichert Kollisionen, die während des Tests auftreten
    private TestGameSession session;

    /**
     * Baut einen Testautomaten und testet, ob die Kugel mit der wand kollidiert, mit dem Bumper kollidiert und anschließend verloren geht.
     *
     * @throws InterruptedException Bricht den Test ab, falls im Test unvorhergesehenerweise ein Interrupt aufgetreten ist.
     */
    @Test (timeout = MAX_TEST_DURATION)
    public void gameCollisionTest() throws InterruptedException
    {
        // Aufbau des Automaten.
        pinballMachine = PinballMachineManager.getInstance().createNewMachine();
        pinballMachine.nameProperty().setValue("GameTest Machine");

        // Einfügen von Plunger, Ball, Wand und Bumper.
        pinballMachine.addElement(new PlacedElement(BaseElementManager.getInstance().getElement(PLUNGER_ID), new Vector2(0, 18), 0, 1, 0));
        pinballMachine.addElement(new PlacedElement(BaseElementManager.getInstance().getElement(BALL_SPAWN_ID), new Vector2(0, 14), 0, 1, 0));
        pinballMachine.addElement(new PlacedElement(BaseElementManager.getInstance().getElement(WALL_ID), new Vector2(-1, 7), 0, 1, 0));
        pinballMachine.addElement(new PlacedElement(BaseElementManager.getInstance().getElement(BUMPER_ID), new Vector2(2, 0), 0, 1, 0));

        session = new TestGameSession(pinballMachine, new String[]{"TestSpieler"});        // Start des Spiels

        // Erstellung des Handler, der Kollisionen aufzeichnet.
        Handler collisionHandler = new Handler(new CollisionHandler());

        // Erstellung des Handler, der bei Ballverlust ballLost() aufruft.
        Handler ballLostHandler = new Handler(new BallLostHandler());

        // Registrierung der Handler.
        List<Handler> handlerList = HandlerFactory.generateAllHandlers(session, null);
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
    }

    /**
     * Löscht den verwendeten Automaten von der Festplatte und stoppt die Ausführung des Physik- und des Regelwerk-Threads.
     */
    @After
    public void cleanup()
    {
        pinballMachine.deleteFromDisk();
        session.stopPhysics();
        session.stopUpdateLoop();
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
    private void ballLost()
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
        session.activateUserHandler(new KeyEventArgs(KeyBinding.PLUNGER, KeyEventArgs.KeyChangedToState.DOWN, true));
        Thread.sleep(HOLD_KEY_DURATION);
        session.activateUserHandler(new KeyEventArgs(KeyBinding.PLUNGER, KeyEventArgs.KeyChangedToState.UP, true));
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
        public void activateElementHandler(HandlerGameElement element, CollisionEventType collisionEventType, int colliderID)
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
