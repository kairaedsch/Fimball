package sep.fimball.general.data;

import org.junit.Test;
import sep.fimball.VectorMatcher;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * Tests für die Klasse RectangleDoubleByPoints.
 */
public class RectangleDoubleByPointsTest
{
    /**
     * Testet die korrekte Funktionalität des Rechtecks welches durch zwei Punkte gegeben ist.
     */
    @Test
    public void testRectangleDoubleByPoints()
    {
        RectangleDoubleByPoints rectangleDoubleByPoints = new RectangleDoubleByPoints(new Vector2(0, 0), new Vector2(6, 6));
        assertThat("Die Breite des Rechtecks ist 6", rectangleDoubleByPoints.getWidth(), is(6.0));
        assertThat("Die Höhe des Rechtecks ist 6", rectangleDoubleByPoints.getHeight(), is(6.0));
        assertThat("Die rechte untere Ecke ist (6, 6)", rectangleDoubleByPoints.getEnd(), is(new VectorMatcher(6, 6)));
        assertThat("Die linke obere Ecke ist (0, 0)", rectangleDoubleByPoints.getOrigin(), is(new VectorMatcher(0, 0)));
        assertThat("Die Größe des Rechtecks ist (6, 6)", rectangleDoubleByPoints.getSize(), is(new VectorMatcher(6, 6)));
    }
}
