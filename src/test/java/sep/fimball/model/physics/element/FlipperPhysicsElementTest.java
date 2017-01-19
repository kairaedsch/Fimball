package sep.fimball.model.physics.element;

import org.junit.Test;
import org.mockito.Mockito;

import java.util.Collections;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

/**
 * Tests für die Klasse FlipperPhysicsElement.
 */
public class FlipperPhysicsElementTest
{
    /**
     * Erstellt ein FlipperPhysicsElement mit Mock.
     *
     * @param isLeft Ob es ein linker Flipperarm werden soll.
     * @return Ein FlipperPhysicsElement mit Mock.
     */
    private FlipperPhysicsElement getFlipperPhysicsElementWithMock(boolean isLeft)
    {
        BasePhysicsElement basePhysicsElement = Mockito.mock(BasePhysicsElement.class);
        when(basePhysicsElement.getColliders()).thenReturn(Collections.emptyList());
        return new FlipperPhysicsElement<>(null, null, null, 1, basePhysicsElement, isLeft);
    }

    /**
     * Überprüft die Korrektheit der Methode {@link FlipperPhysicsElement#applyModify}.
     */
    @Test
    public void applyModify()
    {
        // Teste den linke Flipperarm
        {
            FlipperPhysicsElement flipperPhysicsElementLeft = getFlipperPhysicsElementWithMock(true);

            // Versuche den Flipperarm nach oben zu drehen
            flipperPhysicsElementLeft.applyModify(() -> AngularDirection.UP);
            assertThat("Das Flipperarm bewegt sich nach oben", flipperPhysicsElementLeft.getAngularVelocity(), is(-1000.0));

            // Drehe den Flipperarm um ein kleines Stück.
            flipperPhysicsElementLeft.update(0.05);

            // Versuche den Flipperarm nach unten zu drehen
            flipperPhysicsElementLeft.applyModify(() -> AngularDirection.DOWN);
            assertThat("Das Flipperarm bewegt sich nach unten", flipperPhysicsElementLeft.getAngularVelocity(), is(1000.0));
        }

        // Teste den rechten Flipperarm
        {
            FlipperPhysicsElement flipperPhysicsElementRight = getFlipperPhysicsElementWithMock(false);

            // Versuche den Flipperarm nach oben zu drehen
            flipperPhysicsElementRight.applyModify(() -> AngularDirection.UP);
            assertThat("Das Flipperarm bewegt sich nach oben", flipperPhysicsElementRight.getAngularVelocity(), is(1000.0));

            // Drehe den Flipperarm um ein kleines Stück.
            flipperPhysicsElementRight.update(0.05);

            // Versuche den Flipperarm nach unten zu drehen
            flipperPhysicsElementRight.applyModify(() -> AngularDirection.DOWN);
            assertThat("Das Flipperarm bewegt sich nach unten", flipperPhysicsElementRight.getAngularVelocity(), is(-1000.0));

            // Drehe den Flipperarm um ein kleines Stück.
            flipperPhysicsElementRight.update(0.05);

            // Versuche den Flipperarm nach unten zu drehen
            flipperPhysicsElementRight.applyModify(() -> AngularDirection.DOWN);
            assertThat("Das Flipperarm bewegt sich nicht", flipperPhysicsElementRight.getAngularVelocity(), is(0.0));
        }
    }

    /**
     * Überprüft die Korrektheit der Methode {@link FlipperPhysicsElement#update}.
     */
    @Test
    public void update()
    {
        // Teste den linke Flipperarm
        {
            FlipperPhysicsElement flipperPhysicsElementLeft = getFlipperPhysicsElementWithMock(true);

            assertThat("Der Flipperarm ist in der Ausgangsrotation", flipperPhysicsElementLeft.getRotation(), is(20.0));

            // Versuche den Flipperarm nach oben zu drehen
            flipperPhysicsElementLeft.applyModify(() -> AngularDirection.UP);
            flipperPhysicsElementLeft.update(0.01);
            assertThat("Der Flipperarm hat sich nach oben gedreht", flipperPhysicsElementLeft.getRotation(), is(10.0));

            // Versuche den Flipperarm nach unten zu drehen
            flipperPhysicsElementLeft.applyModify(() -> AngularDirection.DOWN);
            flipperPhysicsElementLeft.update(0.01);
            assertThat("Der Flipperarm hat sich zurück gedreht", flipperPhysicsElementLeft.getRotation(), is(20.0));
        }

        // Teste den rechten Flipperarm
        {
            FlipperPhysicsElement flipperPhysicsElementRight = getFlipperPhysicsElementWithMock(false);

            assertThat("Der Flipperarm ist in der Ausgangsrotation", flipperPhysicsElementRight.getRotation(), is(-20.0));

            // Versuche den Flipperarm nach oben zu drehen
            flipperPhysicsElementRight.applyModify(() -> AngularDirection.UP);
            flipperPhysicsElementRight.update(0.01);
            assertThat("Der Flipperarm hat sich nach oben gedreht", flipperPhysicsElementRight.getRotation(), is(-10.0));

            // Versuche den Flipperarm nach unten zu drehen
            flipperPhysicsElementRight.applyModify(() -> AngularDirection.DOWN);
            flipperPhysicsElementRight.update(0.01);
            assertThat("Der Flipperarm hat sich zurück gedreht", flipperPhysicsElementRight.getRotation(), is(-20.0));
        }
    }
}