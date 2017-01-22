package sep.fimball.model.blueprint.settings;

import javafx.scene.input.KeyCode;
import sep.fimball.general.data.Language;
import sep.fimball.model.input.data.KeyBinding;

/**
 * Diese Klasse repräsentiert die JSON-Beschreibung der Settings.
 */
public class SettingsJson
{
    /**
     * Speichert die Sprache.
     */
    public Language language;

    /**
     * Speichert, ob FIMBall im Vollbildmodus angezeigt wird.
     */
    public boolean fullscreen;

    /**
     * Speichert die Master-Lautstärke.
     */
    public int masterVolume;

    /**
     * Speichert die Musik-Lautstärke.
     */
    public int musicVolume;

    /**
     * Speichert die Soundeffekt-Lautstärke.
     */
    public int sfxVolume;

    /**
     * Speichert die Tastenbelegungen für Benutzeraktionen von FIMBall.
     */
    public KeyLayout[] keyLayouts;

    /**
     * Ein KeyLayout stellt eine Tastenbelegung in den Einstellungen zur Speicherung in einer JSON-Datei dar.
     */
    public static class KeyLayout
    {
        /**
         * Speichert den KeyCode der Taste, die an eine Funktion gebunden ist.
         */
        public KeyCode keyCode;

        /**
         * Speichert den Namen der Funktion, die das Drücken der entsprechenden Taste auslösen soll.
         */
        public KeyBinding keyBinding;
    }

}
