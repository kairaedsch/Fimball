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
     * Die ID des BaseElements, zu dem das ElementImage gehört.
     */
    private String baseElementId;

    /**
     * Gibt an, ob das ElementImage gedreht werden kann.
     */
    private boolean canRotate;

    /**
     * Gibt an, ob das ElementImage eine Animation ist.
     */
    private boolean isAnimation;

    /**
     * Die zugehörige Animation.
     */
    private Animation animation;

    /**
     * Die Zeit, zu der das ElementImage erstellt wurde.
     */
    private long creationTime;

    /**
     * Erzeugt ein neues ElementImage mit der gegebenen {@code baseElementId}.
     * @param baseElementId Die ID des BaseElements, zu dem das ElementImage gehören soll.
     */
    public ElementImage(String baseElementId)
    {
        this(baseElementId, false, 360, false, null);
    }

    /**
     * Erzeugt ein neues ElementImage mit den übergebenen Werten.
     * @param baseElementId Die ID des BaseElements, zu dem das ElementImage gehören soll.
     * @param rotationAccuracy Gibt an, um wie viel Grad das zugehörige BaseElement gedreht werden kann.
     */
    public ElementImage(String baseElementId, int rotationAccuracy)
    {
        this(baseElementId, true, rotationAccuracy, false, null);
    }

    /**
     * Erzeugt ein neues ElementImage mit den übergebenen Werten.
     * @param baseElementId Die ID des BaseElements, zu dem das ElementImage gehören soll.
     */
    public ElementImage(String baseElementId, Animation animation)
    {
        this(baseElementId, false, 360, true, animation);
    }

    /**
     * Erzeugt ein neues ElementImage mit den übergebenen Werten.
     * @param baseElementId Die ID des BaseElements, zu dem das ElementImage gehören soll.
     * @param rotationAccuracy Gibt an, um wie viel Grad das zugehörige BaseElement gedreht werden kann.
     */
    public ElementImage(String baseElementId, int rotationAccuracy, Animation animation)
    {
       this(baseElementId, true, rotationAccuracy, true, animation);
    }

    /**
     * Erzeugt ein neues ElementImage mit den übergebenen Werten.
     * @param baseElementId Die ID des BaseElements, zu dem das ElementImage gehören soll.
     * @param canRotate Gibt an, ob das zugehörige BaseElement gedreht werden kann.
     * @param rotationAccuracy Gibt an, um wie viel Grad das zugehörige BaseElement gedreht werden kann.
     * @param isAnimation Gibt am, ob das ElementImage eine Animation ist.
     * @param animation Die zuugehörige Animation.
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
     * @param gameElement Das Element, zu dessen BaseElement das ElementImage gehört,
     * @param animation Die zugehörige Animation.
     */
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
        if(isAnimation && framePos < animation.getFrameCount()) return Config.pathToElementImage(baseElementId, imageLayer, canRotate, (rotation % 360) - (rotation % rotationAccuracy), isAnimation, animation.getName(), framePos % animation.getFrameCount());
        else return Config.pathToElementImage(baseElementId, imageLayer, canRotate, (rotation % 360) - (rotation % rotationAccuracy), false, "", 0);
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