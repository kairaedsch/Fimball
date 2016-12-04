package sep.fimball.model.physics;

import sep.fimball.general.data.Vector2;
import sep.fimball.model.input.data.KeyBinding;
import sep.fimball.model.input.manager.InputManager;
import sep.fimball.model.input.manager.KeyObserverEventArgs;
import sep.fimball.model.physics.element.BallPhysicsElement;
import sep.fimball.model.physics.element.FlipperPhysicsElement;
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
 * @param <GameElementT> Generisches GameElement.
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
     * Der aktuelle Spielball.
     */
    private BallPhysicsElement<GameElementT> ballPhysicsElement;

    private List<FlipperPhysicsElement<GameElementT>> leftFlippers;
    private List<FlipperPhysicsElement<GameElementT>> rightFlippers;

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
    private PhysicGameSession<GameElementT> gameSession;

    /**
     * Die maximale Y-Position aller PhysicElements.
     */
    private double maxElementPosY;

    private final Object monitor = new Object();

    /**
     * Erzeugt einen neuen PhysicsHandler mit den gegebenen Element.
     *
     * @param elements       Die Elemente, die der PhysicsHandler zur Berechnung der Physik nutzen soll.
     * @param gameSession    Die zugehörige GameSession.
     * @param maxElementPosY Die maximale Y-Position aller PhysicElements.
     */
    public PhysicsHandler(List<PhysicsElement<GameElementT>> elements, PhysicGameSession<GameElementT> gameSession, double maxElementPosY, BallPhysicsElement<GameElementT> ballPhysicsElement, List<FlipperPhysicsElement<GameElementT>> leftFlippers, List<FlipperPhysicsElement<GameElementT>> rightFlippers)
    {
        this.physicsElements = elements;
        this.gameSession = gameSession;
        this.maxElementPosY = maxElementPosY;
        this.ballPhysicsElement = ballPhysicsElement;
        this.leftFlippers = leftFlippers;
        this.rightFlippers = rightFlippers;

        bufferedKeyEvents = new ArrayList<>();

        InputManager inputManager = InputManager.getSingletonInstance();
        inputManager.addListener(KeyBinding.LEFT_FLIPPER, args ->
        {
            synchronized (bufferedKeyEvents)
            {
                bufferedKeyEvents.add(args);
            }
        });
        inputManager.addListener(KeyBinding.RIGHT_FLIPPER, args ->
        {
            synchronized (bufferedKeyEvents)
            {
                bufferedKeyEvents.add(args);
            }
        });
        inputManager.addListener(KeyBinding.NUDGE_LEFT, args ->
        {
            synchronized (bufferedKeyEvents)
            {
                bufferedKeyEvents.add(args);
            }
        });
        inputManager.addListener(KeyBinding.NUDGE_RIGHT, args ->
        {
            synchronized (bufferedKeyEvents)
            {
                bufferedKeyEvents.add(args);
            }
        });
        inputManager.addListener(KeyBinding.PAUSE, args ->
        {
            synchronized (bufferedKeyEvents)
            {
                bufferedKeyEvents.add(args);
            }
        });
    }

    /**
     * Fügt den {@code ball} zu den Elementen des PhysicsHandler hinzu.
     */
    public void setBall(Vector2 position, double rotation)
    {
        synchronized (monitor)
        {
            ballPhysicsElement.setRotation(rotation);
            ballPhysicsElement.setPosition(position);
            ballPhysicsElement.setVelocity(new Vector2(0, 0));
            ballPhysicsElement.setAngularVelocity(0);
        }
    }

    /**
     * TODO
     *
     * @param left
     */
    public void nudge(boolean left)
    {
        // TODO
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
                double delta = TICK_RATE / 1000.0;

                // Check bufferedKeyEvents
                synchronized (bufferedKeyEvents)
                {
                    for (KeyObserverEventArgs args : bufferedKeyEvents)
                    {
                        switch (args.getBinding())
                        {
                            case LEFT_FLIPPER:
                                leftFlippers.forEach(flipper ->
                                {
                                    if (args.getState() == KeyObserverEventArgs.KeyChangedToState.DOWN)
                                        flipper.rotateDown();
                                    else
                                        flipper.rotateUp();
                                });
                                break;
                            case RIGHT_FLIPPER:
                                rightFlippers.forEach(flipper ->
                                {
                                    if (args.getState() == KeyObserverEventArgs.KeyChangedToState.DOWN)
                                        flipper.rotateDown();
                                    else
                                        flipper.rotateUp();
                                });
                                break;
                        }
                    }
                }

                // Check all PhysicsElements for collisions with the ball
                List<CollisionEventArgs<GameElementT>> collisionEventArgsList = new ArrayList<>();
                List<ElementEventArgs<GameElementT>> elementEventArgsList = new ArrayList<>();
                boolean ballLost = false;

                synchronized (monitor)
                {
                    if (ballPhysicsElement != null)
                    {
                        ballPhysicsElement.update(delta);

                        if (ballPhysicsElement.getPosition().getY() >= maxElementPosY)
                        {
                            ballLost = true;
                        }
                    }

                    for (PhysicsElement<GameElementT> element : physicsElements)
                    {
                        if (ballPhysicsElement != null && element != ballPhysicsElement)
                        {
                            element.checkCollision(collisionEventArgsList, ballPhysicsElement);
                        }

                        double scale = element == ballPhysicsElement ? ballPhysicsElement.getScale() : 1;
                        elementEventArgsList.add(new ElementEventArgs<>(element.getGameElement(), element.getPosition(), element.getRotation(), scale));

                    }
                }

                leftFlippers.forEach(flipper -> flipper.update(delta));
                rightFlippers.forEach(flipper -> flipper.update(delta));

                gameSession.setBallLost(ballLost);
                gameSession.addEventArgs(collisionEventArgsList, elementEventArgsList);
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