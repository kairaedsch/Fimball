package sep.fimball.model;

import com.sun.javafx.geom.Rectangle;

/**
 * Created by alexcekay on 04.11.16.
 */
public class RectangleDouble
{
    private Vector2 origin;
    private double width;
    private double height;

    public RectangleDouble(Vector2 origin, double width, double height)
    {
        this.origin = origin;
        this.width = width;
        this.height = height;
    }

    public Vector2 getOrigin()
    {
        return origin;
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
