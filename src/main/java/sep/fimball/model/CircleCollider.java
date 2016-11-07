package sep.fimball.model;

import sep.fimball.general.data.Vector2;

public class CircleCollider extends Collider
{
    private Vector2 position;
    private double radius;

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