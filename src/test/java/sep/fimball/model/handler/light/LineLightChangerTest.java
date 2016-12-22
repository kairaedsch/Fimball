package sep.fimball.model.handler.light;

import org.junit.Test;


/**
 * Tests für die Klasse PinballCanvasViewModel.
 */
public class LineLightChangerTest
{
    /**
     * Überprüft ob die Methode {@link LightChanger#determineLightStatusWithAnimation} grundsätzlich funktionieren könnte.
     */
    @Test(timeout = 1000)
    public void determineLightStatus()
    {
        LightChangeTester.test(new LineLightChanger(false, false), false);
        LightChangeTester.test(new LineLightChanger(true, false), false);
        LightChangeTester.test(new LineLightChanger(false, true), false);
        LightChangeTester.test(new LineLightChanger(true, true), false);
    }
}