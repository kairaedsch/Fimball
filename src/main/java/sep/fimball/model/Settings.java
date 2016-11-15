package sep.fimball.model;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.scene.input.KeyCode;
import sep.fimball.general.data.Language;
import sep.fimball.model.input.KeyBinding;

/**
 * Settings speichert die aktuellen Spieleinstellungen, welche vom Spieler in den Einstellungen geändert werden können.
 */
public class Settings
{
    /**
     * Stellt sicher, dass es nur eine Instanz von Settings gibt.
     */
    private static Settings singletonInstance;

    /**
     * Gibt die bereits existierenden Settings oder neu angelegte zurück, falls noch keine existieren.
     * @return
     */
    public static Settings getSingletonInstance()
    {
        if (singletonInstance == null)
            singletonInstance = new Settings();

        return singletonInstance;
    }

    /**
     * Vollbild- oder Fenstermodus.
     */
    private BooleanProperty fullscreen;

    /**
     * Allgemeine Lautstärke von 0-100\%, die Werte von musicVolume und sfxVolume werden mit diesem Wert multipliziert.
     */
    private DoubleProperty masterVolume;

    /**
     * Lautstärke der Musik von 0-100%.
     */
    private DoubleProperty musicVolume;

    /**
     * Lautstärke der Soundeffekte von 0-100%.
     */
    private DoubleProperty sfxVolume;

    /**
     * Aktuell ausgewählte Sprache, ein Teil der Aufzählung <enum> Language.
     */
    private ObjectProperty<Language> language;

    /**
     * Speichert, welche Taste auf welches durch Tastendruck ausgelöstes Spielergebnis gebunden ist.
     */
    private MapProperty<KeyBinding, KeyCode> keyBindingsMap;

    /**
     * Erzeugt eine neue Instanz von Settings.
     */
    private Settings()
    {
        keyBindingsMap = new SimpleMapProperty<>(FXCollections.observableHashMap());

        keyBindingsMap.put(KeyBinding.LEFT_FLIPPER, KeyCode.Y);
        keyBindingsMap.put(KeyBinding.RIGHT_FLIPPER, KeyCode.M);

        language = new SimpleObjectProperty<>();
        language.setValue(Language.ENGLISH);

        fullscreen = new SimpleBooleanProperty(false);

        masterVolume = new SimpleDoubleProperty(100);
        musicVolume = new SimpleDoubleProperty(50);
        sfxVolume = new SimpleDoubleProperty(75);

        // TODO load settings file here
    }

    /**
     * Serialisiert und speichert die Einstelunngen.
     */
    public void Serialize()
    {

    }

    public DoubleProperty masterVolumeProperty()
    {
        return masterVolume;
    }

    public DoubleProperty musicVolumeProperty()
    {
        return musicVolume;
    }

    public DoubleProperty sfxVolumeProperty()
    {
        return sfxVolume;
    }

    public ObjectProperty<Language> languageProperty()
    {
        return language;
    }

    public ReadOnlyMapProperty<KeyBinding, KeyCode> keyBindingsMapProperty()
    {
        return keyBindingsMap;
    }

    public void setKeyBinding(KeyBinding keyBinding, KeyCode keyCode)
    {
        keyBindingsMap.put(keyBinding, keyCode);
    }

    public BooleanProperty fullscreenProperty()
    {
        return fullscreen;
    }
}