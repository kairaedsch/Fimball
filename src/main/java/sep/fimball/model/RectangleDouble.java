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
     * @param origin Linke obere Ecke des Rechtecks.
     * @param width Breite des Rechtecks.
     * @param height Höhe des Rechtecks.
     */
    public RectangleDouble(Vector2 origin, double width, double height)
    {
        this.origin = origin;
        this.width = width;
        this.height = height;
    }

    /**
     * Gibt die Position der linken oberen Ecke des Rechtecks zurück.
     * @return Die Position der linken oberen Ecke des Rechtecks.
     */
    public Vector2 getOrigin()
    {
        return origin;
    }

    /**
     * Gibt die Länge des Rechtecks zurück.
     * @return Die Länge des Rechtecks.
     */
    public double getWidth()
    {
        return width;
    }

    /**
     * Gibt die Höhe des Rechtecks zurück.
     * @return Die Höhe des Rechtecks.
     */
    public double getHeight()
    {
        return height;
    }
}
