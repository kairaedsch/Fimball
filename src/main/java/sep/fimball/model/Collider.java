package sep.fimball.model;

public abstract class Collider
{
    private WorldLayer layer;
    private PhysicsForce force;

    public Collider(WorldLayer layer, ForceType colliderType)
    {
        this.layer = layer;
        this.colliderType = colliderType;
    }

    public WorldLayer getLayer()
    {
        return layer;
    }

    public ForceType getColliderType()
    {
        return colliderType;
    }
}