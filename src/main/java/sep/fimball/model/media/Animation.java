package sep.fimball.model.media;

import sep.fimball.model.blueprint.base.BaseElementJson;

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
    private int duration;

    public Animation(BaseElementJson.MediaElementTypeJson.MediaElementEventJson.AnimationJson animation)
    {
        duration = animation.duration;

        elementImages = new ElementImage[animation.frameCount];
        for(int f = 0; f < animation.frameCount; f++)
        {
            //elementImages[f] = new ElementImage();
        }
    }

    /**
     * Gibt das Array mit den Pfaden der einzelnen Bilder der Animation zurück.
     * @return Ein Array  mit den Pfaden der einzelnen Bilder der Animation.
     */
    public ElementImage[] getElementImages()
    {
        return elementImages;
    }

    /**
     *  Gibt den Wert zurück, wie lang ein einzelnes Bild angezeigt wird, bevor zum nächsten gewechselt wird. In Sekunden.
     * @return Die Anzeigedauer eines einzelnen Bildes in Sekunden.
     */
    public double getDuration()
    {
        return duration;
    }
}
