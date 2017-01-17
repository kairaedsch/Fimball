package sep.fimball.viewmodel;


import sep.fimball.general.data.ImageLayer;
import sep.fimball.model.media.ElementImage;

/**
 * Diese Klasse stellt das Bild eines Elements dar.
 */
public class ElementImageViewModel
{
    /**
     * Das zugehörige ElementImage, das das Bild zum Element darstellt.
     */
    private ElementImage elementImage;

    /**
     * Die Zeit, zu der das ElementImage erstellt wurde.
     */
    private long creationTime;

    private boolean isAnimating;

    /**
     * Erstellt ein neues ElementImageViewModel.
     *
     * @param elementImage Das zugehörige ElementImage.
     */
    public ElementImageViewModel(ElementImage elementImage)
    {
        this.elementImage = elementImage;
        creationTime = System.currentTimeMillis();
        isAnimating = true;
    }

    /**
     * Gibt den Pfad zu dem zu diesem ElementImageViewModel gehörenden Bild in
     * Abhängigkeit von {@code imageLayer} und {@code rotation} zurück.
     *
     * @param imageLayer Das ImageLayer des Bildes.
     * @param rotation   Die Drehung des Bildes.
     * @return Ein zu diesem ElementImage gehörendes Bild.
     */
    public String getImagePath(ImageLayer imageLayer, int rotation, long time)
    {
        long deltaTime = time - creationTime;
        isAnimating = elementImage.isAnimating(deltaTime);
        return elementImage.getImagePath(imageLayer, rotation, deltaTime);
    }

    /**
     * Gibt den Pfad zu dem zu diesem ElementImageViewModel gehörenden Bild in
     * Abhängigkeit von {@code imageLayer} und {@code rotation} zurück.
     *
     * @param imageLayer Das ImageLayer des Bildes.
     * @param rotation   Die Drehung des Bildes.
     * @return Ein zu diesem ElementImage gehörendes Bild.
     */
    public String getImagePath(ImageLayer imageLayer, int rotation)
    {
        return getImagePath(imageLayer, rotation, Long.MAX_VALUE);
    }

    /**
     * Gibt die gegebene Rotation minus die Rotation des Bildes zurück.
     *
     * @param rotation Die gegebene Rotation.
     * @return Die gegebene Rotation minus die Rotation des Bildes.
     */
    public double getRestRotation(int rotation)
    {
        return elementImage.getRestRotation(rotation);
    }

    public boolean isAnimating()
    {
        return isAnimating;
    }
}
