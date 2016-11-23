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

public class GameTest
{

    private static final long MAX_TEST_DURATION = 20000;   // Nach wie vielen Millisekunden wird abgebrochen
    private static final long HOLD_KEY_DURATION = 1000; // Wie lange wird der Plunger gespannt
    private static final String WALL_ID = "hinderniss_linie_schraeg_2";
    private static final String BUMPER_ID = "bumper_blue";
    private static final String PLUNGER_ID = "plunger";
    private static final String BALL_SPAWN_ID = "ball";

    private Stack<GameElement> collidedGameElements = new Stack<>(); // Speichert Kollisionen, die während dem Test passieren
    private TestGameSession session; // Spiel-Session, die zum Test erstellt wird
    private PinballMachine pinballMachine; // Automat, der getestet wird

    @Test(timeout = MAX_TEST_DURATION)
    public synchronized void gameCollisionTest()
    {
        //Pinballautomat so aufbauen, dass der gegebene Verlauf eintritt
        pinballMachine = PinballMachineManager.getInstance().createNewMachine();
        pinballMachine.nameProperty().setValue("GameTest PinballMachine");

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
        try
        {
            wait();
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }

        //Wegschießen der Kugel durch den Plunger
        usePlunger();

        try
        {
            wait();
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }

        //Aufzeichnungen auswerten
        assertEquals(collidedGameElements.pop().getPlacedElement().getBaseElement().getId(), BUMPER_ID);
        assertEquals(collidedGameElements.pop().getPlacedElement().getBaseElement().getId(), WALL_ID);

    }

    @After
    public void cleanup()
    {
        session.stopPhysics();
        session.stopGameLoop();
        //pinballMachine.deleteFromDisk();
        pinballMachine.saveToDisk();
    }

    public void addCollidedGameElement(GameElement gameElement)
    {
        collidedGameElements.push(gameElement);
    }

    public synchronized void ballLost()
    {
        this.notify();
    }

    private synchronized void initializeGameSession()
    {
        session = new TestGameSession(pinballMachine, new String[]{"TestSpieler"});

        Handler collisionTrigger = new Handler();
        collisionTrigger.setElementHandler(new CollisionHandler(this));
        Handler ballLostTrigger = new Handler();
        ballLostTrigger.setGameHandler(new BallLostHandler(this));

        List<Handler> triggerList = HandlerFactory.generateAllHandlers(session);
        triggerList.add(collisionTrigger);
        triggerList.add(ballLostTrigger);
        session.setTriggers(triggerList);

        session.startAll();
        this.notify();
    }

    private void usePlunger()
    {
        KeyCode plungerKey = Settings.getSingletonInstance().keyBindingsMapProperty().get(KeyBinding.PLUNGER);
        InputManager.getSingletonInstance().addKeyEvent(new KeyEvent(KeyEvent.KEY_PRESSED, " ", plungerKey.name(), plungerKey, false, false, false, false));
        try
        {
            Thread.sleep(HOLD_KEY_DURATION);
        }
        catch (InterruptedException e)
        {
        }
        InputManager.getSingletonInstance().addKeyEvent(new KeyEvent(KeyEvent.KEY_RELEASED, " ", plungerKey.name(), plungerKey, false, false, false, false));
    }

    class CollisionHandler implements ElementHandler
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

    class BallLostHandler implements GameHandler
    {
        private GameTest gameTest;

        public BallLostHandler(GameTest gameTest)
        {
            this.gameTest = gameTest;
        }

        @Override
        public void activateGameHandler(GameEvent gameEvent)
        {
            System.out.println("Kugel verlässt das Spiel");
            gameTest.ballLost();
        }
    }

}
