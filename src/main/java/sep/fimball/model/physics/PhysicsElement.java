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
        this.position = element.getPosition();
        this.rotation = element.getRotation();
        this.colliders = element.getPlacedElement().getBaseElement().getPhysics().getColliders();
    }

    /**
     * Aktualisiert Position und Rotation des zugehörigen GameElements.
     */
    public void writeToGameElement()
    {
        element.addPhysicsUpdate(new PhysicsUpdateEventArgs(position, rotation));
    }

    public GameElement getElement()
    {
        return element;
    }

    public Vector2 getPosition()
    {
        return position;
    }

    public void setPosition(Vector2 position)
    {
        this.position = position;
    }

    public void setRotation(double rotation)
    {
        this.rotation = rotation;
    }

    public List<Collider> getColliders()
    {
        return colliders;
    }
}
