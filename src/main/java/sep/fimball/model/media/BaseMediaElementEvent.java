package sep.fimball.model.media;

import java.util.Optional;

/**
 * Stellt Informationen über bei einem Collider ausgelöste Animationen und Soundeffekte bereit, die bei Bedarf abgerufen werden können.
 */
public class BaseMediaElementEvent
{
    /**
     * Die Animation des jeweiligen Bahnelements.
     */
    private Optional<Animation> animation ;

    /**
     * Der Soundeffekt des jeweiligen Bahnelements.
     */
    private Sound sound = null;

    /**
     * Lädt die Animationen und Soundeffekte aus dem angegebenen Objekt.
     * @param animation Die Animation des jeweiligen Bahnelements.
     * @param soundName Die Animation des jeweiligen Bahnelements.
     */
    public BaseMediaElementEvent(Optional<Animation> animation, String soundName)
    {
        this.animation= animation;
        this.sound = new Sound(soundName, false);
    }

    /**
     * Gibt die Animation zurück.
     *
     * @return Die Animation.
     */
    public Optional<Animation> getAnimation()
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
