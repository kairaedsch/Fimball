package sep.fimball.model.media;

import sep.fimball.general.data.Config;
import sep.fimball.general.data.ImageLayer;

/**
 * ElementImage stellt ein Bild oder Animation eines BaseElements dar.
 */
public class ElementImage
{
    /**
     * Gibt an, in welchen Grad-Schritten Bilder vorliegen. So bedeutet z.B. der Wert 90, das Bilder mit 0, 90, 180, und 270 Grad vorliegen.
     */
    private final int rotationAccuracy;

    /**
     * Die ID des BaseElements, zu dem das ElementImage gehört.
     */
    private String baseElementId;

    /**
     * Gibt an, ob Bilder in Grad-Schritten vorliegen.
     */
    private boolean canRotate;

    /**
     * Gibt an, ob das ElementImage eine Animation ist.
     */
    private boolean isAnimation;

    /**
     * Die zugehörige Animation, falls isAnimation true ist, andernfalls null.
     */
    private Animation animation;

    /**
     * Die Zeit, zu der das ElementImage erstellt wurde.
     */
    private long creationTime;

    /**
     * Erzeugt ein neues ElementImage.
     *
     * @param baseElementId Die ID des BaseElements, zu dem das ElementImage gehören soll.
     */
    public ElementImage(String baseElementId)
    {
        this(baseElementId, false, 360, false, null);
    }

    /**
     * Erzeugt ein neues ElementImage mit den übergebenen Werten.
     *
     * @param baseElementId    Die ID des BaseElements, zu dem das ElementImage gehören soll.
     * @param rotationAccuracy Gibt an, um wie viel Grad das zugehörige BaseElement gedreht werden kann.
     */
    public ElementImage(String baseElementId, int rotationAccuracy)
    {
        this(baseElementId, true, rotationAccuracy, false, null);
    }

    /**
     * Erzeugt ein neues ElementImage mit den übergebenen Werten.
     *
     * @param baseElementId    Die ID des BaseElements, zu dem das ElementImage gehören soll.
     * @param canRotate        Gibt an, ob das zugehörige BaseElement gedreht werden kann.
     * @param rotationAccuracy Gibt an, um wie viel Grad das zugehörige BaseElement gedreht werden kann.
     * @param isAnimation      Gibt am, ob das ElementImage eine Animation ist.
     * @param animation        Die zugehörige Animation.
     */
    private ElementImage(String baseElementId, boolean canRotate, int rotationAccuracy, boolean isAnimation, Animation animation)
    {
        this.baseElementId = baseElementId;
        this.canRotate = canRotate;
        this.rotationAccuracy = rotationAccuracy <= 0 ? 360 : rotationAccuracy;
        this.isAnimation = isAnimation;
        this.animation = animation;
        creationTime = System.currentTimeMillis();
    }

    /**
     * Erzeugt ein neues ElementImage mit den übergebenen Werten.
     *
     * @param baseElementId    Die ID des zugehörigen BaseElements.
     * @param baseMediaElement Das zugehörige BaseMediaElement.
     * @param animation        Die zugehörige Animation.
     */
    public ElementImage(String baseElementId, BaseMediaElement baseMediaElement, Animation animation)
    {
        this(baseElementId, baseMediaElement.canRotate(), baseMediaElement.getRotationAccuracy(), true, animation);
    }

    /**
     * Gibt den Pfad zu dem zu diesem ElementImage gehörenden Bild in Abhängigkeit von {@code imageLayer} und {@code rotation} zurück.
     *
     * @param imageLayer Das ImageLayer des Bildes.
     * @param rotation   Die Drehung des Bildes.
     * @return Ein zu diesem ElementImage gehörendes Bild.
     */
    public String getImagePath(ImageLayer imageLayer, int rotation)
    {
        int framePos = animation == null ? 0 : (int) ((System.currentTimeMillis() - creationTime) / animation.getDuration());
        if (isAnimation && framePos < animation.getFrameCount())
            return Config.pathToElementImage(baseElementId, imageLayer, canRotate, (rotation % 360) - (rotation % rotationAccuracy), isAnimation, animation.getName(), framePos % animation.getFrameCount());
        else
            return Config.pathToElementImage(baseElementId, imageLayer, canRotate, (rotation % 360) - (rotation % rotationAccuracy), false, "", 0);
    }

    /**
     * Gibt die gegebene Rotation minus die Rotation des Bildes zurück.
     *
     * @param rotation Die gegebene Rotation.
     * @return Die gegebene Rotation minus die Rotation des Bildes.
     */
    public int getRestRotation(int rotation)
    {
        return rotation % rotationAccuracy;
    }
}