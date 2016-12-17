package sep.fimball.model.physics.collider;

import sep.fimball.general.data.Vector2;

/**
 * Beschreibt die Form einer Fläche die mit dem Ball interagieren kann.
 */
public interface ColliderShape
{
    /**
     * Überprüft ob ein gegebener CircleCollider sich mit dieser ColliderShape überschneidet.
     *
     * @param otherColliderShape      Die andere CircleColliderShape mit der auf Kollision überprüft werden soll.
     * @param otherColliderPosition   Die Position der anderen CircleColliderShape in der Spielwelt.
     * @param currentColliderPosition Die Position dieser ColliderShape in der Spielwelt.
     * @param rotation                Die Rotation des Spielelements welches diese ColliderShape hat.
     * @param pivotPoint              Der Pivot Punkt des Spielelements welches diese ColliderShape hat.
     * @return Gibt Informationen über die eventuelle Kollision zwischen den ColliderShapes zurück.
     */
    HitInfo calculateHitInfo(CircleColliderShape otherColliderShape, Vector2 otherColliderPosition, Vector2 currentColliderPosition, double rotation, Vector2 pivotPoint);

    /**
     * Gibt den Vektor mit minimaler/maximaler X und Y Position dieser Form zurück je nachdem ob {@code max} true oder false ist.
     *
     * @param rotation   Die Rotation des Colliders.
     * @param pivotPoint Der Drehpunkt des Colliders.
     * @param max        Gibt an ob die maximale oder minimale Position gesucht ist.
     * @return Die Maximale Position dieses Colliders bei der gegebenen Rotation.
     */
    Vector2 getExtremePos(double rotation, Vector2 pivotPoint, boolean max);
}
