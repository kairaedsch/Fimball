package sep.fimball.view.tools;

import javafx.scene.image.Image;

import java.io.File;
import java.util.HashMap;

/**
 * Lädt Bilder und cached diese, um unnötiges Laden von mehreren Images des selben Bildes zu verhindern.
 */
public class ImageCache
{
    /**
     * Stellt sicher, dass es nur eine Instanz von ImageCache gibt.
     */
    private static ImageCache instance;

    /**
     * Speichert Bilder zusammen mit dem Pfad, sodass schon geladenen Bilder durch den pfad gefunden werden können.
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
     * Gibt das im {code imagePath} liegende Bild zurück. Falls dieses noch nicht gecached wurde, wird es geladen und zusammen mit {@code imagePath} gecached.
     *
     * @param imagePath Der Pfad zum gewünschten Image.
     * @return Das in {@code imagePath} liegende Bild.
     */
    public Image getImage(String imagePath)
    {
        // Falls das Bild noch nicht im cache ist, wird es geladen und zur {code cachedImages} hinzugefügt.
        if (!cachedImages.containsKey(imagePath))
        {
            File imageFile = new File(imagePath);
            Image loadedImage = new Image(imageFile.toURI().toString(), true);
            cachedImages.put(imagePath, loadedImage);
        }

        return cachedImages.get(imagePath);
    }
}
