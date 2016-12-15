package sep.fimball;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import sep.fimball.general.data.Vector2;

/**
 * Vergleicht zwei Vektoren auf gleichheit, mit einem kleinen Toleranzbereich.
 */
public class VectorMatcher extends BaseMatcher<Vector2>
{
    /**
     * Der Vektor, der mit anderen Vektoren verglichen werden soll.
     */
    private Vector2 myVector;

    /**
     * Erstellt einen neuen VektorMatcher, der Vektoren mit den gegebenen x/y-Werten vergleicht.
     * @param x X-Wert zum vergleichen.
     * @param y Y-Wert zum vergleichen.
     */
    public VectorMatcher(double x, double y)
    {
        this(new Vector2(x, y));
    }

    /**
     * Erstellt einen neuen VektorMatcher, der Vektoren mit dem gegebenen Vektor vergleicht.
     * @param val Vektor, mit dem verglichen wird.
     */
    public VectorMatcher(Vector2 val)
    {
        super();
        myVector = val;
    }

    @Override
    public boolean matches(Object o)
    {
        final Vector2 vector = (Vector2)o;
        return vectorAboutEquals(vector, myVector);
    }

    private boolean vectorAboutEquals(Vector2 a, Vector2 b)
    {
        final double EPSILON = 1e-15;
        return Math.abs(a.getX() - b.getX()) < EPSILON && Math.abs(a.getY() - b.getY()) < EPSILON;
    }

    @Override
    public void describeTo(Description description)
    {
        description.appendText(myVector.toString());
    }
}
