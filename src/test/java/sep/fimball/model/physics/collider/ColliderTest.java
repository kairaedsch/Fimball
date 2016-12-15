package sep.fimball.model.physics.collider;

import org.junit.Test;
import sep.fimball.model.physics.collision.CollisionType;
import sep.fimball.model.physics.element.BallPhysicsElement;
import sep.fimball.model.physics.element.BasePhysicsElement;
import sep.fimball.model.physics.element.PhysicsElement;

import java.util.ArrayList;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by TheAsuro on 15.12.2016.
 */
public class ColliderTest
{
    @Test
    public void CollisionTest()
    {
        BallPhysicsElement ball = mock(BallPhysicsElement.class);
        when(ball.getLayer()).thenReturn(WorldLayer.GROUND);

        PhysicsElement element = mock(PhysicsElement.class);
        when(element.getBasePhysicsElement()).thenReturn(mock(BasePhysicsElement.class));

        ColliderShape shape = mock(ColliderShape.class);
        HitInfo hi = mock(HitInfo.class);
        when(hi.isHit()).thenReturn(true);
        when(shape.calculateHitInfo(null, null, null, 0.0, null)).thenReturn(hi);

        ArrayList<ColliderShape> shapes = new ArrayList<>();
        shapes.add(shape);

        CollisionType type = mock(CollisionType.class);

        Collider collider = new Collider(WorldLayer.GROUND, shapes, type, 0);
        assertThat(collider.checkCollision(ball, element), is(true));
    }
}
