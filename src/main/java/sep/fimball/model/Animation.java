package sep.fimball.model;

import sep.fimball.model.blueprint.json.BaseElementJson;

/**
 * Enthält die Pfade zu den Bild-Dateien einer Animation, und stellt das aktuell angezeigte Bild bereit.
 */
public class Animation
{
    /**
     * Array mit den Pfaden der einzelnen Bilder der Animation.
     */
    private ElementImage[] elementImages;

    /**
     * Wie lang ein einzelnes Bild angezeigt wird, bevor zum nächsten gewechselt wird. In Sekunden.
     */
    private double duration;

    public Animation(BaseElementJson.MediaElementTypeJson.MediaElementEventJson.AnimationJson animation)
    {
        duration = animation.duration;

        elementImages = new ElementImage[animation.frameCount];
        for(int f = 0; f < animation.frameCount; f++)
        {
            //elementImages[f] = new ElementImage();
        }
    }

    public ElementImage[] getElementImages()
    {
        return elementImages;
    }

    public double getDuration()
    {
        return duration;
    }
}
