package sep.fimball.model.physics.collider;

import sep.fimball.general.data.Vector2;

/**
 * Implementierung eines Colliders, der die Form eines Kreises hat.
 */
public class CircleColliderShape implements ColliderShape
{
    /**
     * Mittelpunkt des Kreises, der den Collider formt.
     */
    private Vector2 position;

    /**
     * Radius des Kreises, der den Collider formt.
     */
    private double radius;

    /**
     * Erstellt einen neuen Collider in der Form eines Kreises.
     *
     * @param position Mittelpunkt des Kreises, der den Collider formt.
     * @param radius   Radius des Kreises, der den Collider formt.
     */
    public CircleColliderShape(Vector2 position, double radius)
    {
        this.position = position;
        this.radius = radius;
    }

    /**
     * Gibt den Mittelpunkt des Kreises, der den Collider formt, zurück.
     *
     * @return Der Mittelpunkt des Kreises, der den Collider formt.
     */
    public Vector2 getPosition()
    {
        return position;
    }


    /**
     * Gibt den Radius des Kreises, der den Collider formt, zurück.
     *
     * @return Der Radius des Kreises, der den Collider formt.
     */
    public double getRadius()
    {
        return radius;
    }

    @Override
    public HitInfo calculateHitInfo(CircleColliderShape otherColliderShape, Vector2 otherColliderPosition, Vector2 currentColliderPosition, double rotation, Vector2 pivotPoint)
    {
        // Collision check between two circles
        Vector2 globalColliderPosition = position.plus(currentColliderPosition).rotate(Math.toRadians(rotation), pivotPoint.plus(currentColliderPosition));
        Vector2 ballGlobalColliderPosition = otherColliderPosition.plus(otherColliderShape.getPosition());
        Vector2 distance = ballGlobalColliderPosition.minus(globalColliderPosition);

        if (distance.magnitude() < otherColliderShape.getRadius() + radius)
        {
            double overlapDistance = (otherColliderShape.getRadius() + radius) - distance.magnitude();
            return new HitInfo(true, distance.normalized().scale(overlapDistance));
        }

        return new HitInfo(false, null);
    }

    @Override
    public Vector2 getExtremePos(double rotation, Vector2 pivotPoint, boolean max)
    {
        if (max)
        {
            return position.rotate(Math.toRadians(-rotation), pivotPoint).plus(new Vector2(radius, radius));
        }
        else
        {
            return position.rotate(Math.toRadians(-rotation), pivotPoint).minus(new Vector2(radius, radius));
        }
    }

    /**
     * Gibt eine CircleColliderShape mit sehr geringer Größe zurück welche zur Selektion der Elemente im Editor genutzt wird.
     *
     * @return Eine CircleColliderShape mit sehr geringer Größe.
     */
    public static CircleColliderShape generateSelectionCollider()
    {
        return new CircleColliderShape(new Vector2(0.1, 0.1), 0.1);
    }
}