package sep.fimball.model.media;

/**
 * Eine Audioquelle, wie Musik oder Soundeffekt.
 */
public class Sound
{
    /**
     * Name der Audioquelle.
     */
    private String soundName;

    /**
     * Gibt an ob der Sound als Dauerschleife laufen soll.
     */
    private boolean repeating;

    /**
     * Erstellt eine neue Instanz von Sound.
     *
     * @param soundName Name der Audioquelle.
     * @param repeating Gibt an, ob der Sound als Dauerschleife laufen soll.
     */
    public Sound(String soundName, boolean repeating)
    {
        this.soundName = soundName;
        this.repeating = repeating;
    }

    public String getSoundName()
    {
        return soundName;
    }

    public boolean isRepeating()
    {
        return repeating;
    }
}
