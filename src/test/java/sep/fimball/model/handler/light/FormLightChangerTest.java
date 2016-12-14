package sep.fimball.model.handler.light;

import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import org.junit.Test;
import sep.fimball.general.data.Vector2;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.junit.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class FormLightChangerTest
{

    //TODO

    double xPos = 1;
    double yPos = 1;

    @Test
    public void determineLightStatusTestCircle() {
        Vector2 centerVector = mock(Vector2.class);
        when(centerVector.getX()).thenReturn(0.0);
        when(centerVector.getY()).thenReturn(0.0);
        ReadOnlyObjectProperty<Vector2> center = new SimpleObjectProperty<>(centerVector);
        FormLightChanger test = new FormLightChanger(true, center, true);

        Vector2 positionVector = mock(Vector2.class);
        doAnswer(invocationOnMock -> xPos).when(positionVector).getX();
        doAnswer(invocationOnMock -> yPos).when(positionVector).getY();
        doAnswer(invocationOnMock ->
        {
            Vector2 arg = invocationOnMock.getArgument(0);
            xPos -= arg.getX();
            yPos -= arg.getY();
            return positionVector;
        }).when(positionVector).minus(any(Vector2.class));


        assertThat(test.determineLightStatus(positionVector, 10), is(true));

    }

    @Test
    public void determineLightStatusTestSquare() {
        Vector2 centerVector = mock(Vector2.class);
        when(centerVector.getX()).thenReturn(0.0);
        when(centerVector.getY()).thenReturn(0.0);
        ReadOnlyObjectProperty<Vector2> center = new SimpleObjectProperty<>(centerVector);
        FormLightChanger test = new FormLightChanger(true, center, false);

        Vector2 positionVector = mock(Vector2.class);
        doAnswer(invocationOnMock -> xPos).when(positionVector).getX();
        doAnswer(invocationOnMock -> yPos).when(positionVector).getY();
        doAnswer(invocationOnMock ->
        {
            Vector2 arg = invocationOnMock.getArgument(0);
            xPos -= arg.getX();
            yPos -= arg.getY();
            return positionVector;
        }).when(positionVector).minus(any(Vector2.class));


        assertThat(test.determineLightStatus(positionVector, 10), is(true));

    }
}
