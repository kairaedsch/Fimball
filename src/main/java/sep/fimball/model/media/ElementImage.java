package sep.fimball.model.media;

import sep.fimball.general.data.DataPath;
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
     * Gibt an, ob Bilder in Grad-Schritten vorliegen also ob es vorgesehen ist das zugehörige Element zu drehen.
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
     * Der obere Teil des Standardbilds.
     */
    private String[] defaultImagesTop;

    /**
     * Der untere Teil des Standardbilds.
     */
    private String[] defaultImagesBottom;

    /**
     * Die oberen Teile der Bilder der Animation.
     */
    private String[][] animationImagesTop;

    /**
     * Die unteren Teile der Bilder der Animation.
     */
    private String[][] animationImagesBottom;

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
     * @param baseElementId    Die ID des zugehörigen BaseElements.
     * @param baseMediaElement Das zugehörige BaseMediaElement.
     * @param animation        Die zugehörige Animation.
     */
    public ElementImage(String baseElementId, BaseMediaElement baseMediaElement, Animation animation)
    {
        this(baseElementId, baseMediaElement.canRotate(), baseMediaElement.getRotationAccuracy(), true, animation);
    }

    /**
     * Erzeugt ein neues ElementImage mit den übergebenen Werten.
     *
     * @param baseElementId         Die ID des BaseElements, zu dem das ElementImage gehören soll.
     * @param canRotateParam        Gibt an, ob das zugehörige BaseElement gedreht werden kann.
     * @param rotationAccuracyParam Gibt an, um wie viel Grad das zugehörige BaseElement gedreht werden kann.
     * @param isAnimation           Gibt am, ob das ElementImage eine Animation ist.
     * @param animation             Die zugehörige Animation.
     */
    private ElementImage(String baseElementId, boolean canRotateParam, int rotationAccuracyParam, boolean isAnimation, Animation animation)
    {
        this.baseElementId = baseElementId;
        this.canRotate = canRotateParam && rotationAccuracyParam != 0;
        this.rotationAccuracy = rotationAccuracyParam <= 0 ? 360 : rotationAccuracyParam;
        this.isAnimation = isAnimation;
        this.animation = animation;

        defaultImagesTop = new String[360 / rotationAccuracy];
        calculateDefaultImages(defaultImagesTop, baseElementId, ImageLayer.TOP);

        defaultImagesBottom = new String[360 / rotationAccuracy];
        calculateDefaultImages(defaultImagesBottom, baseElementId, ImageLayer.BOTTOM);

        if (isAnimation)
        {
            animationImagesTop = new String[360 / rotationAccuracy][];
            calculateAnimationImages(animationImagesTop, baseElementId, ImageLayer.TOP);

            animationImagesBottom = new String[360 / rotationAccuracy][];
            calculateAnimationImages(animationImagesBottom, baseElementId, ImageLayer.BOTTOM);
        }
    }

    /**
     * Berechnet die Defaultbilder.
     *
     * @param images        Das zu füllende ImagePathArray.
     * @param baseElementId Die ID des zugehörigen BaseElements.
     * @param imageLayer    Das ImageLayer der Bilder.
     */
    private void calculateDefaultImages(String[] images, String baseElementId, ImageLayer imageLayer)
    {
        for (int i = 0; i < images.length; i++)
        {
            images[i] = DataPath.pathToElementImage(baseElementId, imageLayer, canRotate, i * rotationAccuracy, false, "", 0);
        }
    }

    /**
     * Berechnet die Animationsbilder.
     *
     * @param images        Das zu füllende ImagePathArray.
     * @param baseElementId Die ID des zugehörigen BaseElements.
     * @param imageLayer    Das ImageLayer der Bilder.
     */
    private void calculateAnimationImages(String[][] images, String baseElementId, ImageLayer imageLayer)
    {
        for (int i = 0; i < images.length; i++)
        {
            images[i] = new String[animation.getFrameCount()];
            for (int framePos = 0; framePos < animation.getFrameCount(); framePos++)
            {
                images[i][framePos] = DataPath.pathToElementImage(baseElementId, imageLayer, canRotate, i * rotationAccuracy, true, animation.getName(), framePos);
            }
        }
    }

    /**
     * Gibt den Pfad zu dem zu diesem ElementImage gehörenden Bild in Abhängigkeit von {@code imageLayer} und {@code rotation} zurück.
     *
     * @param imageLayer Das ImageLayer des Bildes.
     * @param rotation   Die Drehung des Bildes.
     * @param deltaTime  Die Zeit, die seit der Erstellung des Bildes vergangen ist.
     * @return Ein zu diesem ElementImage gehörendes Bild.
     */
    public String getImagePath(ImageLayer imageLayer, int rotation, long deltaTime)
    {
        //Berechnet die Zahl der Bilder, die in der vergangenen Zeit angezeigt werden hätten sollen
        int framePos = isAnimation ? (int) (deltaTime / animation.getDuration()) : 0;
        int rotationLeft = (rotation % 360) - getRestRotation(rotation);
        if (isAnimation && framePos < animation.getFrameCount())
        {
            //Gibt den Pfad des zugehörigen Bildes zurück, falls die Zahl der angezeigten Bilder kleiner als die Zahl der Bilder Animation ist. Die Rotation wird dabei als das nächst
            if (imageLayer == ImageLayer.TOP) return animationImagesTop[rotationLeft / rotationAccuracy][framePos];
            else return animationImagesBottom[rotationLeft / rotationAccuracy][framePos];
        }
        else
        {
            //Gibt den Pfad des zugehörigen Bildes zurück, das kein Teil einer Animation ist.
            if (imageLayer == ImageLayer.TOP) return defaultImagesTop[rotationLeft / rotationAccuracy];
            else return defaultImagesBottom[rotationLeft / rotationAccuracy];
        }
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

    /**
     * Gibt zurück ob das Element aktuell noch eine Animation abspielt.
     *
     * @param deltaTime Die Zeit seit Erzeugung der Animation.
     * @return Ob das Element aktuell noch eine Animation abspielt.
     */
    public boolean isAnimating(long deltaTime)
    {
        if (isAnimation)
        {
            int framePos = (int) (deltaTime / animation.getDuration());
            return framePos < animation.getFrameCount();
        }
        else
        {
            return false;
        }
    }
}