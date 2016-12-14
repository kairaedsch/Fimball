package sep.fimball.model.physics;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import sep.fimball.general.data.PhysicsConfig;
import sep.fimball.general.data.Vector2;
import sep.fimball.model.input.data.KeyBinding;
import sep.fimball.model.input.manager.InputManager;
import sep.fimball.model.input.manager.KeyObserverEventArgs;
import sep.fimball.model.physics.element.*;
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
     * Der aktuelle Spielball.
     */
    private BallPhysicsElement<GameElementT> ballPhysicsElement;

    private List<Modifi> modifis;
    private final Object modifisMonitor = new Object();

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

    /**
     * Gibt an, ob die Kugel verloren ist.
     */
    private boolean ballLost;

    /**
     * Ein Monitor, über den synchronisiert wird.
     * TODO
     */
    private final Object monitor = new Object();

    /**
     * Gibt an, ob die Physik auf auf User Input reagiert.
     */
    private boolean reactingToInput;

    /**
     * Erzeugt einen neuen leeren PhysicsHandler.
     */
    public PhysicsHandler()
    {

    }

    /**
     * Initialisiert diesen PhysicsHandler mit den gegebenen Elementen.
     *
     * @param elements           Die Elemente, die der PhysicsHandler zur Berechnung der Physik nutzen soll.
     * @param gameSession        Die zugehörige GameSession.
     * @param maxElementPosY     Die maximale Y-Position aller PhysicElements.
     * @param ballPhysicsElement Der physikalische Ball.
     */
    public void init(List<PhysicsElement<GameElementT>> elements, PhysicGameSession<GameElementT> gameSession, double maxElementPosY, BallPhysicsElement<GameElementT> ballPhysicsElement)
    {
        this.physicsElements = elements;
        this.gameSession = gameSession;
        this.maxElementPosY = maxElementPosY;
        this.ballPhysicsElement = ballPhysicsElement;
        this.ballLost = false;
        this.reactingToInput = true;

        bufferedKeyEvents = new ArrayList<>();
        modifis = new ArrayList<>();

        addListenersToInputManager();
    }

    /**
     * Meldet die Listener für die benötigten Tasten beim InputManager an.
     */
    private void addListenersToInputManager()
    {
        InputManager inputManager = InputManager.getSingletonInstance();
        inputManager.addListener(KeyBinding.NUDGE_LEFT, this::addToKeyEvents);
        inputManager.addListener(KeyBinding.NUDGE_RIGHT, this::addToKeyEvents);
    }

    /**
     * Fügt die {@code args} zu den gebufferten KeyEvents hinzu.
     *
     * @param args Die Argumente, die hinzugefügt werden sollen.
     */
    private void addToKeyEvents(KeyObserverEventArgs args)
    {
        if (reactingToInput)
        {
            synchronized (bufferedKeyEvents)
            {
                bufferedKeyEvents.add(args);
            }
        }
    }

    public void addModifi(Modifi modifi)
    {
        synchronized (modifisMonitor)
        {
            modifis.add(modifi);
        }
    }

    /**
     * Fügt den {@code ball} zu den Elementen des PhysicsHandler hinzu.
     *
     * @param position Die Position des Balls.
     * @param rotation Die Rotation des Balls.
     */
    public void setBall(Vector2 position, double rotation)
    {
        synchronized (monitor)
        {
            ballPhysicsElement.setRotation(rotation);
            ballPhysicsElement.setPosition(position);
            ballPhysicsElement.setVelocity(new Vector2());
            ballLost = false;
        }
    }


    /**
     * Simuliert das Stoßen am Automaten.
     *
     * @param left Gibt an, ob von links gestoßen wurde.
     */
    private void nudge(boolean left)
    {
        gameSession.nudge();
        if (left)
        {
            accelerateBallInX(PhysicsConfig.NUDGE_VELOCITY);
        }
        else
        {
            accelerateBallInX(-PhysicsConfig.NUDGE_VELOCITY);
        }
    }

    /**
     * Erhöht die Geschwindigkeit des Balls in x-Richtung.
     *
     * @param additionalVelocity Die Geschwindigkeit, die auf die x-Geschwindigkeit des Balls gerechnet wird.
     */
    private void accelerateBallInX(int additionalVelocity)
    {
        ballPhysicsElement.setVelocity(new Vector2(ballPhysicsElement.getVelocity().getX() + additionalVelocity, ballPhysicsElement.getVelocity().getY()));
    }

    /**
     * Startet die Physik-Schleife.
     */
    public void startTicking()
    {
        physicTimer = new Timer(false);
        physicTimer.scheduleAtFixedRate(createTask(), PhysicsConfig.TIMER_DELAY, PhysicsConfig.TICK_RATE_MILISEC);
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
                double delta = PhysicsConfig.TICK_RATE_SEC;

                // Check bufferedKeyEvents
                synchronized (bufferedKeyEvents)
                {
                    checkKeyEvents();
                }

                List<Modifi> localModifis;
                synchronized (modifisMonitor)
                {
                    localModifis = modifis;
                    modifis = new ArrayList<>();
                }

                for (Modifi modifi : localModifis)
                {
                    modifi.getPhysicsElement().applyModifi(modifi);
                }

                // Check all PhysicsElements for collisions with the ball
                List<CollisionEventArgs<GameElementT>> collisionEventArgsList = new ArrayList<>();
                List<ElementEventArgs<GameElementT>> elementEventArgsList = new ArrayList<>();
                boolean localBallLost = false;

                synchronized (monitor)
                {
                    checkElementsForCollision(collisionEventArgsList, elementEventArgsList);

                    for (PhysicsElement<GameElementT> element : physicsElements)
                    {
                        if (element instanceof PhysicsUpdateable)
                        {
                            ((PhysicsUpdateable) element).update(delta);
                        }
                    }

                    if (ballPhysicsElement != null)
                    {
                        ballPhysicsElement.update(delta);

                        if (ballPhysicsElement.getPosition().getY() >= maxElementPosY)
                        {
                            localBallLost = true;
                        }
                    }

                    if (ballLost)
                        localBallLost = false;
                    if (localBallLost)
                        ballLost = true;
                }

                if (localBallLost)
                    gameSession.setBallLost(true);
                gameSession.addEventArgs(collisionEventArgsList, elementEventArgsList);
            }
        };
    }

    /**
     * Prüft alle PhysicsElements auf Kollisionen mit dem Ball und fügt gegebenenfalls Argumente zu den gegebenen
     * Event-Argument-Listen hinzu.
     *
     * @param collisionEventArgsList Die Liste der CollisionEvents.
     * @param elementEventArgsList   Die Liste der ElementEvents.
     */
    private void checkElementsForCollision(List<CollisionEventArgs<GameElementT>> collisionEventArgsList, List<ElementEventArgs<GameElementT>> elementEventArgsList)
    {
        for (PhysicsElement<GameElementT> element : physicsElements)
        {
            if (element != ballPhysicsElement)
            {
                element.checkCollision(collisionEventArgsList, ballPhysicsElement);
                if (element.hasChanged())
                {
                    elementEventArgsList.add(new ElementEventArgs<>(element.getGameElement(), element.getPosition(), element.getRotation(), 0));
                    element.setChanged(false);
                }
            }
            else
            {
                elementEventArgsList.add(new ElementEventArgs<>(element.getGameElement(), element.getPosition(), element.getRotation(), ballPhysicsElement.getHeight()));
            }
        }
    }

    /**
     * Arbeitet die {@code bufferedKeyEvents} ab.
     */
    private void checkKeyEvents()
    {
        for (KeyObserverEventArgs args : bufferedKeyEvents)
        {
            switch (args.getBinding())
            {
                case NUDGE_LEFT:
                    if (args.getState() == KeyObserverEventArgs.KeyChangedToState.DOWN)
                    {
                        nudge(true);
                    }
                    break;
                case NUDGE_RIGHT:
                    if (args.getState() == KeyObserverEventArgs.KeyChangedToState.DOWN)
                    {
                        nudge(false);
                    }
                    break;
            }
        }
        bufferedKeyEvents.clear();
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

    /**
     * Stoppt das Reagieren auf User Input.
     */
    public void stopReactingToUserInput()
    {
        reactingToInput = false;
    }

    /**
     * Der PhysicsHandler soll wieder auf User Input reagieren.
     */
    public void doReactToUserInput()
    {
        reactingToInput = true;
    }
}