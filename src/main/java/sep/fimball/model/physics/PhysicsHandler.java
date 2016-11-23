package sep.fimball.model.physics;

import sep.fimball.general.data.Vector2;
import sep.fimball.model.blueprint.settings.KeyBinding;
import sep.fimball.model.input.InputManager;
import sep.fimball.model.input.KeyObserverEventArgs;
import sep.fimball.model.physics.collider.Collider;
import sep.fimball.model.physics.element.BallPhysicsElement;
import sep.fimball.model.physics.element.PhysicsElement;
import sep.fimball.model.physics.game.CollisionEventArgs;
import sep.fimball.model.physics.game.ElementEventArgs;
import sep.fimball.model.physics.game.PhysicGameSession;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Der PhysicsHandler kümmert sich um die Physikalische Simulation des Automaten. Er ist dafür verantwortlich, dass sich der Ball korrekt auf der zweidimensionalen Fläche bewegt. Auch überprüft er ob die Kugel, welche das einzige BaseElement ist welches dauerhaft in Bewegung ist, mit anderen Elementen kollidiert. Falls sie dies tut wird die Kollision aufgelöst indem die beiden Elemente voneinander abprallen. Alle diese Berechnungen führt der PhysicsHandler in einer Schleife aus.
 *
 * @param <GameElementT> TODO
 */
public class PhysicsHandler<GameElementT>
{
    /**
     * Die Verzögerung des Startens des Physic Timers in Millisekunden.
     */
    public final static int TIMER_DELAY = 0;

    /**
     * Gibt an nach wie vielen Millisekunden Wartezeit der nächste Schritt der Physikschleife ausgeführt wird.
     */
    public final static int TICK_RATE = 1000 / 120;

    /**
     * In m/s^2. Gibt an wie stark der Ball auf der y-Achse nach Unten beschleunigt wird. Dabei wurde die Neigung des Tisches schon mit eingerechnet: 9.81 m/s^2 * sin(7°), wobei 9.81 m/s^2 die Schwerkraftkonstante und 7° die angenommene Neigung ist.
     */
    private final double GRAVITY = 1.19554 * 20;

    /**
     * Der aktuelle Spielball.
     */
    private BallPhysicsElement ballPhysicsElement;

    /**
     * Der Timer wird zur Erzeugung der Physik Schleife genutzt.
     */
    private Timer physicTimer;

    /**
     * Hier werden alle Tastendrücke die vom InputManager an den PhysikHandler mithilfe des Observer Pattern gesendet
     * wurden gespeichert. Im nächsten Physik Schritt kann der PhysikHandler diese dann abarbeiten. Dies ist notwendig
     * da das Observer Pattern nicht zur Thread-übergreifenden Kommunikation gedacht ist.
     */
    private List<KeyObserverEventArgs> bufferedKeyEvents;

    /**
     * Eine Liste aller PhysicsElements auf welche die Berechnungen angewendet werden sollen.
     */
    private List<PhysicsElement<GameElementT>> physicsElements;

    /**
     * Die aktive GameSession, die mögliche Events von der Physik bekommen soll.
     */
    private PhysicGameSession gameSession;

    /**
     * Die Maximale Y-Position aller PhysicElement.
     */
    private double maxElementPosY;

    //TODO entfernen
    private final boolean debug = false;

    /**
     * Erzeugt einen neuen PhysicsHandler mit den gegebenen Element.
     *
     * @param elements       Die Elemente, die der PhysicsHandler zur Berechnung der Physik nutzen soll.
     * @param gameSession    Die zugehörige GameSession.
     * @param maxElementPosY TODO
     */
    public PhysicsHandler(List<PhysicsElement<GameElementT>> elements, PhysicGameSession gameSession, double maxElementPosY)
    {
        this.physicsElements = elements;
        this.gameSession = gameSession;
        this.maxElementPosY = maxElementPosY;

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
     *
     * @param ball Der Ball, der hinzugefügt werden soll.
     */
    public void addBall(BallPhysicsElement ball)
    {
        ballPhysicsElement = ball;
        synchronized (physicsElements)
        {
            physicsElements.add(ball.getSubElement());
        }
    }

    /**
     * Entfernt den aktuellen Ball aus dem Spiel.
     */
    public void removeBall()
    {
        ballPhysicsElement = null;
    }

    /**
     * Startet die Physik-Schleife.
     */
    public void startTicking()
    {
        physicTimer = new Timer(false);
        physicTimer.scheduleAtFixedRate(createTask(), TIMER_DELAY, TICK_RATE);
    }

    /**
     * Erstellt einen neuen TimerTask, welcher die Berechnung der Physik ausführt.
     *
     * @return Den TimerTask.
     */
    private TimerTask createTask()
    {
        return new TimerTask()
        {
            /**
             * Diese Methode wird 60 mal pro Sekunde ausgeführt und ist für die physikalischen Berechnungen zuständig.
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

                if (ballPhysicsElement != null)
                {
                    double delta = TICK_RATE / 1000.0;

                    // Wende Schwerkraft auf den Ball an
                    ballPhysicsElement.setVelocity(ballPhysicsElement.getVelocity().plus(new Vector2(0.0, GRAVITY * delta)));

                    // Bewege den Ball
                    ballPhysicsElement.setPosition(ballPhysicsElement.getPosition().plus(ballPhysicsElement.getVelocity().scale(delta)));

                    if (ballPhysicsElement.getPosition().getY() >= maxElementPosY)
                    {
                        gameSession.setBallLost(true);
                    }
                }

                List<CollisionEventArgs> collisionEventArgses = new ArrayList<>();
                List<ElementEventArgs> elementEventArgses = new ArrayList<>();

                synchronized (physicsElements)
                {
                    for (PhysicsElement<GameElementT> element : physicsElements)
                    {
                        if (ballPhysicsElement != null && element != ballPhysicsElement.getSubElement())
                        {
                            for (Collider collider : element.getColliders())
                            {
                                boolean hit = collider.checkCollision(ballPhysicsElement, element.getPosition(), element.getRotation(), element.getBasePhysicsElement().getPivotPoint());

                                if (hit)
                                {
                                    collisionEventArgses.add(new CollisionEventArgs<>(element.getGameElement(), collider.getId()));
                                }
                            }
                        }
                        elementEventArgses.add(new ElementEventArgs<>(element.getGameElement(), element.getPosition(), element.getRotation()));
                    }
                }

                gameSession.addEventArgs(collisionEventArgses, elementEventArgses);

                // TODO Notify GameElements about collisions

                // TODO Check if ball is lost
            }
        };
    }

    /**
     * Stoppt die Physik-Schleife.
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