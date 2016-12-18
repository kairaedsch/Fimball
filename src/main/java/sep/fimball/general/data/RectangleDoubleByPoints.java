package sep.fimball.general.data;

/**
 * RectangleDouble stellt ein Rechteck dar, wobei seine Daten mit Double-Werten gespeichert werden und es durch zwei Punkte dargestellt wird.
 */
public class RectangleDoubleByPoints
{
    /**
     * Der erste Punkt durch den dieses Rechteck dargestellt wird.
     */
    private final Vector2 pointA;

    /**
     * Der zweite Punkt durch den dieses Rechteck dargestellt wird.
     */
    private final Vector2 pointB;

    /**
     * Erzeugt ein Rechteck welches durch zwei Punkte dargestellt wird.
     *
     * @param pointA Der erste Punkt durch den das Rechteck dargestellt wird.
     * @param pointB Der zweite Punkt durch den das Rechteck dargestellt wird.
     */
    public RectangleDoubleByPoints(Vector2 pointA, Vector2 pointB)
    {
        this.pointA = pointA;
        this.pointB = pointB;
    }

    /**
     * Erzeugt ein RectangleDouble aus diesem welches durch die linke obere Ecke sowie Breite und Höhe dargestellt wird.
     *
     * @return Ein aus diesem Rechteck erzeugtes Rechteck welches durch die linke obere Ecke sowie Breite und Höhe dargestellt wird.
     */
    public RectangleDouble toRectangleDouble()
    {
        return new RectangleDouble(getOrigin(), getSize());
    }

    /**
     * Gibt den ersten Punkt durch den dieses Rechteck dargestellt wird zurück.
     *
     * @return Der erste Punkt durch den dieses Rechteck dargestellt wird.
     */
    public Vector2 getPointA()
    {
        return pointA;
    }

    /**
     * Gibt den zweiten Punkt durch den dieses Rechteck dargestellt wird zurück.
     *
     * @return Der zweite Punkt durch den dieses Rechteck dargestellt wird.
     */
    public Vector2 getPointB()
    {
        return pointB;
    }

    /**
     * Gibt die linke obere Ecke des Rechtecks an.
     *
     * @return Die linke obere Ecke des Rechtecks.
     */
    public Vector2 getOrigin()
    {
        return Vector2.minComponents(pointA, pointB);
    }

    /**
     * Gibt die rechte untere Ecke des Rechtecks an.
     *
     * @return Die rechte untere Ecke des Rechtecks.
     */
    public Vector2 getEnd()
    {
        return Vector2.maxComponents(pointA, pointB);
    }

    /**
     * Gibt die Breite und Höhe des Rechtecks an.
     *
     * @return Breite und Höhe des Rechtecks.
     */
    public Vector2 getSize()
    {
        return getEnd().minus(getOrigin());
    }

    /**
     * Gibt die Breite des Rechtecks zurück.
     *
     * @return Die Breite des Rechtecks.
     */
    public double getWidth()
    {
        return getSize().getX();
    }

    /**
     * Gibt die Höhe des Rechtecks zurück.
     *
     * @return Die Höhe des Rechtecks.
     */
    public double getHeight()
    {
        return getSize().getY();
    }
}
