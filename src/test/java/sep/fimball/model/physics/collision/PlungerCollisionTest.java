package sep.fimball.model.physics.collision;

import org.junit.Test;
import sep.fimball.VectorMatcher;
import sep.fimball.general.data.Vector2;
import sep.fimball.model.physics.element.BallPhysicsElement;
import sep.fimball.model.physics.element.PlungerPhysicsElement;

import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

/**
 * Tests für die Klasse PlungerCollision
 */
public class PlungerCollisionTest
{
    /**
     * Testet ob der Ball korrekt vom Plunger nach oben bzw. rechts beschleunigt wird.
     */
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
        when(plungerPhysicsElement.getStrength()).thenReturn(8.0);
        when(plungerPhysicsElement.getRotation()).thenReturn(0.0);
        when(plungerPhysicsElement.getStrengthMultiplier()).thenReturn(1.0);

        //Überprüfe die Beschleunigung bei einem normalen Plunger ohne Rotation.
        ballPhysicsElement.setVelocity(new Vector2(0, 0));
        PlungerCollision plungerCollision = spy(new PlungerCollision());
        doNothing().when(plungerCollision).callNormalCollision(notNull());
        plungerCollision.applyCollision(collisionInfo);
        assertThat("Die Geschwindigkeit des Balls nach Beschleunigung durch den Plunger ist (0, -8)", ballPhysicsElement.getVelocity(), new VectorMatcher(0, -8));

        //Überprüfe die Beschleunigung bei einem um 90 Grad nach rechts gedrehten Plunger.
        when(plungerPhysicsElement.getRotation()).thenReturn(90.0);
        ballPhysicsElement.setVelocity(new Vector2(0, 0));
        plungerCollision.applyCollision(collisionInfo);
        assertThat("Die Geschwindigkeit des Balls nach Beschleunigung durch den Plunger ist (8, 0)", ballPhysicsElement.getVelocity(), new VectorMatcher(8, 0));
    }
}
