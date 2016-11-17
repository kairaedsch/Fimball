package sep.fimball.model;

import org.junit.Ignore;

/**
 * Created by marc on 16.11.16.
 */

@Ignore
public class GameTest
{
    /**
     * The amount of the seconds the Test will run before it is failed.
     */
    /*
    private static final int MAX_ITERATIONS = 30;

    //TODO correct id
    private static final string WALL_ID = "Wall";
    private static final string BUMPER_ID = "Bumper";

    private Stack<GameElement> collidedGameElements;
    private boolean stop = false;
    private boolean forcedStop = false;
    private int iterations = 0;

    @Before
    public void initialize()
    {
        collidedGameElements = new ArrayStack<GameElement>();
    }

    @Test
    public void gameCollisionTest()
    {
        //TODO Pinballautomat so aufbauen, dass der gegebene Verlauf eintritt
        PinballMachine pinballMachine;

        //Starten des Spiels
        GameSession session = new GameSession(pinballMachine);
        CollisionTrigger collisionTrigger = new CollisionTrigger(this);
        BallLostTrigger ballLostTrigger = new BallLostTrigger(this);
        List<Trigger> triggerList = new ArrayList<Trigger>();
        triggerList.add(collisionTrigger);
        triggerList.add(ballLostTrigger);
        session.setTriggers(triggerList);

        session.startAll();

        //Aufzeichnen der Kollisionen
        while (!stop && !forcedStop)
        {
            iterations++;
            if (iterations >= MAX_ITERATIONS)
            {
                forcedStop = true;
            }
        }
        session.stopAll();

        //Aufzeichnungen auswerten
        assertTrue(stop);
        assertEquals(collidedGameElements.pop().getPlacedElement().getBaseElement().getId(), WALL_ID);
        assertEquals(collidedGameElements.pop().getPlacedElement().getBaseElement().getId(), BUMPER_ID);
    }

    public synchronized void addCollidedGameElement(GameElement gameElement)
    {
        collidedGameElements.push(gameElement);
    }

    public synchronized void ballLost()
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
        public void activateTrigger(GameElement element, int colliderID)
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
        public void activateTrigger()
        {
            gameTest.ballLost();
        }
    }
    */
}
