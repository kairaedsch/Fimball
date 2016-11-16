package sep.fimball.model;

import sep.fimball.general.data.Config;

/**
 * Created by kaira on 16.11.2016.
 */
public class ElementImage
{
    private String topImagePath;
    private String bottomImagePath;

    public ElementImage(String elementTypeId)
    {
        this(elementTypeId, false, -1, false, null, -1);
    }

    public ElementImage(String elementTypeId, double rotation)
    {
        this(elementTypeId, true, rotation, false, null, -1);
    }

    public ElementImage(String elementTypeId, String animationName, int animationId)
    {
        this(elementTypeId, false, -1, true, animationName, animationId);
    }

    public ElementImage(String elementTypeId, double rotationAccuracy, String animationName, int animationId)
    {
       this(elementTypeId, true, rotationAccuracy, true, animationName, animationId);
    }

    private ElementImage(String elementTypeId, boolean canRotate, double rotation, boolean animation, String animationName, int animationId)
    {
        topImagePath = Config.pathToElementImage(elementTypeId, true, canRotate, rotation, animation, animationName, animationId);
        bottomImagePath = Config.pathToElementImage(elementTypeId, false, canRotate, rotation, animation, animationName, animationId);
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
