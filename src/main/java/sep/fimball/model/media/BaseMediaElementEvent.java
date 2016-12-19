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
    private Optional<Animation> animation;

    /**
     * Der Soundeffekt des jeweiligen Bahnelements.
     */
    private Optional<Sound> sound;

    /**
     * Lädt die Animationen und Soundeffekte aus dem angegebenen Objekt.
     *
     * @param animation Die Animation des jeweiligen Bahnelements.
     * @param soundName Die Animation des jeweiligen Bahnelements.
     */
    public BaseMediaElementEvent(Optional<Animation> animation, Optional<String> soundName)
    {
        this.animation = animation;
        if (soundName.isPresent())
            this.sound = Optional.of(new Sound(soundName.get(), false));
        else
            this.sound = Optional.empty();
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
    public Optional<Sound> getSound()
    {
        return sound;
    }
}
