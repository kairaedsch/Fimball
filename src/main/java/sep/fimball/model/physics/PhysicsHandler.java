package sep.fimball.model.physics;

import sep.fimball.general.data.Vector2;
import sep.fimball.model.element.GameElementList;
import sep.fimball.model.input.InputManager;
import sep.fimball.model.input.KeyBinding;
import sep.fimball.model.input.KeyObserverEventArgs;
import sep.fimball.model.World;

import java.util.*;

/**
 * Der PhysicsHandler kümmert sich um die Physikalische Simulation des Automaten. Er ist dafür verantwortlich, dass sich der Ball korrekt auf der zweidimensionalen Fläche bewegt. Auch überprüft er ob die Kugel, welche das einzige ElementType ist welches dauerhaft in Bewegung ist, mit anderen Elementen kollidiert. Falls sie dies tut wird die Kollision aufgelöst indem die beiden Elemente voneinander abprallen. Das Objekt mit dem die Kugel kollidiert ist wird über die Kollision informiert. Alle diese Berechnungen führt der PhysicsHandler in einer Schleife aus welche 60 mal pro Sekunde ausgeführt wird. Auch wird überprüft ob die Kugel verloren gegangen ist.
 */
public class PhysicsHandler
{
    /**
     * Die Physikschleife beginnt ohne Verzögerung wenn sie gestartet wird
     */
    private final int TIMER_DELAY = 0;

    /**
     * Gibt an nach wie vielen Millisekunden Wartezeit der nächste Schritt der Physikschleife ausgeführt wird.
     */
    private final int TICK_RATE = 1000 / 60;

    /**
     * Eine Referenz auf die Welt auf welcher die physikalischen Berechnungen ausgeführt werden.
     */
    private World world;

    private PhysicsElement ballElement;

    /**
     * Der Timer wird zur Erzeugung der Physik Schleife genutzt.
     */
    private Timer physicTimer;

    /**
     * Die Aufgabe, die in jedem Schritt der Physikschleife ausgeführt werden soll.
     */
    private TimerTask timerTask;
    /**
     * Hier werden alle Tastendrücke die vom InputManager an den PhysikHandler mithilfe des Observer Pattern gesendet
     * wurden gespeichert. Im nächsten Physik Schritt kann der PhysikHandler diese dann abarbeiten. Dies ist notwendig
     * da das Observer Pattern nicht zur Threadübergreifenden Kommunikation gedacht ist.
     */
    private List<KeyObserverEventArgs> bufferedKeyEvents;

    /**
     * Eine Liste aller GameElements
     */
    private List<PhysicsElement> physicsElements;

    /**
     * Erzeugt einen PhysicsHandler
     *
     * @param world
     */
    public PhysicsHandler(World world)
    {
        this.world = world;
        bufferedKeyEvents = new ArrayList<>();

        InputManager inputManager = InputManager.getSingletonInstance();
        inputManager.addListener(KeyBinding.LEFT_FLIPPER, args -> bufferedKeyEvents.add(args));
        inputManager.addListener(KeyBinding.RIGHT_FLIPPER, args -> bufferedKeyEvents.add(args));
        inputManager.addListener(KeyBinding.NUDGE_LEFT, args -> bufferedKeyEvents.add(args));
        inputManager.addListener(KeyBinding.NUDGE_RIGHT, args -> bufferedKeyEvents.add(args));
        inputManager.addListener(KeyBinding.PAUSE, args -> bufferedKeyEvents.add(args));

        physicsElements = new ArrayList<>();
        LoadPhysicsElementList(world);
        world.gameElementsProperty().addListener((observableValue, gameElements, t1) -> LoadPhysicsElementList(world));

        timerTask = new TimerTask()
        {
            /**
             * Diese Methode wird 60 mal pro Sekunde ausgeführt und ist für die physikalischen Berechnungen zuständig.
             */
            @Override
            public void run()
            {
                // TODO check bufferedKeyEvents

                for (PhysicsElement element : physicsElements)
                {
                    for (CircleCollider circle : element.getCircleColliders())
                    {
                        for (CircleCollider ballCircle : ballElement.getCircleColliders())
                        {
                            Vector2 distance = Vector2.sub(ballCircle.getPosition(), circle.getPosition());
                            if (distance.magnitude() < ballCircle.getRadius() + circle.getRadius())
                            {
                                double overlapDistance = (ballCircle.getRadius() + circle.getRadius()) - distance.magnitude();
                                Vector2 pushVector = Vector2.scale(distance.normalized(), overlapDistance);
                            }
                        }
                    }
                }

                // TODO Notify GameElements about collisions
                // TODO Solve collisions
                // TODO Check if ball is lost
                // TODO Simulate ball movement
            }
        };
    }

    /**
     * Lädt die Liste von GameElements aus der World, löscht alle vorhandenen PhysicsElements und ersetzt diese durch
     * neu geneierte.
     * @param world Die Welt aus der die GameElements gelesen werden.
     */
    private void LoadPhysicsElementList(World world)
    {
        physicsElements.clear();
        GameElementList elements = world.getGameElements();
        elements.getElementsWithoutBall().forEach(gameElement -> physicsElements.add(new PhysicsElement(gameElement)));
        ballElement = new PhysicsElement(elements.getBall());
        physicsElements.add(ballElement);
    }

    /**
     * Startet die Physik Schleife
     */
    public void startTicking()
    {
        physicTimer = new Timer(false);
        physicTimer.scheduleAtFixedRate(timerTask, TIMER_DELAY, TICK_RATE);
    }

    /**
     * Stoppt die Physik Schleife
     */
    public void stopTicking()
    {
        if (physicTimer != null)
        {
            physicTimer.cancel();
            physicTimer.purge();
        }
    }
}