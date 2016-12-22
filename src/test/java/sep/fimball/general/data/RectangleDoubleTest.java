package sep.fimball.general.data;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * Tests für die Klasse RectangleDouble.
 */
public class RectangleDoubleTest
{
    /**
     * Testet die korrekte Funktionalität des Rechtecks.
     */
    @Test
    public void testRectangleDouble()
    {
        RectangleDouble rectangleDouble = new RectangleDouble(new Vector2(0, 0), new Vector2(5, 5));
        assertThat("Die Höhe des Rechtecks ist 5", rectangleDouble.getHeight(), is(5.0));
        assertThat("Die Breite des Rechtecks ist 5", rectangleDouble.getWidth(), is(5.0));
        assertThat("Die Mitte des Rechtecks ist (2.5, 2.5)", rectangleDouble.getMiddle(), is(new Vector2(2.5, 2.5)));

        RectangleDouble rectangleDoubleTwo = new RectangleDouble(new Vector2(0, 0), 10, 10);
        assertThat("Das zweite Rechteck hat den Mittelpunkt (5, 5)", rectangleDoubleTwo.getMiddle(), is(new Vector2(5, 5)));
    }
}
