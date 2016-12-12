package sep.fimball.model.physics.collision;

import org.junit.Test;
import sep.fimball.general.data.Vector2;
import sep.fimball.model.physics.element.BallPhysicsElement;

import static org.mockito.Mockito.*;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * Tests für die Klasse NormalCollision
 */
public class NormalCollisionTest
{
    @Test
    public void testNormalCollision()
    {
        BallPhysicsElement ballPhysicsElement = mock(BallPhysicsElement.class);
        CollisionInfo collisionInfo = mock(CollisionInfo.class);

        doCallRealMethod().when(ballPhysicsElement).setPosition(notNull());
        doCallRealMethod().when(ballPhysicsElement).setVelocity(notNull());
        when(ballPhysicsElement.getPosition()).thenCallRealMethod();
        when(ballPhysicsElement.getVelocity()).thenCallRealMethod();

        when(collisionInfo.getBall()).thenReturn(ballPhysicsElement);
        when(collisionInfo.getShortestIntersect()).thenReturn(new Vector2(-1.0, 0));

        ballPhysicsElement.setPosition(new Vector2(0, 0));
        ballPhysicsElement.setVelocity(new Vector2(1, 1));
        NormalCollision normalCollision = new NormalCollision();
        normalCollision.applyCollision(collisionInfo);
        assertThat("Neue Position ist: (-1.0, 0.0)", ballPhysicsElement.getPosition(), is(new Vector2(-1.0, 0.0)));
        assertThat("Neue Geschwindigkeit ist: (-0.7, 1.0)", ballPhysicsElement.getVelocity(), is(new Vector2(-0.7, 1.0)));
    }
}
