package sep.fimball.model.media;

import sep.fimball.model.blueprint.base.BaseElementJson;

/**
 * Enthält die Pfade zu den Bild-Dateien einer Animation, und stellt das aktuell angezeigte Bild bereit.
 */
public class Animation
{
    /**
     * Wie lang ein einzelnes Bild angezeigt wird, bevor zum nächsten gewechselt wird. In Sekunden.
     */
    private int duration;

    private int frameCount;

    private String name;

    public Animation(BaseElementJson.MediaElementTypeJson.MediaElementEventJson.AnimationJson animation)
    {
        duration = animation.duration;
        name = animation.animationName;
        frameCount = animation.frameCount;
    }

    /**
     *  Gibt den Wert zurück, wie lang ein einzelnes Bild angezeigt wird, bevor zum nächsten gewechselt wird. In Sekunden.
     * @return Die Anzeigedauer eines einzelnen Bildes in Sekunden.
     */
    public int getDuration()
    {
        return duration;
    }

    public String getName()
    {
        return name;
    }

    public int getFrameCount()
    {
        return frameCount;
    }
}
