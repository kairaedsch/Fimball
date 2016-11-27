package sep.fimball.general.data;

public enum Sounds
{
    /**
     * Der Sound, der im Spiel abgespielt wird.
     */
    GAME("game"),

    /**
     * Der Sound, der im Hauptmenü abgespielt wird.
     */
    MAIN_MENU("mainmenu");

    /**
     * Der Name der Sound-Datei.
     */
    String soundName;

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
     * @return  Der Name der Sound-Datei.
     */
    public String getSoundName()
    {
        return soundName;
    }
}
