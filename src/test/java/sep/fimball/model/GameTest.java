package sep.fimball.model;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import sep.fimball.general.data.Config;
import sep.fimball.general.data.Vector2;
import sep.fimball.model.blueprint.base.BaseElementManager;
import sep.fimball.model.blueprint.pinballmachine.PinballMachine;
import sep.fimball.model.blueprint.pinballmachine.PinballMachineManager;
import sep.fimball.model.blueprint.pinballmachine.PlacedElement;
import sep.fimball.model.blueprint.settings.Settings;
import sep.fimball.model.element.GameElement;
import sep.fimball.model.input.InputManager;
import sep.fimball.model.input.KeyBinding;
import sep.fimball.model.trigger.ElementTrigger;
import sep.fimball.model.trigger.GameTrigger;
import sep.fimball.model.trigger.Trigger;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@Ignore
public class GameTest
{
    private static final long MAX_TEST_DURATION = 30;   //nach wie vielen Sekunden wird abgebrochen
    private static final long HOLD_KEY_DURATION = 1000; //wie lange wird der Plunger gespannt
    private static final String WALL_ID = "Wall";       //TODO correct id
    private static final String BUMPER_ID = "bumper_blue";
    private static final String PLUNGER_ID = "Plunger"; //TODO correct id
    private static final String BALL_SPAWN_ID = "ball";

    private Stack<GameElement> collidedGameElements;
    private boolean stop = false;
    private boolean forcedStop = false;

    @Before
    public void initialize()
    {
        collidedGameElements = new Stack<GameElement>();
        Config.config();
    }

    @Test
    public void gameCollisionTest()
    {
        //TODO Pinballautomat so aufbauen, dass der gegebene Verlauf eintritt
        PinballMachine pinballMachine = PinballMachineManager.getInstance().createNewMachine();

        pinballMachine.addElement(new PlacedElement(
                BaseElementManager.getInstance().getElement(PLUNGER_ID), new Vector2(0, 0), 0, 0, 0));

        pinballMachine.addElement(new PlacedElement(
                BaseElementManager.getInstance().getElement(BALL_SPAWN_ID), new Vector2(0, 5), 0, 0, 0));

        pinballMachine.addElement(new PlacedElement(
                BaseElementManager.getInstance().getElement(WALL_ID), new Vector2(0, 20), 0, 0, 0));

        pinballMachine.addElement(new PlacedElement(
                BaseElementManager.getInstance().getElement(PLUNGER_ID), new Vector2(5, 17), 0, 0, 0));

        //Starten des Spiels
        GameSession session = new GameSession(pinballMachine, new String[]{"Testautomat"});
        ElementTrigger collisionTrigger = new CollisionTrigger(this);
        GameTrigger ballLostTrigger = new BallLostTrigger(this);
        List<Trigger> triggerList = new ArrayList<>();
        Trigger trigger = new Trigger();
        trigger.setElementTrigger(collisionTrigger);
        trigger.setGameTrigger(ballLostTrigger);
        triggerList.add(trigger);
        session.setTriggers(triggerList);

        session.startAll();

        //Wegschießen der Kugel durch den Plunger
        KeyCode plungerKey = Settings.getSingletonInstance().keyBindingsMapProperty().get(KeyBinding.PLUNGER);
        InputManager.getSingletonInstance().addKeyEvent(new KeyEvent(KeyEvent.KEY_PRESSED, " ", plungerKey.name(), plungerKey, false, false, false, false));
        try
        {
            Thread.sleep(HOLD_KEY_DURATION);
        } catch (InterruptedException e)
        {
        }
        InputManager.getSingletonInstance().addKeyEvent(new KeyEvent(KeyEvent.KEY_RELEASED, " ", plungerKey.name(), plungerKey, false, false, false, false));

        //Aufzeichnen der Kollisionen
        int iteration = 0;
        while (!stop && !forcedStop)
        {
            try
            {
                Thread.sleep(1000);
                iteration++;
            } catch (InterruptedException e)
            {
            }

            if (iteration >= MAX_TEST_DURATION)
            {
                forcedStop = true;
            }
        }

        session.stopPhysics();
        session.stopTimeline();

        //Aufzeichnungen auswerten
        assertTrue(stop);
        assertEquals(collidedGameElements.pop().getPlacedElement().getBaseElement().getId(), WALL_ID);
        assertEquals(collidedGameElements.pop().getPlacedElement().getBaseElement().getId(), BUMPER_ID);

        //Loeschen des vorher erstellten Automaten
        pinballMachine.deleteFromDisk();
    }

    public void addCollidedGameElement(GameElement gameElement)
    {
        collidedGameElements.push(gameElement);
    }

    public void ballLost()
    {
        stop = true;
    }

    class CollisionTrigger implements ElementTrigger
    {
        private GameTest gameTest;

        public CollisionTrigger(GameTest gameTest)
        {
            this.gameTest = gameTest;
        }

        @Override
        public void activateElementTrigger(GameElement element, int colliderID)
        {
            gameTest.addCollidedGameElement(element);
        }
    }

    class BallLostTrigger implements GameTrigger
    {
        private GameTest gameTest;

        public BallLostTrigger(GameTest gameTest)
        {
            this.gameTest = gameTest;
        }

        @Override
        public void activateGameTrigger()
        {
            gameTest.ballLost();
        }
    }
}
