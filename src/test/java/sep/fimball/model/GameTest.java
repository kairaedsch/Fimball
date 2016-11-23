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
import sep.fimball.model.input.InputManager;
import sep.fimball.model.input.KeyBinding;

import java.util.List;
import java.util.Stack;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class GameTest
{
    // Nach wie vielen Millisekunden wird abgebrochen
    private static final long MAX_TEST_DURATION = 20000;
    // Wie lange wird der Plunger gespannt
    private static final long HOLD_KEY_DURATION = 1000;

    private static final String WALL_ID = "hinderniss_linie_schraeg_2";
    private static final String BUMPER_ID = "bumper_blue";
    private static final String PLUNGER_ID = "plunger";
    private static final String BALL_SPAWN_ID = "ball";
    private static Object monitor = new Object();

    // Speichert Kollisionen, die während dem Test passieren
    private Stack<GameElement> collidedGameElements = new Stack<>();
    // Spiel-Session, die zum Test erstellt wird
    private TestGameSession session;
    // Automat, der getestet wird
    private PinballMachine pinballMachine;

    @Test(timeout = MAX_TEST_DURATION)
    public void gameCollisionTest() throws InterruptedException
    {
        // Pinballautomat so aufbauen, dass der gegebene Verlauf eintritt
        pinballMachine = PinballMachineManager.getInstance().createNewMachine();
        pinballMachine.nameProperty().setValue("GameTest PinballMachine");

        // Plunger, Ball, Wand und Bumper einfügen
        pinballMachine.addElement(new PlacedElement(
                BaseElementManager.getInstance().getElement(PLUNGER_ID), new Vector2(0, 20), 0, 0, 0));

        pinballMachine.addElement(new PlacedElement(
                BaseElementManager.getInstance().getElement(BALL_SPAWN_ID), new Vector2(0, -5), 0, 0, 0));

        pinballMachine.addElement(new PlacedElement(
                BaseElementManager.getInstance().getElement(WALL_ID), new Vector2(0, -20), 0, 0, 0));

        pinballMachine.addElement(new PlacedElement(
                BaseElementManager.getInstance().getElement(BUMPER_ID), new Vector2(7, -12), 0, 0, 0));

        //Starten des Spiels
        initializeGameSession();

        // Wegschießen der Kugel durch den Plunger
        usePlunger();

        // Warten, bis der Ball verloren gegangen ist
        synchronized (monitor)
        {
            monitor.wait();
        }

        //Aufzeichnungen auswerten
        assertEquals(collidedGameElements.pop().getPlacedElement().getBaseElement().getId(), BUMPER_ID);
        assertEquals(collidedGameElements.pop().getPlacedElement().getBaseElement().getId(), WALL_ID);
        assertEquals(collidedGameElements.pop().getPlacedElement().getBaseElement().getId(), PLUNGER_ID);
        assertTrue(collidedGameElements.empty());
    }

    @After
    public void cleanup()
    {
        session.stopPhysics();
        session.stopGameLoop();
    }

    public void addCollidedGameElement(GameElement gameElement)
    {
        collidedGameElements.push(gameElement);
    }

    public void ballLost()
    {
        synchronized (monitor)
        {
            monitor.notify();
        }
    }

    private void initializeGameSession()
    {
        session = new TestGameSession(pinballMachine, new String[]{"TestSpieler"});

        // Erstellt Handler, der Kollisionen aufzeichnet
        Handler collisionHandler = new Handler();
        collisionHandler.setElementHandler(new CollisionHandler(this));

        // Erstellt Handler, der bei Ballverlust this.notify() aufruft
        Handler ballLostHandler = new Handler();
        ballLostHandler.setGameHandler(new BallLostHandler(this));

        // Registriert Handler
        List<Handler> handlerList = HandlerFactory.generateAllHandlers(session);
        handlerList.add(collisionHandler);
        handlerList.add(ballLostHandler);
        session.setTriggers(handlerList);

        session.startAll();
    }

    private void usePlunger() throws InterruptedException
    {
        KeyCode plungerKey = Settings.getSingletonInstance().keyBindingsMapProperty().get(KeyBinding.PLUNGER);
        InputManager.getSingletonInstance().addKeyEvent(new KeyEvent(KeyEvent.KEY_PRESSED, " ", plungerKey.name(), plungerKey, false, false, false, false));

        Thread.sleep(HOLD_KEY_DURATION);

        InputManager.getSingletonInstance().addKeyEvent(new KeyEvent(KeyEvent.KEY_RELEASED, " ", plungerKey.name(), plungerKey, false, false, false, false));
    }

    private class CollisionHandler implements ElementHandler
    {
        private GameTest gameTest;

        public CollisionHandler(GameTest gameTest)
        {
            this.gameTest = gameTest;
        }

        @Override
        public void activateElementHandler(GameElement element, int colliderID)
        {
            System.out.println("Kugel kollidiert mit " + element.getPlacedElement().getBaseElement().getId());
            gameTest.addCollidedGameElement(element);
        }
    }

    private class BallLostHandler implements GameHandler
    {
        private GameTest gameTest;

        public BallLostHandler(GameTest gameTest)
        {
            this.gameTest = gameTest;
        }

        @Override
        public void activateGameHandler(GameEvent gameEvent)
        {
            if (gameEvent == GameEvent.BALL_LOST)
            {
                System.out.println("Kugel verlässt das Spiel");
                gameTest.ballLost();
            }
        }
    }
}
