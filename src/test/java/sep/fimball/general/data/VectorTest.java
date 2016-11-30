package sep.fimball.general.data;

import org.junit.Test;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;

public class VectorTest {

    private final double EPSILON = 1e-15;

    @Test
    public void plusTest()
    {
        Vector2 vecOne = new Vector2(1, 0);
        Vector2 vecTwo = new Vector2();
        assertThat(vecOne.plus(vecTwo), is(new Vector2(1, 0)));
    }

    @Test
    public void minusTest()
    {
        Vector2 vecOne = new Vector2(100, 150);
        Vector2 vecTwo = new Vector2(50, 100);
        assertThat(vecOne.minus(vecTwo), is(new Vector2(50, 50)));
    }

    @Test
    public void scaleTest()
    {
        Vector2 vecOne = new Vector2(191, 6);
        double scalar = 7;
        assertThat(vecOne.scale(scalar), is(new Vector2(1337, 42)));
    }

    @Test
    public void magnitudeTest()
    {
        Vector2 vec = new Vector2(0, 9);
        assertThat(vec.magnitude(), is(9.0));
    }

    @Test
    public void dotTest()
    {
        Vector2 vecOne = new Vector2(21, 17);
        Vector2 vecTwo = new Vector2(3, 2);
        assertThat(vecOne.dot(vecTwo), is(97.0));
    }

    @Test
    public void rotateTest()
    {
        Vector2 vecOne = new Vector2(1, 0);
        Vector2 rotatedVec = vecOne.rotate(Math.toRadians(90));
        assertThat(Math.abs(0.0 - rotatedVec.getX()) < EPSILON, is(true));
        assertThat(Math.abs(1.0 - rotatedVec.getY()) < EPSILON, is(true));
    }

    @Test
    public void rotatePivotTest()
    {
        Vector2 vecOne = new Vector2(2, 0);
        assertThat(vecOne.rotate(Math.toRadians(90), new Vector2(1, 0)), is(new Vector2(1, 1)));
    }

    @Test
    public void normalizeTest()
    {
        Vector2 vecOne = new Vector2(3, 7);
        Vector2 normedVec = vecOne.normalized();
        assertThat(Math.abs(1.0 - normedVec.magnitude()) < EPSILON, is(true));
    }

    @Test
    public void normalTest()
    {
        Vector2 vecOne = new Vector2(5, 5);
        assertThat(vecOne.normal(), is(new Vector2(5, -5)));
    }

    @Test
    public void lerpTest()
    {
        Vector2 vecOne = new Vector2(1, 1);
        Vector2 vecTwo = new Vector2(11, 11);
        assertThat(vecOne.lerp(vecTwo, 0.5), is(new Vector2(6, 6)));
    }

    @Test
    public void angleBetweenTest()
    {
        Vector2 vecOne = new Vector2(1, 0);
        Vector2 vecTwo = new Vector2(0, 1);
        assertThat(Math.toDegrees(vecOne.angleBetween(vecTwo)), is(90.0));
    }

    @Test
    public void roundTest()
    {
       Vector2 vecOne = new Vector2(1.2, 1.5);
        assertThat(vecOne.round(), is(new Vector2(1.0, 2.0)));
    }

    @Test
    public void clampTest()
    {
        Vector2 vecOne = new Vector2(1.0, 1.0);
        assertThat(Math.abs(1.0 - vecOne.clamp(1.0).magnitude()) < EPSILON, is(true));

        Vector2 vecTwo = new Vector2(1.0, 0.0);
        assertThat(vecTwo.clamp(1.0), is(vecTwo));

        Vector2 vecThree = new Vector2(0.0, 2.0);
        assertThat(vecThree.clamp(3.0), is(vecThree));
    }

    @Test
    public void projectTest()
    {
        Vector2 vecOne = new Vector2(1, 1);
        assertThat(vecOne.project(new Vector2(1, 0)), is(new Vector2(1, 0)));
    }

    @Test
    public void vectorToStringTest()
    {
        Vector2 vecOne = new Vector2(1, 1);
        assertThat(vecOne.toString(), is("{1.0|1.0}"));
    }

    @Test
    public void vectorCloneTest()
    {
        Vector2 vecOne = new Vector2(1.2, 5.1);
        Vector2 vecClone = vecOne.clone();
        assertThat(vecOne, is(vecClone));
    }

    @Test
    public void equalTest()
    {
        Vector2 vecOne = new Vector2(13.37, 42);
        assertThat(vecOne, is(vecOne));
        assertThat(vecOne.equals(null), is(false));
        assertThat(vecOne.equals(42), is(false));
    }
}
