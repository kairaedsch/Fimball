package sep.fimball.model.media;

import org.junit.Before;
import org.junit.Test;
import sep.fimball.general.data.Config;
import sep.fimball.general.data.ImageLayer;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;


public class ElementImageTest
{
    ElementImage test;

    @Before
    public void init()
    {
        //Legt ein Test-BaseMediaELement, eien Test-Animation und ein Test-ElementImage an.
        BaseMediaElement testMediaElement = new BaseMediaElement("testMedia", "testMediaDesc", true, 40, null, null);
        Animation testAnimation = new Animation(2, 3, "test");
        test = new ElementImage("test2", testMediaElement, testAnimation);
    }

    @Test
    public void RestRotationTest()
    {
        //Testet, ob die Berechnung der Rest-Rotation funktioniert.
        assertThat(test.getRestRotation(25), equalTo(25));
        assertThat(test.getRestRotation(40), equalTo(0));
        assertThat(test.getRestRotation(75), equalTo(35));
    }

    @Test
    public void imagePathTest()
    {
        // Testet, ob der richtige Bildpfad angegeben wird.
        assertThat(test.getImagePath(ImageLayer.BOTTOM, 35, 9), equalTo(Config.pathToElements() + "/test2/bottom-0.png"));
        assertThat(test.getImagePath(ImageLayer.TOP, 60, 1), equalTo(Config.pathToElements() + "/test2/top-40+test_0.png"));
        assertThat(test.getImagePath(ImageLayer.TOP, 60, 2), equalTo(Config.pathToElements() + "/test2/top-40+test_1.png"));
        assertThat(test.getImagePath(ImageLayer.TOP, 90, 5), equalTo(Config.pathToElements() + "/test2/top-80+test_2.png"));
        assertThat(test.getImagePath(ImageLayer.TOP, 90, 7), equalTo(Config.pathToElements() + "/test2/top-80.png"));
    }


}
