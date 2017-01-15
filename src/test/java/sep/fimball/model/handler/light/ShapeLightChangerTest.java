package sep.fimball.model.handler.light;

import javafx.beans.property.SimpleObjectProperty;
import org.junit.Test;
import sep.fimball.general.data.Vector2;

/**
 * Tests für die Klasse PinballCanvasViewModel.
 */
public class ShapeLightChangerTest
{
    /**
     * Überprüft ob die Methode {@link LightChanger#determineLightStatusWithAnimation} grundsätzlich funktionieren könnte.
     */
    @Test (timeout = 1000)
    public void determineLightStatus()
    {
        LightChangeTester.test(new ShapeLightChanger(false, new SimpleObjectProperty<>(new Vector2()), false), false);
        LightChangeTester.test(new ShapeLightChanger(true, new SimpleObjectProperty<>(new Vector2()), false), false);
        LightChangeTester.test(new ShapeLightChanger(false, new SimpleObjectProperty<>(new Vector2()), true), false);
        LightChangeTester.test(new ShapeLightChanger(true, new SimpleObjectProperty<>(new Vector2()), true), false);
    }
}