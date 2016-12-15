package sep.fimball.general.data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * RectangleDouble stellt ein Rechteck dar, wobei seine Daten mit Double-Werten gespeichert werden.
 */
public class RectangleDouble
{
    /**
     * Die Position der linken oberen Ecke des Rechtecks.
     */
    private final Vector2 origin;

    /**
     * Die Breite des Rechtecks.
     */
    private final Vector2 size;

    /**
     * Erzeugt ein RectangleDouble mit den gegebenen Werten.
     *
     * @param origin Linke obere Ecke des Rechtecks.
     * @param size  Größe des Rechtecks.
     */
    public RectangleDouble(Vector2 origin, Vector2 size)
    {
        this.origin = origin;
        this.size = size;
    }

    /**
     * Erzeugt ein RectangleDouble mit den gegebenen Werten.
     *
     * @param origin Linke obere Ecke des Rechtecks.
     * @param width  Breite des Rechtecks.
     * @param height Höhe des Rechtecks.
     */
    public RectangleDouble(Vector2 origin, double width, double height)
    {
        this(origin, new Vector2(width, height));
    }

    /**
     * Gibt die Position der linken oberen Ecke des Rechtecks zurück.
     *
     * @return Die Position der linken oberen Ecke des Rechtecks.
     */
    public Vector2 getOrigin()
    {
        return origin;
    }

    /**
     * Gibt den Mittelpunkt des Rechtecks zurück.
     * @return Der Mittelpunkt des Rechtecks.
     */
    public Vector2 getMiddle()
    {
        return new Vector2(origin.getX() + (size.getX() / 2), origin.getY() + (size.getY() / 2));
    }

    /**
     * Gibt die Länge des Rechtecks zurück.
     *
     * @return Die Länge des Rechtecks.
     */
    public double getWidth()
    {
        return size.getX();
    }

    /**
     * Gibt die Höhe des Rechtecks zurück.
     *
     * @return Die Höhe des Rechtecks.
     */
    public double getHeight()
    {
        return size.getY();
    }

    public Vector2 getSize()
    {
        return size;
    }

    public RectangleDouble normalize()
    {
        List<Vector2> list = Arrays.asList(origin, size);
        Vector2 origin = Vector2.getExtremeVector(list, false, v -> v);
        Vector2 size = Vector2.getExtremeVector(list, true, v -> v);

        return new RectangleDouble(origin, size);
    }
}
