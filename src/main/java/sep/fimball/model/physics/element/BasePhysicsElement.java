package sep.fimball.model.physics.element;

import sep.fimball.general.data.Vector2;
import sep.fimball.model.physics.collider.Collider;

import java.util.List;

/**
 * Diese Klasse enthält alle Informationen zu den Physik-Eigenschaften eines BaseElements.
 */
public class BasePhysicsElement
{
    /**
     * Die Position des Pivot-Punktes des Elements.
     */
    private Vector2 pivotPoint;

    /**
     * Die Collider, die dieses Element hat.
     */
    private List<Collider> colliders;

    /**
     * Erstellt ein neues BasePhysicsElement.
     * @param pivotPoint Die Position des Pivot-Punktes des Elements.
     * @param colliders Die Collider, die dieses Element hat.
     */
    public BasePhysicsElement(Vector2 pivotPoint, List<Collider> colliders)
    {
        this.pivotPoint = pivotPoint;
        this.colliders = colliders;
    }

    /**
     * Gibt die Position des Pivot-Punktes des Elements zurück.
     * @return Die Position des Pivot-Punktes des Elements.
     */
    public Vector2 getPivotPoint()
    {
        return pivotPoint;
    }

    /**
     * Gibt die Liste der Collider, die dieses Element hat, zurück.
     * @return Eine Liste von Collidern, die dieses Element hat.
     */
    public List<Collider> getColliders()
    {
        return colliders;
    }
}
