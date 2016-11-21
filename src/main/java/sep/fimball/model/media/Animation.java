package sep.fimball.model.media;

import sep.fimball.model.blueprint.base.BaseElementJson;

/**
 * Enthält Informationen über die Anzeige einer Animation.
 */
public class Animation
{
    /**
     * Gibt an, ie lang ein einzelnes Bild angezeigt wird, bevor zum nächsten gewechselt wird. In Sekunden.
     */
    private int duration;

    /**
     * Gibt an, aus wie vielen Bildern die Animation besteht.
     */
    private int frameCount;

    /**
     * Der Name der Animation.
     */
    private String name;

    /**
     * Erstellt eine neue Animation.
     *
     * @param animation Das AnimationJson, dessen Informationen übernommen werden sollen.
     */
    public Animation(BaseElementJson.MediaElementJson.MediaElementEventJson.AnimationJson animation)
    {
        duration = animation.duration;
        name = animation.animationName;
        frameCount = animation.frameCount;
    }

    /**
     * Gibt den Wert zurück, wie lang ein einzelnes Bild angezeigt wird, bevor zum nächsten gewechselt wird. In Sekunden.
     *
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
