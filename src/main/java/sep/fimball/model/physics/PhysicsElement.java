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
     * Das zugehörige GameElement.
     */
    private GameElement element;

    /**
     * Die aktuelle Position des PhysikElements.
     */
    private Vector2 position;

    /**
     * Die aktuelle Rotation.
     */
    private double rotation;

    private List<Collider> colliders;

    /**
     * Erstellt eine Instanz von PhysicsElement mit dem zugehörigen GameElement.
     * @param element
     */
    public PhysicsElement(GameElement element)
    {
        this.element = element;
        this.position = element.positionProperty().get();
        this.rotation = element.rotationProperty().get();
        this.colliders = element.getPlacedElement().getBaseElement().getPhysics().getColliders();
    }

    /**
     * Aktualisiert Position und Rotation des zugehörigen GameElements.
     */
    public void writeToGameElement()
    {
        element.setPosition(position);
        element.setRotation(rotation);
    }

    /**
     * Gibt das zu diesem PhysicElement gehörende GameElement zurück.
     * @return Das zu diesem PhysicElement gehörende GameElement.
     */
    public GameElement getElement()
    {
        return element;
    }

    /**
     * Gibt die Position des PhysicsElements zurück.
     * @return Die Position des PhysiscsElements.
     */
    public Vector2 getPosition()
    {
        return position;
    }

    /**
     * Setzt die Position des PhysicsElements auf den übergebenen Wert.
     * @param position Die neue Position des PhysicsElement
     */
    public void setPosition(Vector2 position)
    {
        this.position = position;
    }

    /**
     * Gibt die Rotation des PhysicsElements zurück.
     * @return Gibt die Rotation des PhysicsElements zurück.
     */
    public double getRotation()
    {
        return rotation;
    }

    /**
     * Setzt die Rotation.
     * @param rotation Die Rotation des PhysicsElements zurück.
     */
    public void setRotation(double rotation)
    {
        this.rotation = rotation;
    }

    /**
     * Gibt eine Liste der Collider des PhysicsElements zurück.
     * @return Eine Liste der Collider des PhysicsElements.
     */
    public List<Collider> getColliders()
    {
        return colliders;
    }
}
