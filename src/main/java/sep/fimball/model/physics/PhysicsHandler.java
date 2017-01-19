package sep.fimball.model.physics;

import sep.fimball.general.data.PhysicsConfig;
import sep.fimball.general.data.Vector2;
import sep.fimball.general.util.RegionHashConverter;
import sep.fimball.model.physics.element.*;
import sep.fimball.model.physics.game.CollisionEventArgs;
import sep.fimball.model.physics.game.ElementEventArgs;
import sep.fimball.model.physics.game.PhysicsGameSession;

import java.util.*;
import java.util.stream.Collectors;

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
     * Eine Liste aller PhysicsElements, welche eine update methode haben.
     */
    private List<PhysicsUpdatable<GameElementT>> updatablePhysicsElements;

    /**
     * Eine Liste aller PhysicsElements auf welche die Berechnungen angewendet werden sollen.
     */
    private List<PhysicsElement<GameElementT>> physicsElements;

    /**
     * Eine Liste aller PhysicsElements OHNE BALL, für schnellen und nach Position sortiertem Zugriff.
     * Der Long-Key der HashMap ist ein aus der Position des PhysicsElements generierter hash, der naheliegende Elemente in die selbe Gruppe einfügt.
     */
    private HashMap<Long, List<PhysicsElement<GameElementT>>> physicsElementsMap;

    /**
     * Die aktive GameSession, die mögliche Events von der Physik bekommen soll.
     */
    private PhysicsGameSession<GameElementT> gameSession;

    /**
     * Wie viele Physik-Updates in der letzten Sekunde durchgeführt wurden.
     */
    private int frameCount;

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
        physicsElementsMap = new HashMap<>();

        for (PhysicsElement<GameElementT> element : elements)
        {
            if (element instanceof BallPhysicsElement)
                continue;

            for (Long hash : getElementRegionHashes(element))
            {
                if (physicsElementsMap.containsKey(hash))
                    physicsElementsMap.get(hash).add(element);
                else
                {
                    List<PhysicsElement<GameElementT>> list = new ArrayList<>();
                    list.add(element);
                    physicsElementsMap.put(hash, list);
                }
            }
        }

        this.gameSession = gameSession;
        this.ballPhysicsElement = ballPhysicsElement;
        this.physicTimer = new Timer(false);

        modifyContainers = new ArrayList<>();

        updatablePhysicsElements = physicsElements
                .stream()
                .filter(element -> element instanceof PhysicsUpdatable)
                .map(element -> (PhysicsUpdatable<GameElementT>) element)
                .collect(Collectors.toList());
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
        physicTimer.schedule(createTask(), PhysicsConfig.TIMER_DELAY, PhysicsConfig.TICK_RATE_MILISEC * PhysicsConfig.LOOPS_PER_TICK);
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
                frameCount++;
                long currentTime = System.currentTimeMillis();
                if (((double) currentTime - (double) lastTime) > 1000)
                {
                    System.out.println("Physics FPS: " + frameCount);
                    frameCount = 0;
                    lastTime = currentTime;
                }

                double delta = PhysicsConfig.TICK_RATE_SEC;
                // PhysicsElements auf Kollisionen mit dem Ball prüfen
                List<CollisionEventArgs<GameElementT>> collisionEventArgsList = new ArrayList<>();
                List<ElementEventArgs<GameElementT>> elementEventArgsList = new ArrayList<>();

                for (int i = 0; i < PhysicsConfig.LOOPS_PER_TICK; i++)
                {
                    List<ModifyContainer> localModifyContainers;
                    synchronized (modifiesMonitor)
                    {
                        localModifyContainers = modifyContainers;
                        modifyContainers = new ArrayList<>();
                    }

                    localModifyContainers.forEach(ModifyContainer::apply);

                    synchronized (physicsMonitor)
                    {
                        checkElementsForCollision(collisionEventArgsList, elementEventArgsList);
                        updatablePhysicsElements.forEach(element -> element.update(delta));
                    }
                }
                updatablePhysicsElements.forEach(element ->
                {
                    if (element.hasChanged())
                    {
                        elementEventArgsList.add(element.getStatus());
                        element.resetChanged();
                    }
                });
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
        for (Long hash : getElementRegionHashes(ballPhysicsElement))
        {
            if (physicsElementsMap.containsKey(hash))
            {
                physicsElementsMap.get(hash)
                        .forEach(element -> element.checkCollision(collisionEventArgsList, ballPhysicsElement));
            }
        }
    }

    /**
     * Berechnet die Hashes für die Position eines Elements, die für den Zugriff auf die physicsElementMap benötigt werden.
     *
     * @param element Element für das die Hashes berechnet werden.
     * @return Eine Liste von Positions-Hashes.
     */
    private List<Long> getElementRegionHashes(PhysicsElement<GameElementT> element)
    {
        Vector2 minPos = element.getPosition().plus(element.getBasePhysicsElement().getExtremePos(element.getRotation(), false));
        Vector2 maxPos = element.getPosition().plus(element.getBasePhysicsElement().getExtremePos(element.getRotation(), true));
        return RegionHashConverter.gameAreaToRegionHashes(minPos, maxPos, PhysicsConfig.PHYSICS_REGION_SIZE);
    }
}