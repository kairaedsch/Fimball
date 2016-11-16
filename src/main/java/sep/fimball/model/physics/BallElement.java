package sep.fimball.model.physics;

import sep.fimball.general.data.Vector2;
import sep.fimball.model.element.GameElement;

public class BallElement
{
    private PhysicsElement subElement;
    private Vector2 velocity;
    private double angularVelocity;
    private CircleColliderShape collider;
    private WorldLayer layer;

    public BallElement(GameElement gameElement, CircleColliderShape collider, WorldLayer layer)
    {
        subElement = new PhysicsElement(gameElement);

        this.collider = collider;
        this.velocity = new Vector2();
        this.angularVelocity = 0.0;
        this.layer = layer;
    }

    public PhysicsElement getSubElement()
    {
        return subElement;
    }

    public Vector2 getPosition()
    {
        return subElement.getPosition();
    }

    public void setPosition(Vector2 position)
    {
        subElement.setPosition(position);
    }

    public void setRotation(double rotation)
    {
        subElement.setRotation(rotation);
    }

    public Vector2 getVelocity()
    {
        return velocity;
    }

    public void setVelocity(Vector2 velocity)
    {
        this.velocity = velocity;
    }

    public double getAngularVelocity()
    {
        return angularVelocity;
    }

    public void setAngularVelocity(double angularVelocity)
    {
        this.angularVelocity = angularVelocity;
    }

    public CircleColliderShape getCollider()
    {
        return collider;
    }

    public WorldLayer getLayer()
    {
        return layer;
    }

    public void setLayer(WorldLayer layer)
    {
        this.layer = layer;
    }
}
