package sep.fimball.model.handler.light;

import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import org.junit.Test;
import sep.fimball.general.data.Vector2;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.junit.MatcherAssert.assertThat;

public class FormLightChangerTest
{
    @Test
    public void determineLightStatusTestCircle() {
        Vector2 centerVector = new Vector2(0,0);
        ReadOnlyObjectProperty<Vector2> center = new SimpleObjectProperty<>(centerVector);
        FormLightChanger test = new FormLightChanger(true, center, true);

        Vector2 positionVector = new Vector2(1,1);


        assertThat(test.determineLightStatus(positionVector, 10), is(true));

    }

    @Test
    public void determineLightStatusTestSquare() {
        Vector2 centerVector = new Vector2(0,0);
        ReadOnlyObjectProperty<Vector2> center = new SimpleObjectProperty<>(centerVector);
        FormLightChanger test = new FormLightChanger(true, center, false);

        Vector2 positionVector = new Vector2(1,1);


        assertThat(test.determineLightStatus(positionVector, 10), is(true));

    }
}
