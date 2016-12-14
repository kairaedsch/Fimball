package sep.fimball.model.handler.light;

import org.junit.Test;
import sep.fimball.general.data.Vector2;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.junit.MatcherAssert.assertThat;

public class LineLightChangerTest
{
    @Test
    public void determineLightStatusTest() {
        LineLightChanger test = new LineLightChanger(true,true);

        Vector2 positionVector = new Vector2(1,1);
        assertThat(test.determineLightStatus(positionVector, 5), is(true));

        //TODO

    }
}
