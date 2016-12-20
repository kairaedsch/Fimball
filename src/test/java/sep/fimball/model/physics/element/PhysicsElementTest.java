package sep.fimball.model.physics.element;

import org.junit.Test;
import org.mockito.Mockito;
import sep.fimball.general.data.Vector2;

import java.util.Collections;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.when;

public class PhysicsElementTest
{
    @Test
    public void checkCollision() throws Exception
    {
        BasePhysicsElement basePhysicsElement = Mockito.mock(BasePhysicsElement.class);
        when(basePhysicsElement.getColliders()).thenReturn(Collections.emptyList());
        PhysicsElement physicsElement = new PhysicsElement<>(null, null, 0, basePhysicsElement);

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