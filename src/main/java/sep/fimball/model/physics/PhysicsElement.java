package sep.fimball.model.physics;

import sep.fimball.general.data.Vector2;
import sep.fimball.model.element.GameElement;

/**
 * Repräsentiert ein GameElement in der Physikberechnung. Der PhysicsHandler arbeitet nicht direkt auf GameElement um gleichzeitigen Zugriff von der Zeichenschleife und der Physikschleife zu vermeiden
 */
public class PhysicsElement
{
    /**
     * Das zugehörige GameElement.
     */
    GameElement element;

    /**
     * Die aktuelle Position des PhysikElements.
     */
    Vector2 position;

    /**
     * Die aktuelle Rotation.
     */
    double rotation;

    /**
     * Erstellt eine Instanz von PhysicsElement mit dem zugehörigen GameElement.
     * @param element
     */
    public PhysicsElement(GameElement element)
    {
        this.element = element;
        this.position = element.getPosition();
        this.rotation = element.getRotation();
    }

    /**
     * Aktualisiert Position und Rotation des zugehörigen GameElements.
     */
    public void writeToGameElement()
    {
        element.addPhysicsUpdate(new PhysicsUpdateEventArgs(position, rotation));
    }
}
