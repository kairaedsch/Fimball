package sep.fimball.view;

import javafx.scene.image.Image;
import org.junit.ClassRule;
import org.junit.Test;
import sep.fimball.JavaFXThreadingRule;

import static javafx.scene.input.KeyCode.M;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
/**
 * Created by kaira on 29.11.2016.
 */
public class ImageCacheTest
{
    @ClassRule
    public static JavaFXThreadingRule javafxRule = new JavaFXThreadingRule();

    @Test
    public void getImage() throws Exception
    {
        ImageCache imageCache = ImageCache.getInstance();

        String testImagepath = ImageCacheTest.class.getClassLoader().getResource("images/bigpic.png").toString();

        Image imageFromFile;
        int durationLoadingFromFile;
        {
            long start = System.currentTimeMillis();
            imageFromFile = imageCache.getImage(testImagepath);
            long end = System.currentTimeMillis();
            durationLoadingFromFile = (int) (end - start);
        }

        Image imageFromCache;
        int durationLoadingFromCache;
        {
            long start = System.currentTimeMillis();
            imageFromCache = imageCache.getImage(testImagepath);
            long end = System.currentTimeMillis();
            durationLoadingFromCache = (int) (end - start);
        }

        assertThat(imageFromFile, equalTo(imageFromCache));

        for(int i = 0; i < 1000; i++)
        {
            long start = System.currentTimeMillis();
            imageCache.getImage(testImagepath);
            long end = System.currentTimeMillis();
            assertThat((int) (end - start));
        }
    }
}