package sep.fimball.model.physics.element;

import org.junit.Test;
import org.mockito.Mockito;

import java.util.Collections;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

public class PlungerPhysicsElementTest
{
    @Test
    public void applyModify() throws Exception
    {
        BasePhysicsElement basePhysicsElement = Mockito.mock(BasePhysicsElement.class);
        when(basePhysicsElement.getColliders()).thenReturn(Collections.emptyList());
        PlungerPhysicsElement plungerPhysicsElement = new PlungerPhysicsElement<>(null, null, null, 0, basePhysicsElement);

        plungerPhysicsElement.applyModify(() -> 100.0);
        assertThat(plungerPhysicsElement.getStrength(), is(100.0));

        plungerPhysicsElement.applyModify(() -> 50.0);
        assertThat(plungerPhysicsElement.getStrength(), is(50.0));
    }
}