package sep.fimball.model.physics.collision;

import org.junit.Test;
import sep.fimball.general.data.Vector2;
import sep.fimball.model.physics.collider.CircleColliderShape;
import sep.fimball.model.physics.collider.ColliderShape;
import sep.fimball.model.physics.collider.PolygonColliderShape;
import sep.fimball.model.physics.element.BallPhysicsElement;
import sep.fimball.model.physics.element.BasePhysicsElement;
import sep.fimball.model.physics.element.PhysicsElement;

import static org.mockito.Mockito.*;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * Tests für die Klasse RampClimbingCollision
 */
public class RampClimbingCollisionTest
{
    /**
     * Testet ob der Ball korrekt an Höhe gewinnt wenn er eine Rampe nach oben rollt.
     */
    @Test
    public void testRampClimbingCollision()
    {
        BallPhysicsElement ballPhysicsElement = mock(BallPhysicsElement.class);
        CollisionInfo collisionInfo = mock(CollisionInfo.class);
        ColliderShape rampCollisionShape = mock(PolygonColliderShape.class);
        PhysicsElement otherPhysicsElement = mock(PhysicsElement.class);
        BasePhysicsElement basePhysicsElement = mock(BasePhysicsElement.class);
        CircleColliderShape ballCollider = mock(CircleColliderShape.class);

        when(ballPhysicsElement.getPosition()).thenCallRealMethod();
        when(ballPhysicsElement.getHeight()).thenCallRealMethod();
        when(ballPhysicsElement.getCollider()).thenReturn(ballCollider);
        when(ballCollider.getRadius()).thenReturn(2.0);
        doCallRealMethod().when(ballPhysicsElement).setPosition(notNull());
        doCallRealMethod().when(ballPhysicsElement).setHeight(anyDouble());
        when(rampCollisionShape.getExtremePos(anyDouble(), notNull(), eq(false))).thenReturn(new Vector2(0, 0));
        when(rampCollisionShape.getExtremePos(anyDouble(), notNull(), eq(true))).thenReturn(new Vector2(6, 6));
        when(otherPhysicsElement.getPosition()).thenReturn(new Vector2(0, 0));
        when(otherPhysicsElement.getRotation()).thenReturn(0.0);
        when(otherPhysicsElement.getBasePhysicsElement()).thenReturn(basePhysicsElement);
        when(basePhysicsElement.getPivotPoint()).thenReturn(new Vector2(3, 3));
        when(collisionInfo.getBall()).thenReturn(ballPhysicsElement);
        when(collisionInfo.getOtherPhysicsElement()).thenReturn(otherPhysicsElement);
        when(collisionInfo.getOtherColliderShape()).thenReturn(rampCollisionShape);

        ballPhysicsElement.setPosition(new Vector2(8, 8));
        ballPhysicsElement.setHeight(0.0);

        RampClimbingCollision rampClimbingCollision = new RampClimbingCollision();
        rampClimbingCollision.applyCollision(collisionInfo);
        assertThat("Überprüfe ob Ball Höhe gleich 0 bleibt wenn er nicht auf der Rampen Auffahrt ist", ballPhysicsElement.getHeight(), is(0.0));

        ballPhysicsElement.setPosition(new Vector2(1, 1));
        rampClimbingCollision.applyCollision(collisionInfo);
        assertThat("Wenn der Ball die Rampe zur Hälfte hochgerollt ist hat er 75% seiner Größe erreicht", ballPhysicsElement.getHeight(), is(1.5));

        ballPhysicsElement.setPosition(new Vector2(0, 0));
        rampClimbingCollision.applyCollision(collisionInfo);
        assertThat("Sobald der Ball die obere Kante der Rampe berührt hat er 100% seiner Größe erreicht", ballPhysicsElement.getHeight(), is(2.0));

        when(otherPhysicsElement.getRotation()).thenReturn(90.0);
        ballPhysicsElement.setHeight(0.0);
        ballPhysicsElement.setPosition(new Vector2(1, 1));
        rampClimbingCollision.applyCollision(collisionInfo);
        assertThat("Wenn der Ball die um 90 Grad gedrehte Rampe zur Hälfte hochgerollt ist hat er 75% seiner Größe erreicht", ballPhysicsElement.getHeight(), is(1.5));

        when(otherPhysicsElement.getRotation()).thenReturn(180.0);
        ballPhysicsElement.setHeight(0.0);
        ballPhysicsElement.setPosition(new Vector2(1, 1));
        rampClimbingCollision.applyCollision(collisionInfo);
        assertThat("Wenn der Ball die um 180 Grad gedrehte Rampe zur Hälfte hochgerollt ist hat er 75% seiner Größe erreicht", ballPhysicsElement.getHeight(), is(1.5));
    }
}
