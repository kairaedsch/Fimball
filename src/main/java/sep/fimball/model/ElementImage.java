package sep.fimball.model;

import sep.fimball.general.data.Config;

/**
 * Created by kaira on 16.11.2016.
 */
public class ElementImage
{
    String topImagePath;
    String bottomImagePath;

    public ElementImage(String elementTypeId)
    {
        this(elementTypeId, true, false, -1, false, null, -1);
    }

    public ElementImage(String elementTypeId, double rotationAccuracy)
    {
        this(elementTypeId, true, true, rotationAccuracy, false, null, -1);
    }

    public ElementImage(String elementTypeId, String animationName, int animationId)
    {
        this(elementTypeId, true, false, -1, true, animationName, animationId);
    }

    public ElementImage(String elementTypeId, double rotationAccuracy, String animationName, int animationId)
    {
       this(elementTypeId, true, true, rotationAccuracy, true, animationName, animationId);
    }

    private ElementImage(String elementTypeId, boolean top, boolean canRotate, double rotationAccuracy, boolean animation, String animationName, int animationId)
    {
        topImagePath = Config.pathToElementImage(elementTypeId, true, canRotate, rotationAccuracy, animation, animationName, animationId);
        bottomImagePath = Config.pathToElementImage(elementTypeId, true, canRotate, rotationAccuracy, animation, animationName, animationId);
    }

    public String getTopImagePath()
    {
        return topImagePath;
    }

    public String getBottomImagePath()
    {
        return bottomImagePath;
    }
}
