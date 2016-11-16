package sep.fimball.model;

/**
 * Diese Klasse repräsentiert die JSON-Beschreibung der Settings.
 */
public class SettingsJson
{
    /**
     * Speichert die Sprache.
     */
    public String language;

    /**
     * Speichert, ob FIMBall im Vollbildmodus angezeigt wird.
     */
    public boolean fullscreen;

    /**
     * Speichert die Master-Lautstärke.
     */
    public double masterVolume;

    /**
     * Speichert die Musik-Lautstärke.
     */
    public double musicVolume;

    /**
     * Speichert die Soundeffekt-Lautstärke.
     */
    public double sfxVolume;

    /**
     * Speichert die Tastenbelegungen für Benutzeraktionen von FIMBall.
     */
    public KeyLayout[] keyLayouts;

    /**
     * Ein KeyLaout stellt eine Tastenbelegung in den Einstellungen  zur Speicherung in einer JSON-Datei dar.
     */
    public static class KeyLayout
    {
        /**
         * Speichert den Namen der Funktion, die das Drücken der entsprechenden Taste auslösen soll.
         */
        public String bindingName;

        /**
         * Speichert den KeyCode der Taste, die an eine Funktion gebunden ist.
         */
        public String keyCode;
    }

}
