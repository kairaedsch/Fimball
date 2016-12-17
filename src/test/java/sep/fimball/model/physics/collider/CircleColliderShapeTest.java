package sep.fimball.model.physics.collider;

import org.junit.Test;
import sep.fimball.general.data.Vector2;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static sep.fimball.VectorMatcher.matchesVector;

/**
 * Tests für die Klasse CircleColliderShape.
 */
public class CircleColliderShapeTest
{
    /**
     * Überprüft ob die Kollision zwischen zwei Kreisen korrekt erkannt wird.
     */
    @Test
    public void hitInfoTest()
    {
        CircleColliderShape testShape = new CircleColliderShape(new Vector2(0.0, 0.0), 1.0);
        CircleColliderShape otherShape = new CircleColliderShape(new Vector2(0.0, 1.0), 1.0);

        HitInfo info = testShape.calculateHitInfo(otherShape, new Vector2(), new Vector2(), 0.0, new Vector2());
        assertThat(info.getShortestIntersect(), equalTo(new Vector2(0.0, 1.0)));
        assertThat(info.isHit(), is(true));
    }

    /**
     * Überprüft ob die Vektoren mit den maximalen/minimalen x- und y-Komponenten eines Kreises korrekt berechnet wird.
     */
    @Test
    public void extremePosTest()
    {
        CircleColliderShape testShape = new CircleColliderShape(new Vector2(0.0, 0.0), 1.0);

        assertThat(testShape.getExtremePos(-270.0, new Vector2(1.0, 1.0), true), matchesVector(1.0, 3.0));
        assertThat(testShape.getExtremePos(90.0, new Vector2(1.0, 1.0), false), matchesVector(-1.0, 1.0));
    }
}
