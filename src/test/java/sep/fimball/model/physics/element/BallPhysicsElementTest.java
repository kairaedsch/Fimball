package sep.fimball.model.physics.element;

import org.junit.Test;
import org.mockito.Mockito;
import sep.fimball.general.data.Vector2;
import sep.fimball.model.physics.collider.WorldLayer;

import java.util.Collections;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.number.OrderingComparison.greaterThan;
import static org.hamcrest.number.OrderingComparison.lessThan;
import static org.junit.Assert.assertThat;

/**
 * Tests für die Klasse BallPhysicsElement.
 */
public class BallPhysicsElementTest
{
    /**
     * Erstellt ein BallPhysicsElement mit Mocks.
     *
     * @return Ein BallPhysicsElement mit Mocks
     */
    private BallPhysicsElement getBallPhysicsElementWithMock()
    {
        BasePhysicsElement basePhysicsElement = Mockito.mock(BasePhysicsElement.class);
        Mockito.when(basePhysicsElement.getColliders()).thenReturn(Collections.emptyList());

        return new BallPhysicsElement<>(null, null, new Vector2(), 0, 1, basePhysicsElement);
    }

    /**
     * Überprüft die Korrektheit der Methode {@link BallPhysicsElement#update}.
     */
    @Test
    public void update()
    {
        BallPhysicsElement ballPhysicsElement = getBallPhysicsElementWithMock();

        // Setzte Höhe und Geschwindigkeit
        ballPhysicsElement.setHeight(2);
        ballPhysicsElement.setVelocity(new Vector2(0.001, 0));

        // Wende die Zeit an
        ballPhysicsElement.update(0.016);

        assertThat("Der Ball ist nach unten gefallen", ballPhysicsElement.getHeight(), is(lessThan(2.0)));
        assertThat("Die Geschwindigkeit in X-Richtung ist konstant geblieben", ballPhysicsElement.getVelocity().getX(), is(0.001));
        assertThat("Die Geschwindigkeit in Y-Richtung hat durch die Schwerkraft zugenommen", ballPhysicsElement.getVelocity().getY(), is(greaterThan(0.0)));
    }

    /**
     * Überprüft die Korrektheit der Methode {@link BallPhysicsElement#setVelocity}.
     */
    @Test
    public void setVelocity()
    {
        Vector2 tooFast = new Vector2(10000, 1000);
        BallPhysicsElement ballPhysicsElement = getBallPhysicsElementWithMock();

        // Setzte die Geschwindigkeit des Balles auf eine zu schnelle
        ballPhysicsElement.setVelocity(tooFast);

        assertThat("Ball wird gebremst da er zu schnell ist", ballPhysicsElement.getVelocity().magnitude(), is(lessThan(tooFast.magnitude())));
    }

    /**
     * Überprüft die Korrektheit der Methode {@link BallPhysicsElement#setHeight}.
     */
    @Test
    public void setHeight()
    {
        BallPhysicsElement ballPhysicsElement = getBallPhysicsElementWithMock();

        ballPhysicsElement.setHeight(-1);
        assertThat("Der Ball ist am Boden", ballPhysicsElement.getHeight(), is(0.0));
        assertThat("Der Ball ist am Boden", ballPhysicsElement.getLayer(), is(WorldLayer.GROUND));

        ballPhysicsElement.setHeight(0);
        assertThat("Der Ball ist am Boden", ballPhysicsElement.getHeight(), is(0.0));
        assertThat("Der Ball ist am Boden", ballPhysicsElement.getLayer(), is(WorldLayer.GROUND));

        ballPhysicsElement.setHeight(0.5);
        assertThat("Der Ball ist auf der gesetzten Höhe", ballPhysicsElement.getHeight(), is(0.5));
        assertThat("Der Ball ist am Boden", ballPhysicsElement.getLayer(), is(WorldLayer.GROUND));

        ballPhysicsElement.setHeight(1.5);
        assertThat("Der Ball ist auf der gesetzten Höhe", ballPhysicsElement.getHeight(), is(1.5));
        assertThat("Der Ball ist auf der Rampe", ballPhysicsElement.getLayer(), is(WorldLayer.RAMP));

        ballPhysicsElement.setHeight(3);
        assertThat("Der Ball ist auf der maximalen Höhe", ballPhysicsElement.getHeight(), is(2.0));
        assertThat("Der Ball ist auf der Rampe", ballPhysicsElement.getLayer(), is(WorldLayer.RAMP));
    }

    /**
     * Überprüft die Korrektheit der Methode {@link BallPhysicsElement#applyModify}.
     */
    @Test
    public void applyModify()
    {
        // ResetModify
        {
            BallPhysicsElement ballPhysicsElement = getBallPhysicsElementWithMock();

            // Setzte Höhe und Geschwindigkeit
            ballPhysicsElement.setHeight(2);
            ballPhysicsElement.setVelocity(new Vector2(0.001, 0));

            // Setzte den Ball zurück
            ballPhysicsElement.applyModify((BallResetModify) () -> new Vector2(5, 5));

            assertThat("Der Ball wurde versetzt", ballPhysicsElement.getPosition().getX(), is(5.0));
            assertThat("Der Ball wurde versetzt", ballPhysicsElement.getPosition().getY(), is(5.0));
            assertThat("Die Geschwindigkeit wurde zurückgesetzt", ballPhysicsElement.getVelocity().magnitude(), is(0.0));
        }

        // NudgeModify
        {
            BallPhysicsElement ballPhysicsElement = getBallPhysicsElementWithMock();

            // Setzte Höhe und Geschwindigkeit
            ballPhysicsElement.setHeight(2);
            ballPhysicsElement.setVelocity(new Vector2(0, 0));

            // Rüttle
            ballPhysicsElement.applyModify((BallNudgeModify) () -> true);

            assertThat("Der Ball wurde auf der X-Achse negativ beschleunigt", ballPhysicsElement.getVelocity().getX(), is(lessThan(0.0)));
            assertThat("Der Ball wurde auf der Y-Achse nicht beschleunigt", ballPhysicsElement.getVelocity().getY(), is(0.0));
        }
    }
}