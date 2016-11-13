package sep.fimball.general.data;

import java.awt.*;

/**
 * Vector2 stellt einen Vektor mit zwei Komponenten dar.
 */
public class Vector2
{
    /**
     * X Komponente des Vektors.
     */
    private double x;

    /**
     * Y Komponente des Vektors.
     */
    private double y;

    /**
     * Konstruiert einen Vektor bei dem sowohl die X als auch Y Komponente 0 sind.
     */
    public Vector2()
    {
        x = 0.0;
        y = 0.0;
    }

    /**
     * Konstruiert einen Vektor mit den übergebenen Parameter.
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

    public void add(Vector2 otherVec)
    {
        this.x += otherVec.getX();
        this.y += otherVec.getY();
    }

    public void sub(Vector2 otherVec)
    {
        this.x -= otherVec.getX();
        this.y -= otherVec.getY();
    }

    public void scale(double scalar)
    {
        this.x *= scalar;
        this.y *= scalar;
    }

    public double magnitude()
    {
        return Math.sqrt(x * x + y * y);
    }

    public double dot(Vector2 other)
    {
        return (this.x * other.getX()) + (this.y * other.getY());
    }

    public void normalize()
    {
        double norm = magnitude();
        this.x /= norm;
        this.y /= norm;
    }

    public static Vector2 add(Vector2 vecOne, Vector2 vecTwo)
    {
        return new Vector2(vecOne.getX() + vecTwo.getX(), vecOne.getY() + vecTwo.getY());
    }

    public static Vector2 sub(Vector2 vecOne, Vector2 vecTwo)
    {
        return new Vector2(vecOne.getX() - vecTwo.getX(), vecOne.getY() - vecTwo.getY());
    }

    public static Vector2 scale(Vector2 vecOne, double scalar)
    {
        return new Vector2(vecOne.getX() * scalar, vecOne.getY() * scalar);
    }

    public static double dot(Vector2 vecOne, Vector2 vecTwo)
    {
        return (vecOne.getX() * vecTwo.getX()) + (vecOne.getY() * vecTwo.getY());
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
