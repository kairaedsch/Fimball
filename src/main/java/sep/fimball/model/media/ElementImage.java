package sep.fimball.model.media;

import sep.fimball.general.data.Config;
import sep.fimball.general.data.ImageLayer;
import sep.fimball.model.element.GameElement;

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
    private boolean isAnimation;

    /**
     * TODO
     */
    private Animation animation;

    /**
     * TODO
     */
    private long creationTime;

    /**
     * Erzeugt ein neues ElementImage mit der gegebenen {@code baseElementId}.
     * @param elementTypeId Die ID des Elementtypen, zu dem das ElementImage gehören soll.
     */
    public ElementImage(String elementTypeId)
    {
        this(elementTypeId, false, 360, false, null);
    }

    /**
     * Erzeugt ein neues ElementImage mit den übergebenen Werten.
     * @param elementTypeId Die ID des Elementtypen, zu dem das ElementImage gehören soll.
     * @param rotationAccuracy Gibt an, um wie viel Grad das Bild gedreht werden können soll.
     */
    public ElementImage(String elementTypeId, int rotationAccuracy)
    {
        this(elementTypeId, true, rotationAccuracy, false, null);
    }

    /**
     * Erzeugt ein neues ElementImage mit den übergebenen Werten.
     * @param elementTypeId Die ID des Elementtypen, zu dem das ElementImage gehören soll.
     */
    public ElementImage(String elementTypeId, Animation animation)
    {
        this(elementTypeId, false, 360, true, animation);
    }

    /**
     * Erzeugt ein neues ElementImage mit den übergebenen Werten.
     * @param elementTypeId Die ID des Elementtypen, zu dem das ElementImage gehören soll.
     * @param rotationAccuracy Gibt an, um wie viel Grad das Bild gedreht werden können soll.
     */
    public ElementImage(String elementTypeId, int rotationAccuracy, Animation animation)
    {
       this(elementTypeId, true, rotationAccuracy, true, animation);
    }

    /**
     * Erzeugt ein neues ElementImage mit den übergebenen Werten.
     * @param elementTypeId Die ID des Elementtypen, zu dem das ElementImage gehören soll.
     * @param canRotate Gibt an, ob das ElementImage rotiert werden können soll.
     * @param rotationAccuracy Gibt an, um wie viel Grad das Bild gedreht werden können soll.
     * @param animation TODO
     */
    private ElementImage(String elementTypeId, boolean canRotate, int rotationAccuracy, boolean isAnimation, Animation animation)
    {
        this.elementTypeId = elementTypeId;
        this.canRotate = canRotate;
        this.rotationAccuracy = rotationAccuracy <= 0 ? 360 : rotationAccuracy;
        this.isAnimation = isAnimation;
        this.animation = animation;
        creationTime = System.currentTimeMillis();
    }

    public ElementImage(GameElement gameElement, Animation animation)
    {
        this(gameElement.getPlacedElement().getBaseElement().getId(), gameElement.getPlacedElement().getBaseElement().getMedia().canRotate(), gameElement.getPlacedElement().getBaseElement().getMedia().getRotationAccuracy(), true, animation);
    }

    /**
     * Gibt den Pfad zu dem zu diesem ElementImage gehörenden Bild in Abhängigkeit von {@code imageLayer} und {@code rotation} zurück.
     * @param imageLayer Das ImageLayer des Bildes.
     * @param rotation Die Drehung des Bildes.
     * @return Ein zu diesem ElementImage gehörendes Bild.
     */
    public String getImagePath(ImageLayer imageLayer, int rotation)
    {
        int framePos = animation == null ? 0 : (int) ((System.currentTimeMillis() - creationTime) / animation.getDuration());
        if(isAnimation && framePos < animation.getFrameCount()) return Config.pathToElementImage(elementTypeId, imageLayer, canRotate, (rotation % 360) - (rotation % rotationAccuracy), isAnimation, animation.getName(), framePos % animation.getFrameCount());
        else return Config.pathToElementImage(elementTypeId, imageLayer, canRotate, (rotation % 360) - (rotation % rotationAccuracy), false, "", 0);
    }

    /**
     * Gibt zurück, welcher Anzahl von {@code rotationAccuracy} {@code rotation} entspicht.
     * @param rotation Der Wert, der mit der {@code rotationAccuracy} verglichen werden soll.
     * @return Die Anzahl von {@code rotationAccuracy}, die {@code rotation} entspricht.
     */
    public int getRestRotation(int rotation)
    {
        return rotation % rotationAccuracy;
    }
}