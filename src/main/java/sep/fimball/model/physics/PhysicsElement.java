package sep.fimball.model.physics;

import sep.fimball.general.data.Vector2;
import sep.fimball.model.element.GameElement;

import java.util.List;

/**
 * Repräsentiert ein GameElement in der Physikberechnung. Der PhysicsHandler arbeitet nicht direkt auf GameElement um gleichzeitigen Zugriff von der Zeichenschleife und der Physikschleife zu vermeiden
 */
public class PhysicsElement
{
    /**
     * Die aktuelle Position des PhysikElements.
     */
    private Vector2 position;

    /**
     * Die aktuelle Rotation.
     */
    private double rotation;

    /**
     * Die Liste der Collider, welche die Kollisionsflächen dieses Elements darstellen.
     */
    private List<Collider> colliders;
    private GameElement gameElement;

    /**
     * Erstellt eine Instanz von PhysicsElement mit dem zugehörigen GameElement.
     *
     * @param gameElement Das zugehörige GameElement, welches von diesem PhysicsElement bewegt werden soll.
     */
    public PhysicsElement(GameElement gameElement)
    {
        this.position = gameElement.positionProperty().get();
        this.rotation = gameElement.rotationProperty().get();
        this.colliders = gameElement.getPlacedElement().getBaseElement().getPhysics().getColliders();
        this.gameElement = gameElement;
    }

    /**
     * Gibt das zu diesem PhysicElement gehörende GameElement zurück.
     *
     * @return Das zu diesem PhysicElement gehörende GameElement.
     */
    public GameElement getGameElement()
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
     * Setzt die Position des PhysicsElements auf den übergebenen Wert.
     *
     * @param position Die neue Position des PhysicsElement
     */
    public void setPosition(Vector2 position)
    {
        this.position = position;
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
     * Setzt die Rotation.
     *
     * @param rotation Die Rotation des PhysicsElements zurück.
     */
    public void setRotation(double rotation)
    {
        this.rotation = rotation;
    }

    /**
     * Gibt eine Liste der Collider des PhysicsElements zurück.
     *
     * @return Eine Liste der Collider des PhysicsElements.
     */
    public List<Collider> getColliders()
    {
        return colliders;
    }
}
