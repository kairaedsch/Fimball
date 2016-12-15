package sep.fimball.model.physics.collider;

import org.junit.Test;
import sep.fimball.general.data.Vector2;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * Created by TheAsuro on 15.12.2016.
 */
public class CircleColliderShapeTest
{
    @Test
    public void hitInfoTest()
    {
        CircleColliderShape testShape = new CircleColliderShape(new Vector2(0.0, 0.0), 1.0);
        CircleColliderShape otherShape = new CircleColliderShape(new Vector2(0.0, 1.0), 1.0);

        HitInfo info = testShape.calculateHitInfo(otherShape, new Vector2(), new Vector2(), 0.0, new Vector2());
        assertThat(info.getShortestIntersect(), equalTo(new Vector2(0.0, 1.0)));
        assertThat(info.isHit(), is(true));
    }

    @Test
    public void extremePosTest()
    {
        CircleColliderShape testShape = new CircleColliderShape(new Vector2(0.0, 0.0), 1.0);

        Vector2 extremePos1 = testShape.getExtremePos(-270.0, new Vector2(1.0, 1.0), true);
        assertThat(vectorAboutEquals(extremePos1, new Vector2(1.0, 3.0)), is(true));
        Vector2 extremePos2 = testShape.getExtremePos(90.0, new Vector2(1.0, 1.0), false);
        assertThat(vectorAboutEquals(extremePos2, new Vector2(-1.0, 1.0)), is(true));
    }

    private boolean vectorAboutEquals(Vector2 a, Vector2 b)
    {
        final double EPSILON = 1e-15;
        return Math.abs(a.getX() - b.getX()) < EPSILON && Math.abs(a.getY() - b.getY()) < EPSILON;
    }
}
