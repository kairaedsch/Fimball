package sep.fimball.model.physics.element;

import sep.fimball.general.data.RectangleDouble;
import sep.fimball.general.data.Vector2;
import sep.fimball.model.physics.collider.Collider;
import sep.fimball.model.physics.collider.ColliderShape;

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
     *
     * @param pivotPoint Die Position des Pivot-Punktes des Elements.
     * @param colliders  Die Collider, die dieses Element hat.
     */
    public BasePhysicsElement(Vector2 pivotPoint, List<Collider> colliders)
    {
        this.pivotPoint = pivotPoint;
        this.colliders = colliders;
    }

    /**
     * Gibt die Position des Pivot-Punktes des Elements zurück.
     *
     * @return Die Position des Pivot-Punktes des Elements.
     */
    public Vector2 getPivotPoint()
    {
        return pivotPoint;
    }

    /**
     * Gibt die Liste der Collider, die dieses Element hat, zurück.
     *
     * @return Eine Liste von Collidern, die dieses Element hat.
     */
    public List<Collider> getColliders()
    {
        return colliders;
    }

    public boolean checkIfPointIsInElement(double rotation, Vector2 point)
    {
        for (Collider collider : colliders)
        {
            for (ColliderShape shape : collider.getShapes())
            {
                RectangleDouble boundingBox = shape.getBoundingBox(rotation, pivotPoint);
                double minX = boundingBox.getOrigin().getX();
                double minY = boundingBox.getOrigin().getY();
                double maxX = minX + boundingBox.getWidth();
                double maxY = minY + boundingBox.getHeight();

                if (point.getX() >= minX && point.getX() <= maxX && point.getY() >= minY && point.getY() <= maxY)
                {
                    return true;
                }
            }
        }
        return false;
    }
}
