package sep.fimball.model.physics.element;

import sep.fimball.general.data.Vector2;
import sep.fimball.model.physics.collider.CircleColliderShape;
import sep.fimball.model.physics.collider.Collider;
import sep.fimball.model.physics.collider.ColliderShape;
import sep.fimball.model.physics.collider.HitInfo;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

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
        return Collections.unmodifiableList(colliders);
    }

    /**
     * Überprüft, ob das PhysicsElement eine Shape an der gegebenen Position hat.
     *
     * @param rotation      Die rotation des PhysicsElements.
     * @param pointToSearch Die gegebene Position, wo eine Shape sein soll.
     * @return Ob eine Shape an der gegebenen Position ist.
     */
    public boolean checkIfPointIsInElement(double rotation, Vector2 pointToSearch)
    {
        for (Collider collider : colliders)
        {
            for (ColliderShape shape : collider.getShapes())
            {
                HitInfo hit = shape.calculateHitInfo(CircleColliderShape.generateSelectionCollider(), pointToSearch, new Vector2(), rotation, pivotPoint);

                if (hit.isHit())
                {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Gibt den Vektor mit minimaler/maximaler X und Y Position dieses PhysicsElements zurück, je nachdem ob {@code maxComponents} true oder false ist.
     *
     * @param rotation Die rotation des PhysicsElements.
     * @param max      Gibt an ob die maximale oder minimale Position gesucht ist.
     * @return Die Maximale Position dieses PhysicsElements bei der gegebenen Rotation.
     */
    public Vector2 getExtremePos(double rotation, boolean max)
    {
        Optional<Vector2> result = colliders
                .stream()
                .map(collider -> collider.getShapes()
                        .stream()
                        .map(shape -> shape.getExtremePos(rotation, pivotPoint, max))
                        .reduce(max ? Vector2::maxComponents : Vector2::minComponents))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .reduce(max ? Vector2::maxComponents : Vector2::minComponents);

        if (result.isPresent())
            return result.get();
        else
            throw new IllegalStateException("Received invalid shapes");
    }
}
