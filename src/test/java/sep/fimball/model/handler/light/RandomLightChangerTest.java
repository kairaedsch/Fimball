package sep.fimball.model.handler.light;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.junit.MatcherAssert.assertThat;

public class RandomLightChangerTest
{
    @Test
    public void determineLightStatusTest() {
        RandomLightChanger test = new RandomLightChanger();

        boolean lightsOn = false;

        //TODO

        for (int i = 0; i < 10; ++i) {
            if(test.determineLightStatus(null, 0)) {
                lightsOn = true;
            }
        }
        assertThat(lightsOn, is(true));
    }
}
