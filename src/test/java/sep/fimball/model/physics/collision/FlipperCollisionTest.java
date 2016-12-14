package sep.fimball.model.physics.collision;

import org.junit.Test;
import sep.fimball.general.data.Vector2;
import sep.fimball.model.physics.element.BallPhysicsElement;
import sep.fimball.model.physics.element.BasePhysicsElement;
import sep.fimball.model.physics.element.FlipperPhysicsElement;

import static org.mockito.Mockito.*;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * Created by alexcekay on 12/14/16.
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
        when(flipperPhysicsElement.getPosition()).thenReturn(new Vector2(0, 0));
        when(flipperPhysicsElement.getRotation()).thenReturn(0.0);
        when(flipperPhysicsElement.getBasePhysicsElement()).thenReturn(flipperBasePhysics);
        when(ballPhysicsElement.getPosition()).thenCallRealMethod();
        when(ballPhysicsElement.getVelocity()).thenCallRealMethod();
        doCallRealMethod().when(ballPhysicsElement).setPosition(notNull());
        doCallRealMethod().when(ballPhysicsElement).setVelocity(notNull());
        when(flipperBasePhysics.getPivotPoint()).thenReturn(new Vector2(2, 2));
        when(ballBasePhysics.getPivotPoint()).thenReturn(new Vector2(2, 2));

        FlipperCollision flipperCollisionSpy = spy(new FlipperCollision());
        doNothing().when((NormalCollision)flipperCollisionSpy).applyCollision(notNull());
        flipperCollisionSpy.applyCollision(collisionInfo);
    }
}
