package sep.fimball.model.physics.collision;

import org.junit.Test;
import sep.fimball.VectorMatcher;
import sep.fimball.general.data.Vector2;
import sep.fimball.model.physics.element.BallPhysicsElement;
import sep.fimball.model.physics.element.PlungerPhysicsElement;

import static org.mockito.Mockito.*;
import static org.junit.Assert.assertThat;

/**
 * Tests für die Klasse PlungerCollision
 */
public class PlungerCollisionTest
{
    @Test
    public void testPlungerCollision()
    {
        PlungerPhysicsElement plungerPhysicsElement = mock(PlungerPhysicsElement.class);
        BallPhysicsElement ballPhysicsElement = mock(BallPhysicsElement.class);
        CollisionInfo collisionInfo = mock(CollisionInfo.class);

        when(collisionInfo.getOtherPhysicsElement()).thenReturn(plungerPhysicsElement);
        when(collisionInfo.getBall()).thenReturn(ballPhysicsElement);
        when(ballPhysicsElement.getVelocity()).thenCallRealMethod();
        doCallRealMethod().when(ballPhysicsElement).setVelocity(notNull());
        when(plungerPhysicsElement.getStrength()).thenReturn(500.0);
        when(plungerPhysicsElement.getRotation()).thenReturn(0.0);

        ballPhysicsElement.setVelocity(new Vector2(0, 0));
        PlungerCollision plungerCollision = spy(new PlungerCollision());
        doNothing().when(plungerCollision).callNormalCollision(notNull());
        plungerCollision.applyCollision(collisionInfo);
        assertThat(ballPhysicsElement.getVelocity(), new VectorMatcher(0, -8));

        when(plungerPhysicsElement.getRotation()).thenReturn(90.0);
        ballPhysicsElement.setVelocity(new Vector2(0, 0));
        plungerCollision.applyCollision(collisionInfo);
        assertThat(ballPhysicsElement.getVelocity(), new VectorMatcher(8, 0));
    }
}
