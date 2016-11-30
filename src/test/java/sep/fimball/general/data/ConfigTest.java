package sep.fimball.general.data;

import org.junit.Before;
import org.junit.Test;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * Created by alexcekay on 30.11.16.
 */
public class ConfigTest
{
    private final String testElementName = "elementOne";
    private final String testMachineId = "machineOne";
    private final String expectedLanguagePath = "bundles/fimball_de.properties";
    private final String expectedSoundPathEnding = "/sounds/game.mp3";
    private final String expectedTopEnding = "/elementOne/top.png";
    private final String expectedBottomEnding = "/elementOne/bottom.png";
    private final String expectedTopRotatedEnding = "/elementOne/top-90.png";
    private final String expectedBottomRotatedEnding = "/elementOne/bottom-270.png";
    private final String expectedTopAnimationEnding = "/elementOne/top+glow_0.png";
    private final String expectedBottomAnimationEnding = "/elementOne/bottom+glow_1.png";
    private final String expectedTopRotatedAnimationEnding = "/elementOne/top-0+spinn_0.png";
    private final String expectedBottomRotatedAnimationEnding = "/elementOne/bottom-90+spinn_5.png";

    @Test
    public void testPathToElements()
    {
        String path = Config.pathToElements();
        assertThat(path.endsWith("/elements"), is(true));
    }

    @Test
    public void testPathToElement()
    {
        String path = Config.pathToElement(testElementName);
        assertThat(path.endsWith("/elements/" + testElementName), is(true));
    }

    @Test
    public void testPathToElementDataJson()
    {
        String path = Config.pathToElementDataJson(testElementName);
        assertThat(path.endsWith("/elements/" + testElementName + "/data.json"), is(true));
    }

    @Test
    public void testPathToPinballMachines()
    {
        String path = Config.pathToPinballMachines();
        assertThat(path.endsWith("/machines"), is(true));
    }

    @Test
    public void testPathToPinballMachine()
    {
        String path = Config.pathToPinballMachine(testMachineId);
        assertThat(path.endsWith("/machines/" + testMachineId), is(true));
    }

    @Test
    public void testPathToPinballMachinePreviewImage()
    {
        String path = Config.pathToPinballMachineImagePreview(testMachineId);
        assertThat(path.endsWith("/machines/" + testMachineId + "/preview.png"), is(true));
    }

    @Test
    public void testPathToPinballMachineGeneralData()
    {
        String path = Config.pathToPinballMachineGeneralJson(testMachineId);
        assertThat(path.endsWith("/machines/" + testMachineId + "/general.json"), is(true));
    }

    @Test
    public void testPathToPinballMachineData()
    {
        String path = Config.pathToPinballMachinePlacedElementsJson(testMachineId);
        assertThat(path.endsWith("/machines/" + testMachineId + "/elements.json"), is(true));
    }

    @Test
    public void testPathToSettings()
    {
        String path = Config.pathToSettings();
        assertThat(path.endsWith("/settings.json"), is(true));
    }

    @Test
    public void testPathToLanguage()
    {
        String path = Config.pathToLanguage("de");
        assertThat(path, is(expectedLanguagePath));
    }

    @Test
    public void testPathToLogo()
    {
        String path = Config.pathToLogo();
        assertThat(path.endsWith("/logo.png"), is(true));
    }

    @Test
    public void testPathToTestData()
    {
        String path = Config.pathToTestData();
        assertThat(path.endsWith("/testdata/"), is(true));
    }

    @Test
    public void testPathToSound()
    {
        String path = Config.pathToSound("game");
        assertThat(path.endsWith(expectedSoundPathEnding), is(true));
    }

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
