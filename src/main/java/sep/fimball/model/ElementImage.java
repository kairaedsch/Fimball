package sep.fimball.model;

import sep.fimball.general.data.Config;
import sep.fimball.general.data.ImageLayer;

/**
 * Created by kaira on 16.11.2016.
 */
public class ElementImage
{
    private final int rotationAccuracy;
    private String elementTypeId;
    private boolean canRotate;
    private boolean animation;
    private String animationName;
    private int animationId;

    public ElementImage(String elementTypeId)
    {
        this(elementTypeId, false, 360, false, null, -1);
    }

    public ElementImage(String elementTypeId, int rotationAccuracy)
    {
        this(elementTypeId, true, rotationAccuracy, false, null, -1);
    }

    public ElementImage(String elementTypeId, String animationName, int animationId)
    {
        this(elementTypeId, false, 360, true, animationName, animationId);
    }

    public ElementImage(String elementTypeId, int rotationAccuracy, String animationName, int animationId)
    {
       this(elementTypeId, true, rotationAccuracy, true, animationName, animationId);
    }

    private ElementImage(String elementTypeId, boolean canRotate, int rotationAccuracy, boolean animation, String animationName, int animationId)
    {
        this.elementTypeId = elementTypeId;
        this.canRotate = canRotate;
        this.rotationAccuracy = rotationAccuracy;
        this.animation = animation;
        this.animationName = animationName;
        this.animationId = animationId;
    }

    public String getImagePath(ImageLayer imageLayer, int rotation)
    {
        return Config.pathToElementImage(elementTypeId, imageLayer, canRotate, rotation - (rotation % rotationAccuracy), animation, animationName, animationId);
    }

    public int getRestRotation(int rotation)
    {
        return rotation % rotationAccuracy;
    }
}
