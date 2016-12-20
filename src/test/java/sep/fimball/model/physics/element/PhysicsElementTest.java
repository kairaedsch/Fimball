package sep.fimball.model.physics.element;

import org.junit.Test;
import org.mockito.Mockito;
import sep.fimball.general.data.Vector2;
import sep.fimball.model.physics.collider.Collider;
import sep.fimball.model.physics.game.CollisionEventArgs;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

public class PhysicsElementTest
{
    @Test
    public void checkCollision() throws Exception
    {
        BasePhysicsElement basePhysicsElement = Mockito.mock(BasePhysicsElement.class);
        Collider colliderA = Mockito.mock(Collider.class);
        Collider colliderB = Mockito.mock(Collider.class);
        List<Collider> colliders = new ArrayList<>();
        colliders.add(colliderA);
        colliders.add(colliderB);
        when(basePhysicsElement.getColliders()).thenReturn(colliders);
        PhysicsElement<Object> physicsElement = new PhysicsElement<>(null, null, 0, basePhysicsElement);

        List<CollisionEventArgs<Object>> eventArgsList = new ArrayList<>();
        BallPhysicsElement<Object> ballPhysicsElement = Mockito.mock(BallPhysicsElement.class);

        when(colliderA.checkCollision(eq(ballPhysicsElement), any())).thenReturn(false);
        when(colliderB.checkCollision(eq(ballPhysicsElement), any())).thenReturn(false);
        physicsElement.checkCollision(eventArgsList, ballPhysicsElement);
        assertThat(eventArgsList.size(), is(0));

        when(colliderA.checkCollision(eq(ballPhysicsElement), any())).thenReturn(false);
        when(colliderB.checkCollision(eq(ballPhysicsElement), any())).thenReturn(true);
        physicsElement.checkCollision(eventArgsList, ballPhysicsElement);
        assertThat(eventArgsList.size(), is(1));
        eventArgsList.clear();

        when(colliderA.checkCollision(eq(ballPhysicsElement), any())).thenReturn(true);
        when(colliderB.checkCollision(eq(ballPhysicsElement), any())).thenReturn(true);
        physicsElement.checkCollision(eventArgsList, ballPhysicsElement);
        assertThat(eventArgsList.size(), is(2));
    }

    @Test
    public void hasChanged() throws Exception
    {
        BasePhysicsElement basePhysicsElement = Mockito.mock(BasePhysicsElement.class);
        when(basePhysicsElement.getColliders()).thenReturn(Collections.emptyList());
        PhysicsElement physicsElement = new PhysicsElement<>(null, null, 0, basePhysicsElement);

        physicsElement.setPosition(new Vector2(1, 1));
        assertThat(physicsElement.hasChanged(), is(true));

        physicsElement.resetChanged();
        physicsElement.setRotation(10);
        assertThat(physicsElement.hasChanged(), is(true));

        physicsElement.resetChanged();
        assertThat(physicsElement.hasChanged(), is(false));
    }
}