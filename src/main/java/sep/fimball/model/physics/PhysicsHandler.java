package sep.fimball.model.physics;

import sep.fimball.general.data.PhysicsConfig;
import sep.fimball.general.data.Vector2;
import sep.fimball.model.physics.element.*;
import sep.fimball.model.physics.game.CollisionEventArgs;
import sep.fimball.model.physics.game.ElementEventArgs;
import sep.fimball.model.physics.game.PhysicsGameSession;

import java.util.*;

/**
 * Der PhysicsHandler kümmert sich um die Physikalische Simulation des Automaten. Er ist dafür verantwortlich, dass sich der Ball korrekt auf der zweidimensionalen Fläche bewegt. Auch überprüft er ob die Kugel, welche das einzige BaseElement ist welches dauerhaft in Bewegung ist, mit anderen Elementen kollidiert. Falls sie dies tut wird die Kollision aufgelöst indem die beiden Elemente voneinander abprallen. Alle diese Berechnungen führt der PhysicsHandler in einer Schleife aus.
 *
 * @param <GameElementT> Generisches GameElement.
 */
public class PhysicsHandler<GameElementT>
{
    private static class IntegerVector2
    {
        private int x;
        private int y;

        public IntegerVector2(int x, int y)
        {
            this.x = x;
            this.y = y;
        }

        public int getX()
        {
            return x;
        }

        public int getY()
        {
            return y;
        }
    }

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
        physicsElementsMap = new HashMap<>();

        for (PhysicsElement<GameElementT> element : elements)
        {
            if (element instanceof BallPhysicsElement)
                continue;

            for (Long hash : getElementPositionHashes(element))
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
        physicTimer.schedule(createTask(), PhysicsConfig.TIMER_DELAY, PhysicsConfig.TICK_RATE_MILISEC);
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

                    physicsElements
                            .stream()
                            .filter(element -> element instanceof PhysicsUpdatable)
                            .forEach(element ->
                            {
                                ((PhysicsUpdatable) element).update(delta);
                                if (element.hasChanged())
                                {
                                    elementEventArgsList.add(new ElementEventArgs<>(element.getGameElement(), element.getPosition(), element.getRotation(), 0));
                                    element.resetChanged();
                                }
                            });

                    elementEventArgsList.add(new ElementEventArgs<>(ballPhysicsElement.getGameElement(), ballPhysicsElement.getPosition(), ballPhysicsElement.getRotation(), ballPhysicsElement.getHeight()));
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
        for (Long hash : getElementPositionHashes(ballPhysicsElement))
        {
            if (physicsElementsMap.containsKey(hash))
            {
                physicsElementsMap.get(hash)
                        .forEach(element ->
                        {
                            element.checkCollision(collisionEventArgsList, ballPhysicsElement);
                            if (element.hasChanged())
                            {
                                elementEventArgsList.add(new ElementEventArgs<>(element.getGameElement(), element.getPosition(), element.getRotation(), 0));
                                element.resetChanged();
                            }
                        });
            }
        }
    }

    /**
     * Berechnet die Hashes für die Position eines Elements, die für den Zugriff auf die physicsElementMap benötigt werden.
     * @param element Element für das die Hashes berechnet werden.
     * @return Eine Liste von Positions-Hashes.
     */
    private List<Long> getElementPositionHashes(PhysicsElement<GameElementT> element)
    {
        List<Long> result = new ArrayList<>();

        Vector2 minPos = element.getPosition().plus(element.getBasePhysicsElement().getExtremePos(element.getRotation(), false));
        Vector2 maxPos = element.getPosition().plus(element.getBasePhysicsElement().getExtremePos(element.getRotation(), true));

        IntegerVector2 minRegion = getPositionRegion(minPos);
        IntegerVector2 maxRegion = getPositionRegion(maxPos);

        for (int x = minRegion.getX(); x <= maxRegion.getX(); x++)
        {
            for (int y = minRegion.getY(); y <= maxRegion.getY(); y++)
            {
                long hash = getPositionHash(new IntegerVector2(x, y));
                result.add(hash);
            }
        }

        return result;
    }

    /**
     * Gibt die Region eines Vektors auf dem Spielfeld an.
     * @param position Vektor für den die Region berechnet wird.
     * @return Region, in der sich der Vektor befindet.
     */
    private IntegerVector2 getPositionRegion(Vector2 position)
    {
        final long REGION_SIZE = 10;
        int x = (int)Math.ceil(position.getX() / REGION_SIZE);
        int y = (int)Math.ceil(position.getY() / REGION_SIZE);
        return new IntegerVector2(x, y);
    }

    /**
     * Berechnet den Hash einer Spielfeld-Region.
     * @param region Region, für die der Hash berechnet werden soll.
     * @return Hash für eine Region.
     */
    private long getPositionHash(IntegerVector2 region)
    {
        long hash = region.getX();
        long shiftedY = region.getY();
        shiftedY <<= 32;
        hash |= shiftedY;
        return hash;
    }
}