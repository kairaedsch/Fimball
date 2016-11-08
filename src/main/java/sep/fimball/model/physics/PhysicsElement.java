package sep.fimball.model.physics;

import sep.fimball.general.data.Vector2;
import sep.fimball.model.element.GameElement;

/**
 * Repräsentiert ein GameElement in der Physikberechnung.
 */
public class PhysicsElement
{
    GameElement element;
    Vector2 position;
    double rotation;

    public PhysicsElement(GameElement element)
    {
        this.element = element;
        this.position = element.getPosition();
        this.rotation = element.getRotation();
    }

    public void writeToGameElement()
    {
        element.addPhysicsUpdate(new PhysicsUpdateEventArgs(position, rotation));
    }
}
