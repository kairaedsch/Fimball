package sep.fimball.model.media;

import org.junit.Test;
import sep.fimball.general.data.Config;
import sep.fimball.general.data.ImageLayer;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;


public class ElementImageTest
{
    @Test
    public void elementImageTest()
    {

        //Legt ein Test-BaseMediaELement, eien Test-Animation und ein Test-ElementImage an.
        BaseMediaElement testMediaElement = new BaseMediaElement("testMedia", "testMediaDesc", true,40, null, null );
        Animation testAnimation = new Animation(2, 3, "test");
        ElementImage test = new ElementImage("test2", testMediaElement, testAnimation);

        //Testet, ob die Berechnung der Rest-Rotation funktioniert.
        assertThat(test.getRestRotation(25), equalTo( 25));
        assertThat(test.getRestRotation(40), equalTo(0));
        assertThat(test.getRestRotation(75), equalTo(35));

        // Testet, ob der richtige Bildpfad angegeben wird.
        assertThat(test.getImagePath(ImageLayer.BOTTOM, 35, 20),equalTo(Config.pathToElements() + "/test2/bottom-0.png"));
        assertThat(test.getImagePath(ImageLayer.BOTTOM, 60, 20), equalTo(Config.pathToElements() + "/test2/bottom-40.png"));
      //  assertEquals(test.getImagePath(ImageLayer.TOP, 60, 25), "D:/Dokumente/Fimball/target/data/elements/test2/top+glow_0-40.png" );
    }


}
