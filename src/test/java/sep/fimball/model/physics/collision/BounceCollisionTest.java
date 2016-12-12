package sep.fimball.model.physics.collision;

import org.junit.Test;
import sep.fimball.general.data.Vector2;
import sep.fimball.model.physics.element.BallPhysicsElement;
import sep.fimball.model.physics.element.PhysicsElement;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * Created by alexcekay on 12/11/16.
 */
public class BounceCollisionTest
{
    @Test
    public void testBounceCollision()
    {
        BallPhysicsElement ballPhysicsElement = mock(BallPhysicsElement.class);
        CollisionInfo collisionInfo = mock(CollisionInfo.class);

        when(ballPhysicsElement.getPosition()).thenCallRealMethod();
        when(ballPhysicsElement.getVelocity()).thenCallRealMethod();
        doCallRealMethod().when(ballPhysicsElement).setPosition(notNull());
        doCallRealMethod().when(ballPhysicsElement).setVelocity(notNull());

        when(collisionInfo.getBall()).thenReturn(ballPhysicsElement);
        when(collisionInfo.getShortestIntersect()).thenReturn(new Vector2(1.0, 0));

        ballPhysicsElement.setPosition(new Vector2(2.0, 0.0));
        ballPhysicsElement.setVelocity(new Vector2(-1.0, 0.0));
        BounceCollision bounceCollision = new BounceCollision(2.0);
        bounceCollision.applyCollision(collisionInfo);
        Vector2 newVelocity = ballPhysicsElement.getVelocity();
        assertThat("Neue Geschwindigkeit ist (3.0, 0.0)", newVelocity, is(new Vector2(3.0, 0.0)));
    }
}
