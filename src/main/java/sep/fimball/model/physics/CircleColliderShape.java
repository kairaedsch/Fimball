package sep.fimball.model.physics;

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
     * Erstellt einen neuen Collider in Kreisform.
     * @param position Mittelpunkt des Kreises, der den Collider formt.
     * @param radius Radius des Kreises, der den Collider formt.
     */
    public CircleColliderShape(Vector2 position, double radius)
    {
        this.position = position;
        this.radius = radius;
    }

    /**
     * Gibt den Mittelpunkt des Kreises, der den Collider formt, zurück.
     * @return Der Mittelpunkt des Kreises, der den Collider formt.
     */
    public Vector2 getPosition()
    {
        return position;
    }


    /**
     * Gibt den Radius des Kreises, der den Collider formt, zurück.
     * @return Der Radius des Kreises, der den Collider formt.
     */
    public double getRadius()
    {
        return radius;
    }

    @Override
    public HitInfo calculateHitInfo(BallElement ball, Vector2 colliderObjectPosition)
    {
        // Collision check between two circles
        Vector2 globalColliderPosition = Vector2.add(position, colliderObjectPosition);
        Vector2 ballGlobalColliderPosition = Vector2.add(ball.getPosition(), ball.getCollider().getPosition());
        Vector2 distance = Vector2.sub(ballGlobalColliderPosition, globalColliderPosition);

        if (distance.magnitude() < ball.getCollider().getRadius() + radius)
        {
            double overlapDistance = (ball.getCollider().getRadius() + radius) - distance.magnitude();
            return new HitInfo(true, Vector2.scale(distance.normalized(), overlapDistance));
        }

        return new HitInfo(false, null);
    }
}