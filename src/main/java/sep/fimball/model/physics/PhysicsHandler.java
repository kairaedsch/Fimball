package sep.fimball.model.physics;

import sep.fimball.model.input.InputManager;
import sep.fimball.model.input.KeyBinding;
import sep.fimball.model.input.KeyObserverEventArgs;
import sep.fimball.model.World;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Der PhysicsHandler kümmert sich um die Physikalische Simulation des Automaten. Er ist dafür verantwortlich, dass sich der Ball korrekt auf der zweidimensionalen Fläche bewegt. Auch überprüft er ob die Kugel, welche das einzige ElementType ist welches dauerhaft in Bewegung ist, mit anderen Elementen kollidiert. Falls sie dies tut wird die Kollision aufgelöst indem die beiden Elemente voneinander abprallen. Das Objekt mit dem die Kugel kollidiert ist wird über die Kollision informiert. Alle diese Berechnungen führt der PhysicsHandler in einer Schleife aus welche 60 mal pro Sekunde ausgeführt wird. Auch wird überprüft ob die Kugel verloren gegangen ist.
 */
public class PhysicsHandler
{
    /**
     * Der PhysikTimer beginnt ohne Verzögerung wenn er gestartet wird
     */
    private final int TIMER_DELAY = 0;

    /**
     * Gibt an nach wie vielen Millisekunden Wartezeit die run Methode erneut ausgeführt wrid
     */
    private final int TICK_RATE = 1000 / 60;

    /**
     * Eine Referenz auf die Welt auf welcher die physikalischen Berechnungen ausgeführt werden
     */
    private World world;

    /**
     * Der Timer wird zur Erzeugung der Physik Schleife genutzt
     */
    private Timer physicTimer;
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
        world.gameElementsProperty().forEach(gameElement -> physicsElements.add(new PhysicsElement(gameElement)));
        world.gameElementsProperty().addListener((observableValue, gameElements, t1) ->
        {
            physicsElements.clear();
            gameElements.forEach(gameElement -> physicsElements.add(new PhysicsElement(gameElement)));
        });

        timerTask = new TimerTask()
        {
            /**
             * Diese Methode wird 60 mal pro Sekunde ausgeführt und ist für die physikalischen Berechnungen zuständig
             */
            @Override
            public void run()
            {
                // TODO check bufferedKeyEvents
                // TODO check Collisions
                // TODO Notify GameElements about collisions
                // TODO Solve collisions
                // TODO Check if ball is lost
                // TODO Simulate ball movement
            }
        };
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