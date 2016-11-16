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
    private String[] framePaths;

    /**
     * Wie lang ein einzelnes Bild angezeigt wird, bevor zum nächsten gewechselt wird. In Sekunden.
     */
    private double duration;

    public Animation(BaseElementJson.MediaElementTypeJson.MediaElementEventJson.AnimationJson animation)
    {
        // TODO load animation
    }

    public String[] getFramePaths()
    {
        return framePaths;
    }

    public double getDuration()
    {
        return duration;
    }
}
