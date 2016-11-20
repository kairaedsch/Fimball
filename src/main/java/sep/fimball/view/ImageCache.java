package sep.fimball.view;

import javafx.scene.image.Image;

import java.io.File;
import java.util.HashMap;

/**
 * Created by alexcekay on 17.11.16.
 */
public class ImageCache
{
    private static ImageCache instance;
    private HashMap<String, Image> cachedImages;

    public static ImageCache getInstance()
    {
        if (instance == null)
            instance = new ImageCache();

        return instance;
    }

    private ImageCache()
    {
        cachedImages = new HashMap<>();
    }

    public Image getImage(String imagePath)
    {
        if (cachedImages.containsKey(imagePath))
        {
            return cachedImages.get(imagePath);
        }
        else
        {
            File topFile = new File(imagePath);
            Image loadedImage = new Image(topFile.toURI().toString(), true);
            cachedImages.put(imagePath, loadedImage);
            return loadedImage;
        }
    }
}
