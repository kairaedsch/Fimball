package sep.fimball.model.physics;

import sep.fimball.general.data.Vector2;
import sep.fimball.model.input.InputManager;
import sep.fimball.model.input.KeyBinding;
import sep.fimball.model.input.KeyObserverEventArgs;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Der PhysicsHandler kümmert sich um die Physikalische Simulation des Automaten. Er ist dafür verantwortlich, dass sich der Ball korrekt auf der zweidimensionalen Fläche bewegt. Auch überprüft er ob die Kugel, welche das einzige BaseElement ist welches dauerhaft in Bewegung ist, mit anderen Elementen kollidiert. Falls sie dies tut wird die Kollision aufgelöst indem die beiden Elemente voneinander abprallen. Das Objekt mit dem die Kugel kollidiert ist wird über die Kollision informiert. Alle diese Berechnungen führt der PhysicsHandler in einer Schleife aus welche 60 mal pro Sekunde ausgeführt wird. Auch wird überprüft ob die Kugel verloren gegangen ist.
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
     * In m/s^2. Gibt an wie stark der Ball auf der y-Achse nach Unten beschleunigt wird. Dabei wurde die Neigung des Tisches schon mit einberechnet: 9.81 m/s^2 * sin(7°), wobei 9.81 m/s^2 die Schwerkraftkonstante und 7° die angenommene Neigung ist.
     */
    private final double GRAVITY = 1.19554;

    /**
     * Der aktuelle Spielball.
     */
    private BallElement ballElement;

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

    private final boolean debug = false;

    /**
     * Zrzeugt einen neuen PhysicsHandler mit den gegebenen Element.
     * @param elements Die Elemente, die der PhysicsHandler zur Berechnung der Physik nutzen soll.
     */
    public PhysicsHandler(List<PhysicsElement> elements)
    {
        this.physicsElements = elements;

        bufferedKeyEvents = new ArrayList<>();

        InputManager inputManager = InputManager.getSingletonInstance();
        inputManager.addListener(KeyBinding.LEFT_FLIPPER, args -> bufferedKeyEvents.add(args));
        inputManager.addListener(KeyBinding.RIGHT_FLIPPER, args -> bufferedKeyEvents.add(args));
        inputManager.addListener(KeyBinding.NUDGE_LEFT, args -> bufferedKeyEvents.add(args));
        inputManager.addListener(KeyBinding.NUDGE_RIGHT, args -> bufferedKeyEvents.add(args));
        inputManager.addListener(KeyBinding.PAUSE, args -> bufferedKeyEvents.add(args));
        inputManager.addListener(KeyBinding.DEBUG_LEFT, args -> bufferedKeyEvents.add(args));
        inputManager.addListener(KeyBinding.DEBUG_RIGHT, args -> bufferedKeyEvents.add(args));
    }

    /**
     * Fügt den {@code ball} zu den Elementen des PhysicsHandler hinzu.
     * @param ball Der Ball, der hinzugefügt werden soll.
     */
    public void addBall(BallElement ball)
    {
        ballElement = ball;
        physicsElements.add(ball.getSubElement());
    }

    /**
     * Entfernt den aktuellen Ball aus dem Spiel.
     */
    public void removeBall()
    {
        ballElement = null;
    }

    /**
     * Startet die Physik Schleife
     */
    public void startTicking()
    {
        physicTimer = new Timer(false);
        physicTimer.scheduleAtFixedRate(createTask(), TIMER_DELAY, TICK_RATE);
    }

    private TimerTask createTask()
    {
        return new TimerTask()
        {
            /**
             * Diese Methode wird 60 mal pro Sekund e ausgeführt und ist für die physikalischen Berechnungen zuständig.
             */
            @Override
            public void run()
            {
                boolean cont = !debug;

                // TODO check bufferedKeyEvents
                for (KeyObserverEventArgs args : bufferedKeyEvents)
                {
                    if (args.getBinding() == KeyBinding.DEBUG_RIGHT)
                        cont = true;
                }

                if (!cont)
                    return;

                // Check all PhysicsElements for collisions with the ball

                if (ballElement != null)
                {
                    double delta = TICK_RATE / 1000.0;

                    // Wende Schwerkraft auf den Ball an
                    ballElement.setVelocity(Vector2.add(ballElement.getVelocity(), new Vector2(0.0, GRAVITY * delta)));

                    // Bewege den Ball
                    ballElement.setPosition(Vector2.add(ballElement.getPosition(), Vector2.scale(ballElement.getVelocity(), delta)));
                }

                for (PhysicsElement element : physicsElements)
                {
                    if (ballElement != null && element != ballElement.getSubElement())
                    {
                        for (Collider collider : element.getColliders())
                        {
                            collider.checkCollision(ballElement, element.getPosition());
                        }
                    }
                    element.writeToGameElement();
                }

                // TODO Notify GameElements about collisions

                // TODO Check if ball is lost
            }
        };
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