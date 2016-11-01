package sep.fimball.model;

public class PhysicsObject extends WorldObject
{
    private Collider[] colliders;
    private Vector2 velocity;
    private float angularVelocity;

    public PhysicsObject(Vector2 position, float rotation, Vector2 velocity, float angularVelocity)
    {
        super(position, rotation);
        this.velocity = velocity;
        this.angularVelocity = angularVelocity;
    }

    public void onCollision(CollisionEventArgs collision)
    {
        throw new UnsupportedOperationException();
    }
}