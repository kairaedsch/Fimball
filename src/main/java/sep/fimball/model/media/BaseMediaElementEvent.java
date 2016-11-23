package sep.fimball.model.media;

/**
 * Stellt Informationen über bei einem Collider ausgelöste Animationen und Soundeffekte bereit, die bei Bedarf abgerufen werden können.
 */
public class BaseMediaElementEvent
{
    /**
     * Hält gegebenenfalls die Animation des jeweiligen Bahnelements.
     */
    private Animation animation = null;

    /**
     * Hält gegebenenfalls den Soundeffekt des jeweiligen Bahnelements.
     */
    private Sound sound = null;

    /**
     * Lädt die Animationen und Soundeffekte aus dem angegebenen Objekt.
     */
    public BaseMediaElementEvent(Animation animation, String soundName)
    {
        this.animation = animation;
        this.sound = new Sound(soundName, false);
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
