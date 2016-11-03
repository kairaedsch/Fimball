package sep.fimball.model;

public abstract class Collider
{
	private WorldLayer layer;
    private ColliderType colliderType;

    public Collider(WorldLayer layer, ColliderType colliderType)
    {
        this.layer = layer;
        this.colliderType = colliderType;
    }

	public WorldLayer getLayer()
    {
        return layer;
    }

    public ColliderType getColliderType()
    {
        return colliderType;
    }
}