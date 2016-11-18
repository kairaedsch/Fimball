package sep.fimball.model.blueprint.base;

import sep.fimball.model.media.Animation;
import sep.fimball.model.media.Sound;

/**
 * Stellt Informationen über ausgelöste Animationen und Soundeffekte bereit, die bei Bedarf abgerufen werden können.
 */
public class MediaElementEvent
{
    private Animation animation = null;
    private Sound sound = null;

    /**
     * Lädt die Animationen und Soundeffekte aus dem angegebenen Objekt.
     *
     * @param event Das Objekt, in dem die Informationen über Animation und Soundeffekt gespeichert sind.
     */
    public MediaElementEvent(BaseElementJson.MediaElementTypeJson.MediaElementEventJson event)
    {
        if (event.animation != null) animation = new Animation(event.animation);
        if (event.soundName != null) sound = new Sound(event.soundName, false);
    }

    /**
     * Gibt die Animation zurück.
     *
     * @return Die Animation.
     */
    public Animation getAnimation()
    {
        return animation;
    }

    /**
     * Gibt den Soundeffekt zurück.
     *
     * @return Der Soundeffekt.
     */
    public Sound getSound()
    {
        return sound;
    }
}
