package sep.fimball.model.handler.light;

import sep.fimball.general.data.Vector2;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Dient zum Testen von Unterklassen von LightChanger. Die Unterklassen von LightChanger werden dabei nur sehr rudimentär getestet,
 * da der Lichtereffekt für das Funktionieren des Programms keine Bedeutung hat. Solange die LightChanger die Lichter ein bisschen blinken lassen ist alles in Ordnung.
 */
public class LightChangeTester
{
    /**
     * Testet einen lightChanger.
     *
     * @param lightChanger Der zu testende LightChanger.
     * @param isRandom     Gibt an, ob der LightChanger bei gleichen Parametern verschiedene Ergebnisse liefern darf.
     */
    public static void test(LightChanger lightChanger, boolean isRandom)
    {
        testChangeWithDifferentParameters(lightChanger);
        if (!isRandom) testNoChangeWithSameParameter(lightChanger);
    }

    /**
     * Testet ob sich der Rückgabewert bei verschiedenen Parametern ändert.
     *
     * @param lightChanger Der zu testende LightChanger.
     */
    private static void testNoChangeWithSameParameter(LightChanger lightChanger)
    {
        for (int i = 0; i < 10; i++)
        {
            Vector2 position = new Vector2(Math.random(), Math.random());
            long delta = (long) (Math.random() * lightChanger.getDuration());
            boolean firstResult = lightChanger.determineLightStatus(position, delta);

            for (int ii = 0; ii < 10; ii++)
            {
                assertThat(lightChanger.determineLightStatus(position, delta), is(firstResult));
            }
        }
    }

    /**
     * Testet ob sich der Rückgabewert bei gleichen Parametern nicht ändert.
     *
     * @param lightChanger Der zu testende LightChanger.
     */
    private static void testChangeWithDifferentParameters(LightChanger lightChanger)
    {
        boolean defaultLight = lightChanger.determineLightStatus(new Vector2(0, 0), 0);
        while (defaultLight == lightChanger.determineLightStatus(new Vector2(Math.random(), Math.random()), (long) (Math.random() * lightChanger.getDuration()))) ;
    }
}
