package sep.fimball.general.data;

import org.junit.Test;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by alexcekay on 11/28/16.
 */
public class VectorTest {

    private final double EPSILON = 1e-15;

    @Test
    public void plusTest()
    {
        Vector2 vecOne = new Vector2(1, 0);
        Vector2 vecTwo = new Vector2(0, 1);
        assertEquals(new Vector2(1, 1), vecOne.plus(vecTwo));
    }

    @Test
    public void minusTest()
    {
        Vector2 vecOne = new Vector2(100, 150);
        Vector2 vecTwo = new Vector2(50, 100);
        assertEquals(new Vector2(50, 50), vecOne.minus(vecTwo));
    }

    @Test
    public void scaleTest()
    {
        Vector2 vecOne = new Vector2(191, 6);
        double scalar = 7;
        assertEquals(new Vector2(1337, 42), vecOne.scale(scalar));
    }

    @Test
    public void magnitudeTest()
    {
        Vector2 vec = new Vector2(0, 9);
        assertEquals(9.0, vec.magnitude());
    }

    @Test
    public void dotTest()
    {
        Vector2 vecOne = new Vector2(21, 17);
        Vector2 vecTwo = new Vector2(3, 2);
        assertEquals(97.0, vecOne.dot(vecTwo));
    }

    @Test
    public void rotateTest()
    {
        Vector2 vecOne = new Vector2(1, 0);
        Vector2 rotatedVec = vecOne.rotate(Math.toRadians(90));
        assertTrue(Math.abs(0.0 - rotatedVec.getX()) < EPSILON);
        assertTrue(Math.abs(1.0 - rotatedVec.getY()) < EPSILON);
    }

    @Test
    public void normalizeTest()
    {
        Vector2 vecOne = new Vector2(3, 7);
        Vector2 normedVec = vecOne.normalized();
        assertTrue(Math.abs(1.0 - normedVec.magnitude()) < EPSILON);
    }

    @Test
    public void normalTest()
    {
        Vector2 vecOne = new Vector2(5, 5);
        assertEquals(new Vector2(5, -5), vecOne.normal());
    }

    @Test
    public void lerpTest()
    {
        Vector2 vecOne = new Vector2(1, 1);
        Vector2 vecTwo = new Vector2(11, 11);
        assertEquals(new Vector2(6, 6), vecOne.lerp(vecTwo, 0.5));
    }

    @Test
    public void angleBetweenTest()
    {
        Vector2 vecOne = new Vector2(1, 0);
        Vector2 vecTwo = new Vector2(0, 1);
        assertEquals(90.0, Math.toDegrees(vecOne.angleBetween(vecTwo)));
    }

    @Test
    public void roundTest()
    {
       Vector2 vecOne = new Vector2(1.2, 1.5);
        assertEquals(new Vector2(1.0, 2.0), vecOne.round());
    }
}
