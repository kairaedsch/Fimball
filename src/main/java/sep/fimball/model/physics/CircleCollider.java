package sep.fimball.model.physics;

import sep.fimball.general.data.Vector2;
import sep.fimball.model.Animation;

/**
 * Implementierung eines Colliders, der die Form eines Kreises hat.
 */
public class CircleCollider extends Collider
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
     * @param layer
     * @param force
     * @param animation
     */
    public CircleCollider(Vector2 position, double radius, WorldLayer layer, PhysicsForce force, Animation animation)
    {
        super(layer, force, animation);
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
}