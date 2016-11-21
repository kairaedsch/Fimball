package sep.fimball.view;

import javafx.scene.image.Image;

import java.io.File;
import java.util.HashMap;

/**
 * Cached bereits geladenen Bilder, um Ladezeiten zu verkürzen.
 */
public class ImageCache
{
    /**
     * Stellt sicher, dass es nur eine Instanz von Settings gibt.
     */
    private static ImageCache instance;

    /**
     * Speichert Bilder zusammen mit dem Pfad, der zu diesem führt.
     */
    private HashMap<String, Image> cachedImages;

    /**
     * Gibt den bereits existierenden ImageCache oder einen neu angelegten zurück, falls noch keiner existieren.
     *
     * @return Die Instanz von ImageCache.
     */
    public static ImageCache getInstance()
    {
        if (instance == null)
            instance = new ImageCache();

        return instance;
    }

    /**
     * Erzeugt einen neuen ImageCache.
     */
    private ImageCache()
    {
        cachedImages = new HashMap<>();
    }

    /**
     * Gibt das im {code imagePath} gespeicherte Bild zurück. Falls dieses noch nicht gecached wurde, wird es nun zusammen mit {@code imagePath} gespeichert.
     * @param imagePath Der Pfad zum gewünschten Image.
     * @return Das in {@code imagePath} gespeicherte Bild.
     */
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
