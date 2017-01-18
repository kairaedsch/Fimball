package sep.fimball.general.data;

/**
 * Speichert zwei Integer-Werte als x und y Position.
 */
public class Vector2I
{
    /**
     * Gespeicherter x-Wert.
     */
    private int x;

    /**
     * Gespeicherter y-Wert.
     */
    private int y;

    /**
     * Erzeugt einen neuen Vektor mit x und y Position.
     * @param x X-Position.
     * @param y Y-Position.
     */
    public Vector2I(int x, int y)
    {
        this.x = x;
        this.y = y;
    }

    /**
     * Gibt die x-Position zurück.
     * @return Die x-Position.
     */
    public int getX()
    {
        return x;
    }

    /**
     * Gibt die y-Position zurück.
     * @return Die y-Position.
     */
    public int getY()
    {
        return y;
    }
}
