package sep.fimball.general.data;

import org.junit.Test;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * Created by alexcekay on 30.11.16.
 */
public class ConfigTest
{
    /**
     * ID eines Test Elements.
     */
    private final String testElementName = "elementOne";

    /**
     * ID einer Test Pinballmachine.
     */
    private final String testMachineId = "machineOne";

    /**
     * Das erwartete Pfadende einer Sprachbeschreibung.
     */
    private final String expectedLanguagePathEnding = "bundles/fimball_de.properties";

    /**
     * Das erwartete Pfadende einer Sounddatei.
     */
    private final String expectedSoundPathEnding = "/sounds/game.mp3";

    /**
     * Das erwartete Pfadende eines Bilds, welches die obere Ebene darstellt.
     */
    private final String expectedTopEnding = "/elementOne/top.png";

    /**
     * Das erwartete Pfadende eines Bilds, welches die untere Ebene darstellt.
     */
    private final String expectedBottomEnding = "/elementOne/bottom.png";

    /**
     * Das erwartete Pfadende eines Bilds, welches die obere Ebene und die Drehung um 90 Grad darstellt.
     */
    private final String expectedTopRotatedEnding = "/elementOne/top-90.png";

    /**
     * Das erwartete Pfadende eines Bilds, welches die untere Ebene und die Drehung um 270 Grad darstellt.
     */
    private final String expectedBottomRotatedEnding = "/elementOne/bottom-270.png";

    /**
     * Das erwartete Pfadende eines Bilds, welches die obere Ebene des ersten (0. Index) Frame einer "glow" Animation darstellt.
     */
    private final String expectedTopAnimationEnding = "/elementOne/top+glow_0.png";

    /**
     * Das erwartete Pfadende eines Bilds, welches die untere Ebene des zweiten (1. Index) Frame einer "glow" Animation darstellt.
     */
    private final String expectedBottomAnimationEnding = "/elementOne/bottom+glow_1.png";

    /**
     * Das erwartete Pfadende eines Bilds, welches die obere Ebene des ersten (0. Index) Frame einer "spinn" Animation, welche um 0 Grad gedreht ist darstellt.
     */
    private final String expectedTopRotatedAnimationEnding = "/elementOne/top-0+spinn_0.png";

    /**
     * Das erwartete Pfadende eines Bilds, welches die untere Ebene des sechsten (5. Index) Frame einer "spinn" Animation, welche um 90 Grad gedreht ist darstellt.
     */
    private final String expectedBottomRotatedAnimationEnding = "/elementOne/bottom-90+spinn_5.png";

    /**
     * Überprüft, ob das Ende des Pfades, auf dem sich die Spielelemente befinden korrekt ist.
     */
    @Test
    public void testPathToElements()
    {
        String path = Config.pathToElements();
        assertThat(path.endsWith("/elements"), is(true));
    }

    /**
     * Überprüft, ob das Ende des Pfades, auf dem sich das Spielelement elementOne befindet korrekt ist.
     */
    @Test
    public void testPathToElement()
    {
        String path = Config.pathToElement(testElementName);
        assertThat(path.endsWith("/elements/" + testElementName), is(true));
    }

    /**
     * Überprüft, ob das Ende des Pfades, auf dem sich die Beschreibung des Spielelements elementOne befindet, korrekt ist.
     */
    @Test
    public void testPathToElementDataJson()
    {
        String path = Config.pathToElementDataJson(testElementName);
        assertThat(path.endsWith("/elements/" + testElementName + "/data.json"), is(true));
    }

    /**
     * Überprüft, ob das Ende des Pfades, auf dem sich die gespeicherten Flipperautomaten befinden korrekt ist.
     */
    @Test
    public void testPathToPinballMachines()
    {
        String path = Config.pathToPinballMachines();
        assertThat(path.endsWith("/machines"), is(true));
    }

    /**
     * Überprüft, ob das Ende des Pfades, auf dem sich der gespeicherte Flipperautomat machineOne befindet, korrekt ist.
     */
    @Test
    public void testPathToPinballMachine()
    {
        String path = Config.pathToPinballMachine(testMachineId);
        assertThat(path.endsWith("/machines/" + testMachineId), is(true));
    }

    /**
     * Überprüft, ob das Ende des Pfades, auf dem sich das Vorschaubild des gespeicherten Flipperautomaten machineOne befindet, korrekt ist.
     */
    @Test
    public void testPathToPinballMachinePreviewImage()
    {
        String path = Config.pathToPinballMachineImagePreview(testMachineId);
        assertThat(path.endsWith("/machines/" + testMachineId + "/preview.png"), is(true));
    }

    /**
     * Überprüft, ob das Ende des Pfades, auf dem sich die generelle Beschreibung des gespeicherten Flipperautomaten machineOne befindet, korrekt ist.
     */
    @Test
    public void testPathToPinballMachineGeneralData()
    {
        String path = Config.pathToPinballMachineGeneralJson(testMachineId);
        assertThat(path.endsWith("/machines/" + testMachineId + "/general.json"), is(true));
    }

    /**
     * Überprüft, ob das Ende des Pfades, auf dem sich die Beschreibung der Spielelemente des gespeicherten Flipperautomaten machineOne befindet, korrekt ist.
     */
    @Test
    public void testPathToPinballMachineData()
    {
        String path = Config.pathToPinballMachinePlacedElementsJson(testMachineId);
        assertThat(path.endsWith("/machines/" + testMachineId + "/elements.json"), is(true));
    }

    /**
     * Überprüft, ob das Ende des Pfades, auf dem sich die Beschreibung der Spieleinstellungen befindet, korrekt ist.
     */
    @Test
    public void testPathToSettings()
    {
        String path = Config.pathToSettings();
        assertThat(path.endsWith("/settings.json"), is(true));
    }

    /**
     * Überprüft, ob der Pfad der Sprachbeschreibung der Sprache "de" gleich dem erwarteten ist.
     */
    @Test
    public void testPathToLanguage()
    {
        String path = Config.pathToLanguage("de");
        assertThat(path, is(expectedLanguagePathEnding));
    }

    /**
     * Überprüft, ob das Ende des Pfades, auf dem sich das Spiellogo befindet, korrekt ist.
     */
    @Test
    public void testPathToLogo()
    {
        String path = Config.pathToLogo();
        assertThat(path.endsWith("/logo.png"), is(true));
    }

    /**
     * Überprüft, ob das Ende des Pfades, auf dem sich Daten zu Tests befinden, korrekt ist.
     */
    @Test
    public void testPathToTestData()
    {
        String path = Config.pathToTestData();
        assertThat(path.endsWith("/testdata/"), is(true));
    }

    /**
     * Überprüft, ob der Pfad der Sounddatei "game" gleich dem erwarteten Pfad ist.
     */
    @Test
    public void testPathToSound()
    {
        String path = Config.pathToSound("game");
        assertThat(path.endsWith(expectedSoundPathEnding), is(true));
    }

    /**
     * Überprüft, ob die Pfade zu verschiedenen Bildern von Spielelementen gleich den erwarteten Pfaden ist.
     */
    @Test
    public void testPathToElementImage()
    {
        String topPath = Config.pathToElementImage(testElementName, ImageLayer.TOP, false, 0, false, "", 0);
        String bottomPath = Config.pathToElementImage(testElementName, ImageLayer.BOTTOM, false, 0, false, "", 0);
        String topRotated = Config.pathToElementImage(testElementName, ImageLayer.TOP, true, 90, false, "", 0);
        String bottomRotated = Config.pathToElementImage(testElementName, ImageLayer.BOTTOM, true, 270, false, "", 0);
        String topAnimation = Config.pathToElementImage(testElementName, ImageLayer.TOP, false, 0, true, "glow", 0);
        String bottomAnimation = Config.pathToElementImage(testElementName, ImageLayer.BOTTOM, false, 0, true, "glow", 1);
        String topAnimationRotated = Config.pathToElementImage(testElementName, ImageLayer.TOP, true, 0, true, "spinn", 0);
        String bottomAnimationRotated = Config.pathToElementImage(testElementName, ImageLayer.BOTTOM, true, 90, true, "spinn", 5);
        assertThat(topPath.endsWith(expectedTopEnding), is(true));
        assertThat(bottomPath.endsWith(expectedBottomEnding), is(true));
        assertThat(topRotated.endsWith(expectedTopRotatedEnding), is(true));
        assertThat(bottomRotated.endsWith(expectedBottomRotatedEnding), is(true));
        assertThat(topAnimation.endsWith(expectedTopAnimationEnding), is(true));
        assertThat(bottomAnimation.endsWith(expectedBottomAnimationEnding), is(true));
        assertThat(topAnimationRotated.endsWith(expectedTopRotatedAnimationEnding), is(true));
        assertThat(bottomAnimationRotated.endsWith(expectedBottomRotatedAnimationEnding), is(true));
    }
}
