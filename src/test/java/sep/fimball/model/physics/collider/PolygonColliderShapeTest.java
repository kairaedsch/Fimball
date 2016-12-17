package sep.fimball.model.physics.collider;

import org.junit.Test;
import sep.fimball.general.data.Vector2;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static sep.fimball.VectorMatcher.matchesVector;

/**
 * Tests für die Klasse PolygonColliderShape.
 */
public class PolygonColliderShapeTest
{
    /**
     * Überprüft ob die Erkennung der Kollision zwischen einem Kreis und einem Polygon korrekt funktioniert.
     */
    @Test
    public void calculateHitInfoTest()
    {
        CircleColliderShape otherShape = mock(CircleColliderShape.class);
        when(otherShape.getPosition()).thenReturn(new Vector2(0.0, 1.0));
        when(otherShape.getRadius()).thenReturn(0.2);

        List<Vector2> vertices = new ArrayList<>();
        vertices.add(new Vector2(-1.0, 1.0));
        vertices.add(new Vector2(1.0, 1.0));
        vertices.add(new Vector2(1.0, -1.0));
        vertices.add(new Vector2(-1.0, -1.0));

        PolygonColliderShape poly = new PolygonColliderShape(vertices);

        HitInfo info = poly.calculateHitInfo(otherShape, new Vector2(), new Vector2(), 0.0, new Vector2(0.0, 0.0));

        assertThat(info.isHit(), is(true));
        assertThat(info.getShortestIntersect(), matchesVector(0.0, 0.2));
    }
}
