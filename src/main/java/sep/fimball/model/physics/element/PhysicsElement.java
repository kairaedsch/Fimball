package sep.fimball.model.physics.element;

import sep.fimball.general.data.Vector2;
import sep.fimball.model.physics.collider.Collider;
import sep.fimball.model.physics.game.CollisionEventArgs;
import sep.fimball.model.physics.game.ElementEventArgs;

import java.util.Collections;
import java.util.List;

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
    }

    /**
     * Setzt die Position des PhysicsElements auf den übergebenen Wert.
     *
     * @param position Die neue Position des PhysicsElement
     */
    public void setPosition(Vector2 position)
    {
        this.position = position;
        changed = true;
    }

    /**
     * Setzt die Rotation.
     *
     * @param rotation Die Rotation des PhysicsElements zurück.
     */
    public void setRotation(double rotation)
    {
        this.rotation = rotation;
        changed = true;
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
     */
    public void checkCollision(List<CollisionEventArgs<GameElementT>> eventArgsList, BallPhysicsElement<GameElementT> ballPhysicsElement)
    {
        for (Collider collider : colliders)
        {
            if (collider.checkCollision(ballPhysicsElement, this))
            {
                eventArgsList.add(new CollisionEventArgs<>(gameElement, collider.getId()));
            }
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
     * @return
     */
    public ElementEventArgs<GameElementT> getChange()
    {
        return new ElementEventArgs<>(gameElement, position, rotation, 0);
    }
}
