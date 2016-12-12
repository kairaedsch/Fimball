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
public class AccelerationCollisionTest
{
    private final double EPSILON = 1e-15;

    @Test
    public void testAccelerationCollision()
    {
        final Vector2 acceleration = new Vector2(1.0, 0);
        BallPhysicsElement ballPhysicsElement = mock(BallPhysicsElement.class);
        PhysicsElement otherElement = mock(PhysicsElement.class);
        CollisionInfo collisionInfo = mock(CollisionInfo.class);

        when(ballPhysicsElement.getVelocity()).thenCallRealMethod();
        doCallRealMethod().when(ballPhysicsElement).setVelocity(notNull());
        when(otherElement.getRotation()).thenReturn(90.0);
        when(collisionInfo.getBall()).thenReturn(ballPhysicsElement);
        when(collisionInfo.getOtherPhysicsElement()).thenReturn(otherElement);

        ballPhysicsElement.setVelocity(new Vector2(0, 10));
        AccelerationCollision accelerationCollision = new AccelerationCollision(acceleration);
        accelerationCollision.applyCollision(collisionInfo);
        Vector2 newVelocity = ballPhysicsElement.getVelocity();
        assertThat("Ball velocity x is ~ 0.0", Math.abs(0.0 - newVelocity.getX()) < EPSILON, is(true));
        assertThat("Ball velocity y is ~ 10.016", Math.abs(10.016 - newVelocity.getY()) < EPSILON, is(true));
    }

}
