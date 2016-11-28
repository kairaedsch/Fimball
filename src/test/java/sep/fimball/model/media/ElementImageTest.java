package sep.fimball.model.media;

import org.junit.Test;
import sep.fimball.general.data.Config;
import sep.fimball.general.data.ImageLayer;

import static junit.framework.TestCase.assertEquals;


public class ElementImageTest
{
    private static final long MAX_TEST_DURATION = 20000;    // Die Zeit in Millisekunden, nach der der Test abgebrochen wird.


    @Test(timeout = MAX_TEST_DURATION)
    public void elementImageTest()
    {


        BaseMediaElement testMediaElement = new BaseMediaElement("testMedia", "testMediaDesc", true,40, null, null );
        Animation testAnimation = new Animation(2, 3, "test");
        ElementImage test = new ElementImage("test2", testMediaElement, testAnimation);

        assertEquals(test.getRestRotation(25), 25);
        assertEquals(test.getRestRotation(40), 0);
        assertEquals(test.getRestRotation(75), 35);

        assertEquals(test.getImagePath(ImageLayer.BOTTOM, 35, 20), Config.pathToElements() + "/test2/bottom-0.png" );
        assertEquals(test.getImagePath(ImageLayer.BOTTOM, 60, 20), Config.pathToElements() + "/test2/bottom-40.png" );
      //  assertEquals(test.getImagePath(ImageLayer.TOP, 60, 25), "D:/Dokumente/Fimball/target/data/elements/test2/top+glow_0-40.png" );
    }


}
