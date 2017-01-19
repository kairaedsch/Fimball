package sep.fimball.model.handler.light;

import org.junit.Test;
import sep.fimball.general.data.Vector2;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Tests für die Klasse PinballCanvasViewModel.
 */
public class LightChangerTest
{
    /**
     * Überprüft die Korrektheit der Methode {@link LightChanger#determineLightStatusWithAnimation}.
     */
    @Test
    public void determineLightStatusWithAnimation()
    {
        // lightChanger mit keiner umgekehrten Animation
        {
            final long[] receivedDelta = new long[1];
            LightChanger lightChanger = new LightChanger(false)
            {
                @Override
                protected boolean determineLightStatus(Vector2 position, long delta)
                {
                    receivedDelta[0] = delta;
                    return false;
                }
            };

            lightChanger.determineLightStatusWithAnimation(new Vector2(), 2500);
            assertThat("Das Delta stimmt mit dem Übergebenen überein", receivedDelta[0], is(2500L));
        }

        // lightChanger mit umgekehrten Animation
        {
            final long[] recievedDelta = new long[1];
            LightChanger lightChanger = new LightChanger(true)
            {
                @Override
                protected boolean determineLightStatus(Vector2 position, long delta)
                {
                    recievedDelta[0] = delta;
                    return false;
                }
            };

            lightChanger.determineLightStatusWithAnimation(new Vector2(), 2500);
            assertThat("Das Delta ist gespiegelt", recievedDelta[0], is(7500L));
        }
    }
}