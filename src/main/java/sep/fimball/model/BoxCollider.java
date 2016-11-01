package sep.fimball.model;

public class BoxCollider extends Collider
{
    private Vector2[] points;

    public BoxCollider(Vector2[] points)
    {
        if (points == null || points.length != 4)
            throw new IllegalArgumentException("Only 4 points in a box!");

        this.points = points;
    }

    public Vector2[] getPoints()
    {
        return points.clone(); // TODO do we have to do this immutable bs here?
    }
}