package sep.fimball.model.media;

import org.junit.Before;
import org.junit.Test;
import sep.fimball.general.data.Config;
import sep.fimball.general.data.ImageLayer;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;


public class ElementImageTest
{
    ElementImage testElementImage;

    @Before
    public void init()
    {
        //Legt ein Test-BaseMediaELement, eien Test-Animation und ein Test-ElementImage an.
        BaseMediaElement testMediaElement = new BaseMediaElement("testMedia", "testMediaDesc", true, 40, null, null);
        Animation testAnimation = new Animation(2, 3, "testAnimation");
        testElementImage = new ElementImage("testElement", testMediaElement, testAnimation);
    }

    @Test
    public void RestRotationTest()
    {
        //Testet, ob die Berechnung der Rest-Rotation funktioniert.
        assertThat("Die Rest-Rotation wird richtig berechnet, wenn die Rotation kleiner ist, als die Rotations-Genauigkeit", testElementImage.getRestRotation(25), equalTo(25));
        assertThat("Die Rest-Rotation wird richtig berechnet, wenn die Rotation gleich der Rotations-Genauigkeit ist", testElementImage.getRestRotation(40), equalTo(0));
        assertThat("Die Rest-Rotation wird richtig berehchnet, wenn die Rotation größer als die Rotations-Genauigkeit ist", testElementImage.getRestRotation(75), equalTo(35));
    }

    @Test
    public void imagePathTest()
    {
        // Testet, ob der richtige Bildpfad angegeben wird.
        assertThat("Das Bild wird mit der richtigen Rotation, Animation und Drehung ausgewählt", testElementImage.getImagePath(ImageLayer.BOTTOM, 35, 9), equalTo(Config.pathToElements() + "/testElement/bottom-0.png"));
        assertThat("Das Bild wird mit der richtigen Rotation, Animation und Drehung ausgewählt", testElementImage.getImagePath(ImageLayer.TOP, 60, 1), equalTo(Config.pathToElements() + "/testElement/top-40+testAnimation_0.png"));
        assertThat("Das Bild wird mit der richtigen Rotation, Animation und Drehung ausgewählt", testElementImage.getImagePath(ImageLayer.TOP, 60, 2), equalTo(Config.pathToElements() + "/testElement/top-40+testAnimation_1.png"));
        assertThat("Das Bild wird mit der richtigen Rotation, Animation und Drehung ausgewählt", testElementImage.getImagePath(ImageLayer.TOP, 90, 5), equalTo(Config.pathToElements() + "/testElement/top-80+testAnimation_2.png"));
        assertThat("Das Bild wird mit der richtigen Rotation, Animation und Drehung ausgewählt", testElementImage.getImagePath(ImageLayer.TOP, 90, 7), equalTo(Config.pathToElements() + "/testElement/top-80.png"));
    }


}
