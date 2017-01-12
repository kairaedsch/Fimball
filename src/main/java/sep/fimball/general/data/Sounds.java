package sep.fimball.general.data;

/**
 * Sounds ist eine Aufzählung von Bezeichnungen für Sounds und deren korrespondierenden Dateinamen ohne Endung.
 */
public enum Sounds
{
    /**
     * Der Sound, der im Spiel abgespielt wird.
     */
    GAME("game.mp3"),

    /**
     * Der Sound, der im Hauptmenü abgespielt wird.
     */
    MAIN_MENU("mainmenu.mp3"),

    /**
     * Der Sound, der beim Starten eines Automaten abgespielt wird.
     */
    GAME_START("kai.mp3");

    /**
     * Der Name der Sound-Datei.
     */
    private final String soundName;

    /**
     * Erzeugt ein neues Objekt zur Kennzeichnung des Sounds, das den durch {@code name} gegebenen Sound kennzeichnet.
     *
     * @param soundName Der Name der Sound-Datei.
     */
    Sounds(String soundName)
    {
        this.soundName = soundName;
    }

    /**
     * Gibt den Namen der Sound-Datei zurück.
     *
     * @return Der Name der Sound-Datei.
     */
    public String getSoundName()
    {
        return soundName;
    }
}
