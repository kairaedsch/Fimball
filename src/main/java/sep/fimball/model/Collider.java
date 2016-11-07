package sep.fimball.model;

public abstract class Collider
{
    private WorldLayer layer;
    private PhysicsForce force;
    private Animation animation;

    public Collider(WorldLayer layer, PhysicsForce force, Animation animation)
    {
        this.layer = layer;
        this.force = force;
        this.animation = animation;
    }

    public void updateAnimation()
    {
        animation.update();
    }

    public WorldLayer getLayer()
    {
        return layer;
    }

    public PhysicsForce getForce()
    {
        return force;
    }

    public Animation getAnimation()
    {
        return animation;
    }
}