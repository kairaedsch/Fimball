package sep.fimball.model;

import sep.fimball.general.data.Vector2;

/**
 * RectangleDouble stellt ein Rechteck dar, wobei seine Daten mit Double-Werten gespeichert werden.
 */
public class RectangleDouble
{
    /**
     * Die Position der linken oberen Ecke des Rechtecks.
     */
    private Vector2 origin;

    /**
     * Die Breite des Rechtecks.
     */
    private double width;

    /**
     * Die Höhe des Rechtecks.
     */
    private double height;

    /**
     * Erzeugt ein RectangleDouble mit den gegebenen Werten.
     * @param origin
     * @param width
     * @param height
     */
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
