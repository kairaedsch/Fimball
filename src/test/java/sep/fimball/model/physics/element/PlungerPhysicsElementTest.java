package sep.fimball.model.physics.element;

import org.junit.Test;
import org.mockito.Mockito;

import java.util.Collections;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

/**
 * Tests für die Klasse PlungerPhysicsElement.
 */
public class PlungerPhysicsElementTest
{
    /**
     * Überprüft die Korrektheit der Methode {@link PlungerPhysicsElement#applyModify} und  {@link PlungerPhysicsElement#getStrength}.
     */
    @Test
    public void applyModify()
    {
        // Initialisiere PlungerPhysicsElement mit Mock
        BasePhysicsElement basePhysicsElement = Mockito.mock(BasePhysicsElement.class);
        when(basePhysicsElement.getColliders()).thenReturn(Collections.emptyList());
        PlungerPhysicsElement plungerPhysicsElement = new PlungerPhysicsElement<>(null, null, null, 0, 1, basePhysicsElement);

        // Setzte Stärke
        plungerPhysicsElement.applyModify(() -> 100.0);
        assertThat("Die Stärke ist 100", plungerPhysicsElement.getStrength(), is(100.0));

        // Setzte Stärke
        plungerPhysicsElement.applyModify(() -> 50.0);
        assertThat("Die Stärke ist 50", plungerPhysicsElement.getStrength(), is(50.0));
    }
}