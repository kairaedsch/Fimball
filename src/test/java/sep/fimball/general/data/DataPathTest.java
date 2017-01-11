package sep.fimball.general.data;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * Tests für die Klasse Config. Bei diesen Tests ist zu beachten dass immer nur der Pfad relativ zur JAR-Datei getestet wird.
 * Dies hat den Grund, dass zum korrekten Testen der Funktion welche herausfindet wo sich die JAR-Datei befindet dieselbe Funktion
 * genutzt werden müsste, was den Test überflüssig machen würde. Die Alternative den Pfad zur JAR fest zu setzen wurde absichtlich nicht
 * genutzt, da dadurch die Tests auf anderen Maschinen sowie dem Jenkins Server nicht mehr funktionieren würden ohne den Pfad bei jeder
 * Maschine zu ändern.
 */
public class DataPathTest
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
     * Testet ob das Konvertieren eines Pfads in eine URL korrekt funktioniert.
     */
    @Test
    public void testPathToUrlEscaping()
    {
        String escapedPath = DataPath.escapePathToUrl("/home/te st/test.png");
        assertThat("'/home/te st/test.png' wird escaped zu: '/home/te%20st/test.png'", escapedPath.endsWith("/home/te%20st/test.png"), is(true));
    }

    /**
     * Überprüft, ob das Ende des Pfades, auf dem sich die Spielelemente befinden korrekt ist.
     */
    @Test
    public void testPathToElements()
    {
        String path = DataPath.pathToElements();
        assertThat(path.endsWith("/elements"), is(true));
    }

    /**
     * Überprüft, ob das Ende des Pfades, auf dem sich das Spielelement elementOne befindet korrekt ist.
     */
    @Test
    public void testPathToElement()
    {
        String path = DataPath.pathToElement(testElementName);
        assertThat(path.endsWith("/elements/" + testElementName), is(true));
    }

    /**
     * Überprüft, ob das Ende des Pfades, auf dem sich die Beschreibung des Spielelements elementOne befindet, korrekt ist.
     */
    @Test
    public void testPathToElementDataJson()
    {
        String path = DataPath.pathToElementDataJson(testElementName);
        assertThat(path.endsWith("/elements/" + testElementName + "/data.json"), is(true));
    }

    /**
     * Überprüft, ob das Ende des Pfades, auf dem sich die gespeicherten Flipperautomaten befinden korrekt ist.
     */
    @Test
    public void testPathToPinballMachines()
    {
        String path = DataPath.pathToPinballMachines();
        assertThat(path.endsWith("/machines"), is(true));
    }

    /**
     * Überprüft, ob das Ende des Pfades, auf dem sich der gespeicherte Flipperautomat machineOne befindet, korrekt ist.
     */
    @Test
    public void testPathToPinballMachine()
    {
        String path = DataPath.pathToPinballMachine(testMachineId);
        assertThat(path.endsWith("/machines/" + testMachineId), is(true));
    }

    /**
     * Überprüft, ob das Ende des Pfades, auf dem sich die generelle Beschreibung des gespeicherten Flipperautomaten machineOne befindet, korrekt ist.
     */
    @Test
    public void testPathToPinballMachineGeneralData()
    {
        String path = DataPath.pathToPinballMachineGeneralJson(testMachineId);
        assertThat(path.endsWith("/machines/" + testMachineId + "/general.json"), is(true));
    }

    /**
     * Überprüft, ob das Ende des Pfades, auf dem sich die Beschreibung der Spielelemente des gespeicherten Flipperautomaten machineOne befindet, korrekt ist.
     */
    @Test
    public void testPathToPinballMachineData()
    {
        String path = DataPath.pathToPinballMachinePlacedElementsJson(testMachineId);
        assertThat(path.endsWith("/machines/" + testMachineId + "/elements.json"), is(true));
    }

    /**
     * Überprüft, ob das Ende des Pfades, auf dem sich die Beschreibung der Spieleinstellungen befindet, korrekt ist.
     */
    @Test
    public void testPathToSettings()
    {
        String path = DataPath.pathToSettings();
        assertThat(path.endsWith("/settings.json"), is(true));
    }

    /**
     * Überprüft, ob der Pfad der Sprachbeschreibung der Sprache "de" gleich dem erwarteten ist.
     */
    @Test
    public void testPathToLanguage()
    {
        String path = DataPath.pathToLanguage("de");
        // Das erwartete Pfadende einer Sprachbeschreibung.
        String expectedLanguagePathEnding = "bundles/fimball_de.properties";
        assertThat(path, is(expectedLanguagePathEnding));
    }

    /**
     * Überprüft, ob das Ende des Pfades, auf dem sich das Spiellogo befindet, korrekt ist.
     */
    @Test
    public void testPathToLogo()
    {
        String path = DataPath.pathToLogo();
        assertThat(path.endsWith("/logo.png"), is(true));
    }

    /**
     * Überprüft, ob das Ende des Pfades, auf dem sich Daten zu Tests befinden, korrekt ist.
     */
    @Test
    public void testPathToTestData()
    {
        String path = DataPath.pathToTestData();
        assertThat(path.endsWith("/testdata/"), is(true));
    }

    /**
     * Überprüft, ob der Pfad der Sounddatei "game" gleich dem erwarteten Pfad ist.
     */
    @Test
    public void testPathToSound()
    {
        String path = DataPath.pathToSound("game");
        // Das erwartete Pfadende einer Sounddatei.
        String expectedSoundPathEnding = "/sounds/game.mp3";
        assertThat(path.endsWith(expectedSoundPathEnding), is(true));
    }

    /**
     * Überprüft, ob der Pfad zum Preview Bild korrekt ist.
     */
    @Test
    public void testPathToDefaultImage()
    {
        String path = DataPath.pathToDefaultPreview();
        assertThat(path.endsWith("defaultPreview.png"), is(true));
    }

    /**
     * Überprüft ob der Pfad zu einem neu generierten Preview Bild korrekt ist.
     */
    @Test
    public void testPathToGeneratedImage()
    {
        String path = DataPath.generatePathToNewImagePreview(testMachineId, "12345");
        assertThat(path.endsWith("/machines/" + testMachineId + "/preview12345.png"), is(true));
    }

    /**
     * Überprüft, ob die Pfade zu verschiedenen Bildern von Spielelementen gleich den erwarteten Pfaden ist.
     */
    @Test
    public void testPathToElementImage()
    {
        String topPath = DataPath.pathToElementImage(testElementName, ImageLayer.TOP, false, 0, false, "", 0);
        String bottomPath = DataPath.pathToElementImage(testElementName, ImageLayer.BOTTOM, false, 0, false, "", 0);
        String topRotated = DataPath.pathToElementImage(testElementName, ImageLayer.TOP, true, 90, false, "", 0);
        String bottomRotated = DataPath.pathToElementImage(testElementName, ImageLayer.BOTTOM, true, 270, false, "", 0);
        String topAnimation = DataPath.pathToElementImage(testElementName, ImageLayer.TOP, false, 0, true, "glow", 0);
        String bottomAnimation = DataPath.pathToElementImage(testElementName, ImageLayer.BOTTOM, false, 0, true, "glow", 1);
        String topAnimationRotated = DataPath.pathToElementImage(testElementName, ImageLayer.TOP, true, 0, true, "spinn", 0);
        String bottomAnimationRotated = DataPath.pathToElementImage(testElementName, ImageLayer.BOTTOM, true, 90, true, "spinn", 5);
        // Das erwartete Pfadende eines Bilds, welches die obere Ebene darstellt.
        String expectedTopEnding = "/elementOne/top.png";
        assertThat(topPath.endsWith(expectedTopEnding), is(true));
        // Das erwartete Pfadende eines Bilds, welches die untere Ebene darstellt.
        String expectedBottomEnding = "/elementOne/bottom.png";
        assertThat(bottomPath.endsWith(expectedBottomEnding), is(true));
        // Das erwartete Pfadende eines Bilds, welches die obere Ebene und die Drehung um 90 Grad darstellt.
        String expectedTopRotatedEnding = "/elementOne/top-90.png";
        assertThat(topRotated.endsWith(expectedTopRotatedEnding), is(true));
        // Das erwartete Pfadende eines Bilds, welches die untere Ebene und die Drehung um 270 Grad darstellt.
        String expectedBottomRotatedEnding = "/elementOne/bottom-270.png";
        assertThat(bottomRotated.endsWith(expectedBottomRotatedEnding), is(true));
        // Das erwartete Pfadende eines Bilds, welches die obere Ebene des ersten (0. Index) Frame einer "glow" Animation darstellt.
        String expectedTopAnimationEnding = "/elementOne/top+glow_0.png";
        assertThat(topAnimation.endsWith(expectedTopAnimationEnding), is(true));
        // Das erwartete Pfadende eines Bilds, welches die untere Ebene des zweiten (1. Index) Frame einer "glow" Animation darstellt.
        String expectedBottomAnimationEnding = "/elementOne/bottom+glow_1.png";
        assertThat(bottomAnimation.endsWith(expectedBottomAnimationEnding), is(true));
        // Das erwartete Pfadende eines Bilds, welches die obere Ebene des ersten (0. Index) Frame einer "spinn" Animation, welche um 0 Grad gedreht ist darstellt.
        String expectedTopRotatedAnimationEnding = "/elementOne/top-0+spinn_0.png";
        assertThat(topAnimationRotated.endsWith(expectedTopRotatedAnimationEnding), is(true));
        // Das erwartete Pfadende eines Bilds, welches die untere Ebene des sechsten (5. Index) Frame einer "spinn" Animation, welche um 90 Grad gedreht ist darstellt.
        String expectedBottomRotatedAnimationEnding = "/elementOne/bottom-90+spinn_5.png";
        assertThat(bottomAnimationRotated.endsWith(expectedBottomRotatedAnimationEnding), is(true));
    }
}
