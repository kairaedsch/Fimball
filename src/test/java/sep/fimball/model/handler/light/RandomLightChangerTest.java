package sep.fimball.model.handler.light;

import org.junit.Test;


/**
 * Tests für die Klasse PinballCanvasViewModel.
 */
public class RandomLightChangerTest
{
    /**
     * Überprüft ob die Methode {@link LightChanger#determineLightStatusWithAnimation} grundsätzlich funktionieren könnte.
     */
    @Test(timeout = 1000)
    public void determineLightStatus()
    {
        LightChangeTester.test(new RandomLightChanger(), true);
    }

}