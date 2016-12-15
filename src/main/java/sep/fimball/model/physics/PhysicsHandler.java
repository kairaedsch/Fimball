package sep.fimball.model.physics;

import sep.fimball.general.data.PhysicsConfig;
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

    private List<ModifyContainer> modifyContainers;

    private final Object modifiesMonitor = new Object();

    /**
     * Der Timer wird zur Erzeugung der Physik Schleife genutzt.
     */
    private Timer physicTimer;

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
        this.physicTimer = new Timer(false);

        modifyContainers = new ArrayList<>();
    }

    public <ModifyT extends Modify> void addModify(PhysicsModifyAble<ModifyT> physicsElement, ModifyT modify)
    {
        synchronized (modifiesMonitor)
        {
            modifyContainers.add(new ModifyContainer<>(physicsElement, modify));
        }
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

                List<ModifyContainer> localModifyContainers;
                synchronized (modifiesMonitor)
                {
                    localModifyContainers = modifyContainers;
                    modifyContainers = new ArrayList<>();
                }

                for (ModifyContainer modifyContainer : localModifyContainers)
                {
                    modifyContainer.apply();
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
                        if (element instanceof PhysicsUpdateAble)
                        {
                            ((PhysicsUpdateAble) element).update(delta);
                        }
                    }

                    if (ballPhysicsElement != null)
                    {
                        if (ballPhysicsElement.getPosition().getY() >= maxElementPosY)
                        {
                            localBallLost = true;
                        }
                    }

                    if (ballLost) localBallLost = false;
                    if (localBallLost) ballLost = true;
                }

                if (localBallLost) gameSession.setBallLost(true);
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
                    element.resetChanged();
                }
            }
        }
        elementEventArgsList.add(new ElementEventArgs<>(ballPhysicsElement.getGameElement(), ballPhysicsElement.getPosition(), ballPhysicsElement.getRotation(), ballPhysicsElement.getHeight()));
    }

    /**
     * Stoppt die Physik-Schleife.
     */
    public void stopTicking()
    {
        physicTimer.cancel();
        physicTimer.purge();
    }
}