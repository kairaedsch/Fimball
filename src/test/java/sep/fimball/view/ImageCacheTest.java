package sep.fimball.view;

import javafx.scene.image.Image;
import org.junit.ClassRule;
import org.junit.Test;
import sep.fimball.JavaFXThreadingRule;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.number.OrderingComparison.lessThan;
import static org.junit.Assert.assertThat;

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

        // Test wird wiederholt, um sicher zu stellen, dass das Bild nicht zufällig schneller geladen wird.
        for (int i = 0; i < 100; i++)
        {
            long start = System.currentTimeMillis();
            Image imageFromCache = imageCache.getImage(testImagepath);
            long end = System.currentTimeMillis();
            int durationLoadingFromCache = (int) (end - start);

            assertThat("Es wird immer das selbe Bild zurückgegeben", imageFromCache, equalTo(imageFromFile));
            assertThat("Das laden des gecachten Bildes geht schneller", durationLoadingFromCache, lessThan(durationLoadingFromFile));
        }
    }
}