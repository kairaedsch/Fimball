package sep.fimball.model.physics.collision;

import org.junit.Test;
import sep.fimball.VectorMatcher;
import sep.fimball.general.data.Vector2;
import sep.fimball.model.physics.element.BallPhysicsElement;
import sep.fimball.model.physics.element.PhysicsElement;

import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

/**
 * Tests für die Klasse AccelerationCollision
 */
public class AccelerationCollisionTest
{

    /**
     * Testet ob der Ball korrekt von der AccelerationCollision beschleunigt wird.
     */
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

        assertThat(newVelocity, new VectorMatcher(0.0, 10.004));
    }

}
