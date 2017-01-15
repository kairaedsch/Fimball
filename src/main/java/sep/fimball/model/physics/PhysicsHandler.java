package sep.fimball.model.physics;

import sep.fimball.general.data.PhysicsConfig;
import sep.fimball.model.physics.element.*;
import sep.fimball.model.physics.game.CollisionEventArgs;
import sep.fimball.model.physics.game.ElementEventArgs;
import sep.fimball.model.physics.game.PhysicsGameSession;

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

    /**
     * Enthält die ModifyContainer.
     */
    private List<ModifyContainer> modifyContainers;

    /**
     * Der Monitor, über den die Modifies synchronisiert.
     */
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
    private PhysicsGameSession<GameElementT> gameSession;

    /**
     * Wie viele Physik-Updates in der letzten Sekunde durchgeführt wurden.
     */
    private int framecount;

    /**
     * Der Zeitpunkt, seit dem die Physik-Updates gezählt wurden.
     */
    private long lastTime;

    /**
     * Ein Monitor, über den die Physik synchronisiert wird.
     */
    private final Object physicsMonitor = new Object();

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
     * @param ballPhysicsElement Der physikalische Ball.
     */
    public void init(List<PhysicsElement<GameElementT>> elements, PhysicsGameSession<GameElementT> gameSession, BallPhysicsElement<GameElementT> ballPhysicsElement)
    {
        this.physicsElements = elements;
        this.gameSession = gameSession;
        this.ballPhysicsElement = ballPhysicsElement;
        this.physicTimer = new Timer(false);

        modifyContainers = new ArrayList<>();
    }

    /**
     * Fügt das PhysicsElement zusammen mit dem Modify zum {@code modifiesMonitor} hinzu.
     *
     * @param physicsElement Das PhysicsElement, das mit dem Modify hinzugefügt werden soll.
     * @param modify         Der Modify, der hinzugefügt werden soll.
     * @param <ModifyT>      Der Typ des Modify.
     */
    public <ModifyT extends Modify> void addModify(PhysicsElementModifyAble<GameElementT, ModifyT> physicsElement, ModifyT modify)
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
        lastTime = System.currentTimeMillis();
        physicTimer = new Timer(false);
        physicTimer.scheduleAtFixedRate(createTask(), PhysicsConfig.TIMER_DELAY, PhysicsConfig.TICK_RATE_MILISEC);
    }

    /**
     * Stoppt die Physik-Schleife.
     */
    public void stopTicking()
    {
        physicTimer.cancel();
        physicTimer.purge();
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
                framecount++;
                long currentTime = System.currentTimeMillis();
                if (((double) currentTime - (double) lastTime) > 1000)
                {
                    System.out.println("Physics FPS: " + framecount);
                    framecount = 0;
                    lastTime = currentTime;
                }

                double delta = PhysicsConfig.TICK_RATE_SEC;

                List<ModifyContainer> localModifyContainers;
                synchronized (modifiesMonitor)
                {
                    localModifyContainers = modifyContainers;
                    modifyContainers = new ArrayList<>();
                }

                localModifyContainers.forEach(ModifyContainer::apply);

                // PhysicsElements auf Kollisionen mit dem Ball prüfen
                List<CollisionEventArgs<GameElementT>> collisionEventArgsList = new ArrayList<>();
                List<ElementEventArgs<GameElementT>> elementEventArgsList = new ArrayList<>();

                synchronized (physicsMonitor)
                {
                    checkElementsForCollision(collisionEventArgsList, elementEventArgsList);

                    physicsElements.stream().filter(element -> element instanceof PhysicsUpdatable).forEach(element -> ((PhysicsUpdatable) element).update(delta));
                }
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
        physicsElements.stream().filter(element -> element != ballPhysicsElement).forEach(element ->
        {
            element.checkCollision(collisionEventArgsList, ballPhysicsElement);
            if (element.hasChanged())
            {
                elementEventArgsList.add(new ElementEventArgs<>(element.getGameElement(), element.getPosition(), element.getRotation(), 0));
                element.resetChanged();
            }
        });
        elementEventArgsList.add(new ElementEventArgs<>(ballPhysicsElement.getGameElement(), ballPhysicsElement.getPosition(), ballPhysicsElement.getRotation(), ballPhysicsElement.getHeight()));
    }
}