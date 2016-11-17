package sep.fimball.model.media;

import sep.fimball.general.data.Config;
import sep.fimball.general.data.ImageLayer;

/**
 * ElementImage stellt ein Bild eines Elements mit weiteren Informationen zur Darstellung dar.
 */
public class ElementImage
{
    /**
     * Gibt an, um wie viel Grad das Bild gedreht werden kann.
     */
    private final int rotationAccuracy;

    /**
     * Die ID des Elementtypen, zu dem das ElementImage gehört.
     */
    private String elementTypeId;

    /**
     * Gibt an, ob das Bild rotiert werden kann.
     */
    private boolean canRotate;

    /**
     * TODO
     */
    private boolean animation;

    /**
     * TODO
     */
    private String animationName;

    /**
     * TODO
     */
    private int animationId;

    /**
     * Erzeugt ein neues ElementImage mit der gegebenen {@code baseElementId}.
     * @param elementTypeId Die ID des Elementtypen, zu dem das ElementImage gehören soll.
     */
    public ElementImage(String elementTypeId)
    {
        this(elementTypeId, false, 360, false, null, -1);
    }

    /**
     * Erzeugt ein neues ElementImage mit den übergebenen Werten.
     * @param elementTypeId Die ID des Elementtypen, zu dem das ElementImage gehören soll.
     * @param rotationAccuracy Gibt an, um wie viel Grad das Bild gedreht werden könen soll.
     */
    public ElementImage(String elementTypeId, int rotationAccuracy)
    {
        this(elementTypeId, true, rotationAccuracy, false, null, -1);
    }

    /**
     * Erzeugt ein neues ElementImage mit den übergebenen Werten.
     * @param elementTypeId Die ID des Elementtypen, zu dem das ElementImage gehören soll.
     * @param animationName TODO
     * @param animationId TODO
     */
    public ElementImage(String elementTypeId, String animationName, int animationId)
    {
        this(elementTypeId, false, 360, true, animationName, animationId);
    }

    /**
     * Erzeugt ein neues ElementImage mit den übergebenen Werten.
     * @param elementTypeId Die ID des Elementtypen, zu dem das ElementImage gehören soll.
     * @param rotationAccuracy Gibt an, um wie viel Grad das Bild gedreht werden könen soll.
     * @param animationName TODO
     * @param animationId TODO
     */
    public ElementImage(String elementTypeId, int rotationAccuracy, String animationName, int animationId)
    {
       this(elementTypeId, true, rotationAccuracy, true, animationName, animationId);
    }

    /**
     * Erzeugt ein neues ElementImage mit den übergebenen Werten.
     * @param elementTypeId Die ID des Elementtypen, zu dem das ElementImage gehören soll.
     * @param canRotate Gibt an, ob das ElementImage rotiert werden können soll.
     * @param rotationAccuracy Gibt an, um wie viel Grad das Bild gedreht werden können soll.
     * @param animation TODO
     * @param animationName TODO
     * @param animationId TODO
     */
    private ElementImage(String elementTypeId, boolean canRotate, int rotationAccuracy, boolean animation, String animationName, int animationId)
    {
        this.elementTypeId = elementTypeId;
        this.canRotate = canRotate;
        this.rotationAccuracy = rotationAccuracy;
        this.animation = animation;
        this.animationName = animationName;
        this.animationId = animationId;
    }

    /**
     * Gibt den Pfad zu dem zu diesem ElementImage gehörenden Bild in Abhängigkeit von {@code imageLayer} und {@code rotation} zurück.
     * @param imageLayer Das ImageLayer des Bildes.
     * @param rotation Die Drehung des Bildes.
     * @return Ein zu diesem ElementImage gehörendes Bild.
     */
    public String getImagePath(ImageLayer imageLayer, int rotation)
    {
        return Config.pathToElementImage(elementTypeId, imageLayer, canRotate, (rotation % 360) - (rotation % rotationAccuracy), animation, animationName, animationId);
    }

    /**
     * Gibt zurück, welcher Anzahl von {@code rotationAccuracy} {@code rotation} entspicht.
     * @param rotation Der Wert, der mit der {@code rotationAccuracy} verglichen werden soll.
     * @return Die Anzahl von {@code rotationAccuracy}, die {@code rotation} entspicht.
     */
    public int getRestRotation(int rotation)
    {
        return rotation % rotationAccuracy;
    }
}
