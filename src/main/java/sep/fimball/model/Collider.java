package sep.fimball.model;

public abstract class Collider
{
    private WorldLayer layer;
    private PhysicsForce force;

    public Collider(WorldLayer layer, PhysicsForce force)
    {
        this.layer = layer;
        this.force = force;
    }

    public WorldLayer getLayer()
    {
        return layer;
    }

    public PhysicsForce getColliderType()
    {
        return force;
    }
}