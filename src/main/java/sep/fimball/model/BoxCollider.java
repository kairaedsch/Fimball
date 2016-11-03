package sep.fimball.model;

public class BoxCollider extends Collider
{
    private Vector2 position;
    private double width;
    private double height;

    public BoxCollider(Vector2 position, double width, double height)
    {
        if (position == null)
            throw new IllegalArgumentException("No position given");

        this.position = position;
        this.width = width;
        this.height = height;
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
}