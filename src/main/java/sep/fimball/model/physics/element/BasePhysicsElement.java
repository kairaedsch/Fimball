package sep.fimball.model.physics.element;

import javafx.scene.paint.Color;
import sep.fimball.general.data.RectangleDouble;
import sep.fimball.general.data.Vector2;
import sep.fimball.general.debug.Debug;
import sep.fimball.model.physics.collider.CircleColliderShape;
import sep.fimball.model.physics.collider.Collider;
import sep.fimball.model.physics.collider.ColliderShape;
import sep.fimball.model.physics.collider.HitInfo;

import java.util.ArrayList;
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

    public boolean checkIfPointIsInElement(double rotation, Vector2 pointToSearch, Vector2 placedElementPosition)
    {
        for (Collider collider : colliders)
        {
            for (ColliderShape shape : collider.getShapes())
            {
                HitInfo hit = shape.calculateHitInfo(CircleColliderShape.generateSelectionCollider(), pointToSearch, placedElementPosition, rotation, pivotPoint);

                if (hit.isHit())
                {
                    return true;
                }
            }
        }
        return false;
    }

    public Vector2 getExtremePos(double rotation, boolean max)
    {
        List<ColliderShape> shapes = new ArrayList<>();

        for (Collider collider : colliders)
        {
            shapes.addAll(collider.getShapes());
        }
        return Vector2.getExtremeVector(shapes, max, (shape -> shape.getExtremePos(rotation, pivotPoint, max)));
    }
}
