package sep.fimball.model.physics.collision;

import org.junit.Test;
import sep.fimball.model.physics.collider.WorldLayer;
import sep.fimball.model.physics.element.BallPhysicsElement;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

/**
 * Tests für die Klasse RampCollision
 */
public class RampCollisionTest
{

    /**
     * Testet ob sich der Ball korrekt auf und unter Rampen verhält.
     */
    @Test
    public void testRampCollision()
    {
        BallPhysicsElement ballPhysicsElement = mock(BallPhysicsElement.class);
        CollisionInfo collisionInfo = mock(CollisionInfo.class);

        when(collisionInfo.getBall()).thenReturn(ballPhysicsElement);
        when(ballPhysicsElement.getHeight()).thenCallRealMethod();
        when(ballPhysicsElement.getLayer()).thenReturn(WorldLayer.GROUND);
        doCallRealMethod().when(ballPhysicsElement).setHeight(anyDouble());

        ballPhysicsElement.setHeight(0.0);
        RampCollision rampCollision = new RampCollision();
        rampCollision.applyCollision(collisionInfo);
        assertThat("Wenn Rampen Kollision mit Ball auf Boden ist Höhe des Balls 0.0", ballPhysicsElement.getHeight(), is(0.0));

        ballPhysicsElement.setHeight(1.0);
        when(ballPhysicsElement.getLayer()).thenReturn(WorldLayer.RAMP);
        rampCollision.applyCollision(collisionInfo);
        double EPSILON = 1e-15;
        assertThat("Wenn erste Rampen Kollision mit Ball auf Rampe ist Höhe des Balls ~1.03", (1.03 - ballPhysicsElement.getHeight()) < EPSILON, is(true));
        rampCollision.applyCollision(collisionInfo);
        assertThat("Wenn zweite Rampen Kollision mit Ball auf Rampe ist Höhe des Balls ~1.06", (1.06 - ballPhysicsElement.getHeight()) < EPSILON, is(true));
    }
}
