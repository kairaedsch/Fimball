package sep.fimball.model.handler.light;

import org.junit.Test;
import sep.fimball.general.data.Vector2;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.junit.MatcherAssert.assertThat;

public class LightChangerTest
{
    long testDelta;

    @Test
    public void determineLightStatusWithAnimationTest() {
        TestLightChanger testReverted = new TestLightChanger(true);
        testReverted.determineLightStatusWithAnimation(null, 5);
        assertThat(testDelta, is((long) 95));

        TestLightChanger testNotReverted = new TestLightChanger(false);
        testNotReverted.determineLightStatusWithAnimation(null, 5);
        assertThat(testDelta, is((long) 5));
    }

    private class TestLightChanger extends LightChanger {

        boolean revertedAnimation;

        /**
         * Erstellt einen neuen LightChanger.
         *
         * @param revertedAnimation Gibt an, ob die Animation des Lichts rückwärts abgespielt werden soll.
         */
        TestLightChanger(boolean revertedAnimation)
        {
            super(revertedAnimation);
            this.revertedAnimation = revertedAnimation;
        }

        @Override
        protected boolean determineLightStatus(Vector2 position, long delta)
        {
            testDelta = delta;
            return false;
        }

        @Override
        long getDuration() {
            return 100;
        }
    }
}
