package sep.fimball.model.handler.light;

import org.junit.Test;
import sep.fimball.general.data.Vector2;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.junit.MatcherAssert.assertThat;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;

public class LineLightChangerTest
{
    private double xPos = 1.0;
    private double yPos = 1.0;

    @Test
    public void determineLightStatusTest() {
        LineLightChanger test = new LineLightChanger(true,true);

        Vector2 positionVector = mock(Vector2.class);
        doAnswer(invocationOnMock -> xPos).when(positionVector).getX();
        doAnswer(invocationOnMock -> yPos).when(positionVector).getY();
        assertThat(test.determineLightStatus(positionVector, 5), is(true));

        //TODO

    }
}
