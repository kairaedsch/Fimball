package sep.fimball.model.media;

import sep.fimball.general.data.Config;

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

    /**
     * Giibt den Pfad zur Datei des Sounds zurück.
     * @return Der Pfad zur Datei des Sounds.
     */
    public String getSoundPath()
    {
        return Config.pathToSound(soundName);
    }

    /**
     * Gibt zurück, ob sich der Sound wiederholt.
     * @return {@code true} falls sich der Sound wiederholt, {@code false} sonst.
     */
    public boolean isRepeating()
    {
        return repeating;
    }
}
