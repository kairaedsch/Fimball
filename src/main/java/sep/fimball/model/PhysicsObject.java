package sep.fimball.model;

import java.util.LinkedList;

public class PhysicsObject extends WorldObject
{
    private Collider[] colliders;
    private Vector2 velocity;
    private float angularVelocity;
    private LinkedList<CollisionEventArgs> collisionEvents;

    public PhysicsObject(Vector2 position, float rotation, Vector2 velocity, float angularVelocity)
    {
        super(position, rotation);
        this.velocity = velocity;
        this.angularVelocity = angularVelocity;
        collisionEvents = new LinkedList<>();
    }

    @Override public void update()
    {
        super.update();
        for (CollisionEventArgs event : collisionEvents)
            onCollision(event);
    }

    /**
     * Gets called by the physics thread to add collision events for later execution
     * @param collision Event information about the physics interaction
     */
    public void addCollisionEvent(CollisionEventArgs collision)
    {
        collisionEvents.add(collision);
    }

    protected void onCollision(CollisionEventArgs collision)
    {
        throw new UnsupportedOperationException();
    }
}