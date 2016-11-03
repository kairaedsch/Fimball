package sep.fimball.model;

public class BoxCollider extends Collider
{
    private Vector2 position;
    private double width;
    private double height;
    private double rotation;

    public BoxCollider(Vector2 position, double width, double height, WorldLayer worldLayer, ColliderType colliderType)
    {
        super(worldLayer, colliderType);
        if (position == null)
            throw new IllegalArgumentException("No position given");

        this.position = position;
        this.width = width;
        this.height = height;
        this.rotation = 0;
    }

    public Vector2 getPosition()
    {
        return position;
    }

    public double getWidth()
    {
        return width;
    }

    public double getHeight()
    {
        return height;
    }

    public double getRotation()
    {
        return rotation;
    }

    public void setPosition(double rotation)
    {
        this.rotation = rotation;
    }
}