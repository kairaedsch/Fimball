package sep.fimball.model;

public class Transform
{
    private Vector2 position;
    private double rotation;

    public Transform(Vector2 position, double rotation)
    {
        this.position = position;
        this.rotation = rotation;
    }

    public Vector2 getPosition()
    {
        return position;
    }

    public void setPosition(Vector2 position)
    {
        this.position = position;
    }

    public double getRotation()
    {
        return rotation;
    }

    public void setRotation(double rotation)
    {
        this.rotation = rotation;
    }
}