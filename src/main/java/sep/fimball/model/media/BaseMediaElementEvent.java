package sep.fimball.model.media;

import java.util.Optional;

/**
 * Stellt Informationen über bei einem Collider ausgelöste Animationen und Soundeffekte bereit, die bei Bedarf abgerufen werden können.
 */
public class BaseMediaElementEvent
{
    /**
     * Die Animation des jeweiligen Collider.
     */
    private Optional<ElementImage> animation;

    /**
     * Der Soundeffekt des jeweiligen Collider.
     */
    private Optional<Sound> sound;

    /**
     * Gibt an, ob der Sound nur abgespielt werden soll, wenn der Ball mit einer erhöhten Geschwindigkeit auf den Collider trifft.
     */
    private boolean soundSpeedRestricted;

    /**
     * Lädt die Animationen und Soundeffekte aus dem angegebenen Objekt.
     *
     * @param animation Die Animation des jeweiligen Collider.
     * @param soundName Der Sound des jeweiligen Collider.
     * @param soundSpeedRestricted Gibt an ob der Sound nur abgespielt werden soll wenn der Ball mit einer erhöhten Geschwindigkeit auf den Collider trifft.
     */
    public BaseMediaElementEvent(Optional<ElementImage> animation, Optional<String> soundName, boolean soundSpeedRestricted)
    {
        this.animation = animation;
        this.soundSpeedRestricted = soundSpeedRestricted;

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
    public Optional<ElementImage> getAnimation()
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

    /**
     * Gibt zurück ob der Sound nur abgespielt werden soll wenn der Ball mit erhöhter Geschwindigkeit auf den Collider trifft.
     *
     * @return Ob der Sound nur abgespielt werden soll wenn der Ball mit erhöhter Geschwindigkeit auf den Collider trifft.
     */
    public boolean isSoundSpeedRestricted()
    {
        return soundSpeedRestricted;
    }
}
