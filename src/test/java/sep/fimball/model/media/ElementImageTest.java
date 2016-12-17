package sep.fimball.model.media;

import org.junit.Before;
import org.junit.Test;
import sep.fimball.general.data.DataPath;
import sep.fimball.general.data.ImageLayer;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

/**
 * Testet die Klasse ElementImage.
 */
public class ElementImageTest
{
    /**
     * Das ElementImage, das getestet wird.
     */
    private ElementImage testElementImage;

    /**
     * Initialisiert das {@code testElementImage}.
     */
    @Before
    public void init()
    {
        BaseMediaElement testMediaElement = new BaseMediaElement("testMedia", "testMediaDesc", 1, true, 40, null, null, null);
        Animation testAnimation = new Animation(2, 3, "testAnimation");
        testElementImage = new ElementImage("testElement", testMediaElement, testAnimation);
    }

    /**
     * Testet, ob die Berechnung der Rest-Rotation funktioniert.
     */
    @Test
    public void RestRotationTest()
    {
        assertThat("Die Rest-Rotation wird richtig berechnet, wenn die Rotation kleiner ist, als die Rotations-Genauigkeit", testElementImage.getRestRotation(25), equalTo(25));
        assertThat("Die Rest-Rotation wird richtig berechnet, wenn die Rotation gleich der Rotations-Genauigkeit ist", testElementImage.getRestRotation(40), equalTo(0));
        assertThat("Die Rest-Rotation wird richtig berehchnet, wenn die Rotation größer als die Rotations-Genauigkeit ist", testElementImage.getRestRotation(75), equalTo(35));
    }

    /**
     * Testet, ob der Bildpfad in Abhängigkeit von ImageLayer, Rotation und verstrichener Zeit richtig angegeben wird.
     */
    @Test
    public void imagePathTest()
    {

        assertThat("Das Bild wird mit der richtigen Rotation, Animation und Drehung ausgewählt", testElementImage.getImagePath(ImageLayer.BOTTOM, 35, 9), equalTo(DataPath.pathToElements() + "/testElement/bottom-0.png"));
        assertThat("Das Bild wird mit der richtigen Rotation, Animation und Drehung ausgewählt", testElementImage.getImagePath(ImageLayer.TOP, 60, 1), equalTo(DataPath.pathToElements() + "/testElement/top-40+testAnimation_0.png"));
        assertThat("Das Bild wird mit der richtigen Rotation, Animation und Drehung ausgewählt", testElementImage.getImagePath(ImageLayer.TOP, 60, 2), equalTo(DataPath.pathToElements() + "/testElement/top-40+testAnimation_1.png"));
        assertThat("Das Bild wird mit der richtigen Rotation, Animation und Drehung ausgewählt", testElementImage.getImagePath(ImageLayer.TOP, 90, 5), equalTo(DataPath.pathToElements() + "/testElement/top-80+testAnimation_2.png"));
        assertThat("Das Bild wird mit der richtigen Rotation, Animation und Drehung ausgewählt", testElementImage.getImagePath(ImageLayer.TOP, 90, 7), equalTo(DataPath.pathToElements() + "/testElement/top-80.png"));
    }


}
