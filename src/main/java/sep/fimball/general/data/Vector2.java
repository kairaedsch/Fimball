package sep.fimball.general.data;

import java.awt.*;

/**
 * Vector2 stellt einen Vektor mit zwei Komponenten dar
 */
public class Vector2
{
    /**
     * X Komponente des Vektors
     */
    private double x;

    /**
     * Y Komponente des Vektors
     */
    private double y;

    /**
     * Konstruiert einen Vektor bei dem sowohl die X als auch Y Komponente 0 sind
     */
    public Vector2()
    {
        x = 0.0;
        y = 0.0;
    }

    /**
     * Konstruiert einen Vektor mit den übergebenen Parameter
     * @param x
     * @param y
     */
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
