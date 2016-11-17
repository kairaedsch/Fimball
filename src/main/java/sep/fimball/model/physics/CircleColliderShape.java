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
     * @param position
     * @param radius
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
    public HitInfo calculateHitInfo(CircleColliderShape ball)
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