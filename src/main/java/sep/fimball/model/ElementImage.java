package sep.fimball.model;

import sep.fimball.general.data.Config;
import sep.fimball.general.data.ImageLayer;

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

    public ElementImage(String elementTypeId, int rotation)
    {
        this(elementTypeId, true, rotation, false, null, -1);
    }

    public ElementImage(String elementTypeId, String animationName, int animationId)
    {
        this(elementTypeId, false, -1, true, animationName, animationId);
    }

    public ElementImage(String elementTypeId, int rotation, String animationName, int animationId)
    {
       this(elementTypeId, true, rotation, true, animationName, animationId);
    }

    private ElementImage(String elementTypeId, boolean canRotate, int rotation, boolean animation, String animationName, int animationId)
    {
        topImagePath = Config.pathToElementImage(elementTypeId, ImageLayer.TOP, canRotate, rotation, animation, animationName, animationId);
        bottomImagePath = Config.pathToElementImage(elementTypeId, ImageLayer.BOTTOM, canRotate, rotation, animation, animationName, animationId);
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
