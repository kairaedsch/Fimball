package sep.fimball.model.physics;

import sep.fimball.general.data.Vector2;

public class BallElement
{
    private Vector2 position;
    private float rotation;
    private Vector2 velocity;
    private float angularVelocity;
    private CircleCollider collider;
    private WorldLayer layer;

    public BallElement(Vector2 velocity, float angularVelocity, CircleCollider collider, WorldLayer layer)
    {
        this.velocity = velocity;
        this.angularVelocity = angularVelocity;
        this.collider = collider;
        this.layer = layer;
    }

    public Vector2 getPosition()
    {
        return position;
    }

    public void setPosition(Vector2 position)
    {
        this.position = position;
    }

    public float getRotation()
    {
        return rotation;
    }

    public void setRotation(float rotation)
    {
        this.rotation = rotation;
    }

    public Vector2 getVelocity()
    {
        return velocity;
    }

    public void setVelocity(Vector2 velocity)
    {
        this.velocity = velocity;
    }

    public float getAngularVelocity()
    {
        return angularVelocity;
    }

    public void setAngularVelocity(float angularVelocity)
    {
        this.angularVelocity = angularVelocity;
    }

    public CircleCollider getCollider()
    {
        return collider;
    }

    public WorldLayer getLayer()
    {
        return layer;
    }
}
