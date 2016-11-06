package sep.fimball.general.data;

import java.awt.*;

public class Vector2
{
    private double x;
    private double y;

    public Vector2()
    {
        x = 0.0;
        y = 0.0;
    }

    public Vector2(double x, double y)
    {
        this.x = x;
        this.y = y;
    }

    public Vector2(Point p)
    {
        this.x = p.x;
        this.y = p.y;
    }

    public double getX()
    {
        return x;
    }

    public void setX(double x)
    {
        this.x = x;
    }

    public double getY()
    {
        return y;
    }

    public void setY(double y)
    {
        this.y = y;
    }
}
