package sep.fimball.view;

import javafx.scene.image.Image;
import org.junit.ClassRule;
import org.junit.Test;
import sep.fimball.JavaFXThreadingRule;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNot.not;
import static org.hamcrest.number.OrderingComparison.lessThan;
import static org.junit.Assert.assertThat;

public class ImageCacheTest
{
    @ClassRule
    public static JavaFXThreadingRule javafxRule = new JavaFXThreadingRule();

    @Test
    public void getImageSpeedTest()
    {
        ImageCache imageCache = ImageCache.getInstance();

        String testImagePath = ImageCacheTest.class.getClassLoader().getResource("images/bigpic.png").toString();

        // Lade das Bild zum ersten mal von der Festplatte
        Image imageFromFile;
        int durationLoadingFromFile;
        {
            long start = System.currentTimeMillis();
            imageFromFile = imageCache.getImage(testImagePath);
            long end = System.currentTimeMillis();
            durationLoadingFromFile = (int) (end - start);
        }

        // Test wird wiederholt, um sicher zu stellen, dass das Bild nicht zufällig schneller geladen wird
        for (int i = 0; i < 100; i++)
        {
            // Hole das Bild vom Cache
            long start = System.currentTimeMillis();
            Image imageFromCache = imageCache.getImage(testImagePath);
            long end = System.currentTimeMillis();
            int durationLoadingFromCache = (int) (end - start);

            assertThat("Durchlauf " + i + ": Es wird immer das selbe Bild zurückgegeben", imageFromCache, equalTo(imageFromFile));
            assertThat("Durchlauf " + i + ": Das holen des gecachten Bildes war nicht langsamer (mit 1ms Toleranz)", durationLoadingFromFile + 1, is(not(lessThan(durationLoadingFromCache))));
        }
    }

    @Test
    public void getImageMultiPicTest()
    {
        ImageCache imageCache = ImageCache.getInstance();

        String testImage1Path = ImageCacheTest.class.getClassLoader().getResource("images/bigpic.png").toString();
        String testImage2Path = ImageCacheTest.class.getClassLoader().getResource("images/otherpic.png").toString();

        // Lade die Bilder zum ersten mal von der Festplatte
        Image image1FromFile = imageCache.getImage(testImage1Path);
        Image image2FromFile = imageCache.getImage(testImage2Path);

        // Hole die Bilder vom Cache
        Image image1FromCache = imageCache.getImage(testImage1Path);
        Image image2FromCache = imageCache.getImage(testImage2Path);

        assertThat("Gecachtes und geladenes Bild 1 sind gleich", image1FromCache, equalTo(image1FromFile));
        assertThat("Gecachtes und geladenes Bild 2 sind gleich", image2FromCache, equalTo(image2FromFile));

        assertThat("Geladenes Bild 1 und geladenes Bild 2 sind nicht gleich", image1FromFile, is(not(equalTo(image2FromFile))));
    }
}