package sep.fimball;

import org.hamcrest.CustomTypeSafeMatcher;
import sep.fimball.general.data.Vector2;

/**
 * Vergleicht zwei Vektoren auf Gleichheit, mit einem kleinem Toleranzbereich.
 */
public class VectorMatcher extends CustomTypeSafeMatcher<Vector2>
{
    /**
     * Überprüft ob dieser Vektor bis auf einen kleinen Toleranzbereich mit den Komponenten des Gegebenen Vektors gleich ist.
     *
     * @param x Die x-Komponente des anderen Vektors.
     * @param y Die y-Komponente des anderen Vektors.
     * @return Ob die beiden double Werte bis auf einen kleinen Toleranzbereich gleich sind.
     */
    public static VectorMatcher matchesVector(double x, double y)
    {
        return new VectorMatcher(x, y);
    }

    /**
     * Überprüft ob dieser Vektor bis auf einen kleinen Toleranzbereich mit einem Gegebenen gleich ist.
     *
     * @param vector Der andere Vektor.
     * @return Ob die beiden Vektoren bis auf einen kleinen Toleranzbereich gleich sind.
     */
    public static VectorMatcher matchesVector(Vector2 vector)
    {
        return new VectorMatcher(vector);
    }

    /**
     * Der Vektor, der mit anderen Vektoren verglichen werden soll.
     */
    private Vector2 myVector;

    /**
     * Erstellt einen neuen VektorMatcher, der Vektoren mit den Gegebenen x/y-Werten vergleicht.
     *
     * @param x X-Wert zum vergleichen.
     * @param y Y-Wert zum vergleichen.
     */
    public VectorMatcher(double x, double y)
    {
        this(new Vector2(x, y));
    }

    /**
     * Erstellt einen neuen VektorMatcher, der Vektoren mit dem Gegebenen Vektor vergleicht.
     *
     * @param val Vektor, mit dem verglichen wird.
     */
    public VectorMatcher(Vector2 val)
    {
        super(val.toString());
        myVector = val;
    }

    @Override
    public boolean matchesSafely(Vector2 v2)
    {
        return vectorAboutEquals(v2, myVector);
    }

    /**
     * Vergleicht ob Vektoren bis auf den kleinen Toleranzbereich {@code EPSILON} gleich sind.
     *
     * @param a Der erste Vektor.
     * @param b Der zweite Vektor.
     * @return Gibt zurück ob die Vektoren bis auf den kleinen Toleranzbereich gleich sind.
     */
    private boolean vectorAboutEquals(Vector2 a, Vector2 b)
    {
        final double EPSILON = 1e-15;
        return Math.abs(a.getX() - b.getX()) < EPSILON && Math.abs(a.getY() - b.getY()) < EPSILON;
    }
}
