package sep.fimball.model;

public class CircleCollider extends Collider
{
    private Vector2 position;
    private double radius;

    public CircleCollider(Vector2 position, double radius)
    {
        this.position = position;
        this.radius = radius;
    }

    public Vector2 getPosition()
    {
        return position;
    }

    public double getRadius()
    {
        return radius;
    }
}