package sep.fimball.model.physics;

import sep.fimball.general.data.Vector2;
import sep.fimball.model.Animation;

/**
 * Implementierung eines Colliders, der die Form eines Kreises hat.
 */
public class CircleCollider implements CollisionShape
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
     * @param position
     * @param radius
     */
    public CircleCollider(Vector2 position, double radius)
    {
        this.position = position;
        this.radius = radius;
    }

    public Vector2 getPosition()
    {
        return position;
    }

    public double getRadius()
    {
        return radius;
    }

    @Override
    public HitInfo calculateHitInfo(CircleCollider ball)
    {
        // Collision check between two circles
        Vector2 distance = Vector2.sub(ball.getPosition(), position);
        if (distance.magnitude() < ball.getRadius() + radius)
        {
            double overlapDistance = (ball.getRadius() + ball.getRadius()) - distance.magnitude();
            return new HitInfo(true, Vector2.scale(distance.normalized(), overlapDistance));
        }

        return new HitInfo(false, null);
    }
}