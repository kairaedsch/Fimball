package sep.fimball.model.physics.element;

import sep.fimball.general.data.Vector2;
import sep.fimball.model.physics.collider.Collider;
import sep.fimball.model.physics.game.CollisionEventArgs;
import sep.fimball.model.physics.game.CollisionEventType;
import sep.fimball.model.physics.game.ElementEventArgs;

import java.util.*;

/**
 * Repräsentiert ein GameElement in der Berechnung der Physik. Der PhysicsHandler arbeitet nicht direkt auf GameElement um gleichzeitigen Zugriff von der Zeichenschleife und der Physikschleife zu vermeiden.
 *
 * @param <GameElementT> Die Klasse des korrespondierenden GameElements.
 */
public class PhysicsElement<GameElementT>
{
    /**
     * Die aktuelle Position.
     */
    private Vector2 position;

    /**
     * Die aktuelle Rotation.
     */
    private double rotation;

    /**
     * Der Multiplier für die Stärke der Collider.
     */
    private double strengthMultiplier;

    /**
     * Gibt an ob sich Position oder Rotation des physikalischen Elements geändert haben.
     */
    private boolean changed = false;

    /**
     * Die Liste der Collider, welche die Kollisionsflächen dieses Elements darstellen.
     */
    private List<Collider> colliders;

    /**
     * Das korrespondierende GameElement.
     */
    final protected GameElementT gameElement;

    /**
     * Das korrespondierende BasePhysicsElement.
     */
    private BasePhysicsElement basePhysicsElement;

    /**
     * Gibt an, welche Collider aktuell mit dem Ball collidieren.
     */
    private HashMap<Integer, Boolean> colliding;

    /**
     * Erstellt eine Instanz von PhysicsElement mit dem zugehörigen GameElement.
     *
     * @param gameElement        Das zugehörige GameElement, welches von diesem PhysicsElement beeinflusst werden soll.
     * @param position           Die Position des PhysicsElements.
     * @param rotation           Die Rotation des PhysicsElement.
     * @param strengthMultiplier Der Multiplier für die Stärke der Collider.
     * @param basePhysicsElement Das korrespondierende BasePhysicsElement.
     */
    public PhysicsElement(GameElementT gameElement, Vector2 position, double rotation, double strengthMultiplier, BasePhysicsElement basePhysicsElement)
    {
        this.strengthMultiplier = strengthMultiplier;
        this.position = position;
        this.rotation = rotation;
        this.gameElement = gameElement;
        this.colliders = basePhysicsElement.getColliders();
        this.basePhysicsElement = basePhysicsElement;
        this.colliding = new HashMap<>();
        for (Collider collider : colliders)
        {
            colliding.put(collider.getId(), false);
        }
    }

    /**
     * Setzt die Position des PhysicsElements auf den übergebenen Wert.
     *
     * @param position Die neue Position des PhysicsElement
     */
    public void setPosition(Vector2 position)
    {
        if (this.position != position)
            changed = true;

        this.position = position;
    }

    /**
     * Setzt die Rotation.
     *
     * @param rotation Die Rotation des PhysicsElements zurück.
     */
    public void setRotation(double rotation)
    {
        if (this.rotation != rotation)
            changed = true;

        this.rotation = rotation;
    }

    /**
     * Gibt das korrespondierende BasePhysicsElement zurück.
     *
     * @return Das korrespondierende BasePhysicsElement
     */
    public BasePhysicsElement getBasePhysicsElement()
    {
        return basePhysicsElement;
    }

    /**
     * Prüft alle Kollider auf Kollisionen mit dem Ball und fügt bei Kollision Argumente zu den gegebenen Event-Argument-Listen hinzu.
     *
     * @param eventArgsList      Die Liste, welche alle auftretenden Kollisionen speichert.
     * @param ballPhysicsElement Der Ball, welcher mit den Kollidern kollidieren kann.
     *
     * @return Ob eine Kollision stattgefunden hat.
     */
    public boolean checkCollision(List<CollisionEventArgs<GameElementT>> eventArgsList, BallPhysicsElement<GameElementT> ballPhysicsElement)
    {
        boolean collided = false;
        for (Collider collider : colliders)
        {
            Optional<Double> depth = collider.checkCollision(ballPhysicsElement, this);
            if (depth.isPresent())
            {
                if (!colliding.get(collider.getId()))
                {
                    eventArgsList.add(new CollisionEventArgs<>(gameElement, collider.getId(), CollisionEventType.ENTERED, depth.get()));
                    colliding.put(collider.getId(), true);
                }
                else
                {
                    eventArgsList.add(new CollisionEventArgs<>(gameElement, collider.getId(), CollisionEventType.OVER, depth.get()));
                }
                collided = true;
            }
        }
        return collided;
    }

    /**
     * Setzt alle gespeicherten Kollisionen zurück und Fügt diese zur eventArgsList hinzu.
     *
     * @param eventArgsList Die Liste, welche alle auftretenden Kollisionen speichert.
     */
    public void ballLeaved(List<CollisionEventArgs<GameElementT>> eventArgsList)
    {
        for (Map.Entry<Integer, Boolean> integerBooleanEntry : colliding.entrySet())
        {
            if (integerBooleanEntry.getValue())
            {
                eventArgsList.add(new CollisionEventArgs<>(gameElement, integerBooleanEntry.getKey(), CollisionEventType.LEFT, 0));
            }
        }
        for (Collider collider : colliders)
        {
            colliding.put(collider.getId(), false);
        }
    }

    /**
     * Setzt das Feld welches angibt ob sich Position oder Rotation des physikalischen Elements geändert haben auf {@code false} zurück.
     */
    public void resetChanged()
    {
        changed = false;
    }

    /**
     * Gibt die Rotation des PhysicsElements zurück.
     *
     * @return Gibt die Rotation des PhysicsElements zurück.
     */
    public double getRotation()
    {
        return rotation;
    }

    /**
     * Gibt eine Liste der Collider des PhysicsElements zurück.
     *
     * @return Eine Liste der Collider des PhysicsElements.
     */
    protected List<Collider> getColliders()
    {
        return Collections.unmodifiableList(colliders);
    }

    /**
     * Gibt das zu diesem PhysicElement gehörende GameElement zurück.
     *
     * @return Das zu diesem PhysicElement gehörende GameElement.
     */
    public GameElementT getGameElement()
    {
        return gameElement;
    }

    /**
     * Gibt die Position des PhysicsElements zurück.
     *
     * @return Die Position des PhysicsElements.
     */
    public Vector2 getPosition()
    {
        return position;
    }

    /**
     * Gibt zurück ob sich Position oder Rotation des physikalischen Elements geändert haben.
     *
     * @return Ob sich Position oder Rotation des physikalischen Elements geändert haben.
     */
    public boolean hasChanged()
    {
        return changed;
    }

    /**
     * Gibt den Multiplier für die Stärke der Collider zurück.
     *
     * @return Den Multiplier für die Stärke der Collider.
     */
    public double getStrengthMultiplier()
    {
        return strengthMultiplier;
    }

    /**
     * Gibt den aktuellen Zustand des PhysicsElements zurück.
     *
     * @return Der aktuelle Zustand.
     */
    public ElementEventArgs<GameElementT> getStatus()
    {
        return new ElementEventArgs<>(gameElement, position, rotation, 0);
    }
}
