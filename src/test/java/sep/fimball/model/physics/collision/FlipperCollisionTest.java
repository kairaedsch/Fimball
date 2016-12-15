package sep.fimball.model.physics.collision;

import org.junit.Test;
import sep.fimball.general.data.Vector2;
import sep.fimball.model.physics.element.BallPhysicsElement;
import sep.fimball.model.physics.element.BasePhysicsElement;
import sep.fimball.model.physics.element.FlipperPhysicsElement;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.notNull;
import static org.mockito.Mockito.*;

/**
 * Tests für die Klasse FlipperCollision
 */
public class FlipperCollisionTest
{
    @Test
    public void testFlipperCollision()
    {
        FlipperPhysicsElement flipperPhysicsElement = mock(FlipperPhysicsElement.class);
        BallPhysicsElement ballPhysicsElement = mock(BallPhysicsElement.class);
        BasePhysicsElement flipperBasePhysics = mock(BasePhysicsElement.class);
        BasePhysicsElement ballBasePhysics = mock(BasePhysicsElement.class);
        CollisionInfo collisionInfo = mock(CollisionInfo.class);

        when(collisionInfo.getOtherPhysicsElement()).thenReturn(flipperPhysicsElement);
        when(collisionInfo.getBall()).thenReturn(ballPhysicsElement);
        when(collisionInfo.getShortestIntersect()).thenReturn(new Vector2(0, -1));
        when(flipperPhysicsElement.getPosition()).thenReturn(new Vector2(0, 0));
        when(flipperPhysicsElement.getRotation()).thenReturn(0.0);
        when(flipperPhysicsElement.getAngularVelocity()).thenReturn(-500.0);
        when(flipperPhysicsElement.rotatingDown()).thenReturn(true);
        when(flipperPhysicsElement.getBasePhysicsElement()).thenReturn(flipperBasePhysics);
        when(ballPhysicsElement.getBasePhysicsElement()).thenReturn(ballBasePhysics);
        when(ballPhysicsElement.getPosition()).thenCallRealMethod();
        when(ballPhysicsElement.getVelocity()).thenCallRealMethod();
        doCallRealMethod().when(ballPhysicsElement).setPosition(notNull());
        doCallRealMethod().when(ballPhysicsElement).setVelocity(notNull());
        when(flipperBasePhysics.getPivotPoint()).thenReturn(new Vector2(2, 2));
        when(ballBasePhysics.getPivotPoint()).thenReturn(new Vector2(2, 2));

        ballPhysicsElement.setVelocity(new Vector2(0, 0));
        ballPhysicsElement.setPosition(new Vector2(10, -2));

        FlipperCollision flipperCollisionSpy = spy(new FlipperCollision());
        doNothing().when(flipperCollisionSpy).callNormalCollision(notNull());
        flipperCollisionSpy.applyCollision(collisionInfo);
        assertThat("Der Ball hat nach der Kollision mit dem Flipper die Geschwindigkeit (0.0, -50.0", ballPhysicsElement.getVelocity(), is(new Vector2(0.0, -50.0)));
    }
}
