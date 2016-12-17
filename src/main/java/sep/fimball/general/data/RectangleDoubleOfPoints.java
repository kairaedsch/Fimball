package sep.fimball.general.data;

/**
 * RectangleDouble stellt ein Rechteck dar, wobei seine Daten mit Double-Werten gespeichert werden.
 */
public class RectangleDoubleOfPoints
{
    private final Vector2 pointA;

    private final Vector2 pointB;

    public RectangleDoubleOfPoints(Vector2 pointA, Vector2 pointB)
    {
        this.pointA = pointA;
        this.pointB = pointB;
    }

    /**
     * Erzeugt ein RectangleDouble.
     */
    public RectangleDouble toRectangleDouble()
    {
        return new RectangleDouble(getOrigin(), getSize());
    }

    public Vector2 getPointA()
    {
        return pointA;
    }

    public Vector2 getPointB()
    {
        return pointB;
    }

    public Vector2 getOrigin()
    {
        return Vector2.min(pointA, pointB);
    }

    public Vector2 getEnd()
    {
        return Vector2.max(pointA, pointB);
    }

    public Vector2 getSize()
    {
        return getEnd().minus(getOrigin());
    }

    public double getWidth()
    {
        return getSize().getX();
    }

    public double getHeight()
    {
        return getSize().getY();
    }
}
