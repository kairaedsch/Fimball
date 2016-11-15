package sep.fimball.model;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;
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
    private MapProperty<KeyCode, KeyBinding> keyBindingsMap;

    /**
     * Erzeugt eine neue Instanz von Settings.
     */
    private Settings()
    {
        keyBindingsMap = new SimpleMapProperty<>(FXCollections.observableHashMap());

        keyBindingsMap.put(KeyCode.Y, KeyBinding.LEFT_FLIPPER);
        keyBindingsMap.put(KeyCode.M, KeyBinding.RIGHT_FLIPPER);


        // TODO load settings file here
    }

    /**
     * Serialisiert und speichert die Einstelunngen.
     */
    public void Serialize()
    {

    }

    public double getMasterVolume()
    {
        return masterVolume.get();
    }

    public ReadOnlyDoubleProperty masterVolumeProperty()
    {
        return masterVolume;
    }

    public double getMusicVolume()
    {
        return musicVolume.get();
    }

    public ReadOnlyDoubleProperty musicVolumeProperty()
    {
        return musicVolume;
    }

    public double getSfxVolume()
    {
        return sfxVolume.get();
    }

    public ReadOnlyDoubleProperty sfxVolumeProperty()
    {
        return sfxVolume;
    }

    public Language getLanguage()
    {
        return language.get();
    }

    public ReadOnlyObjectProperty<Language> languageProperty()
    {
        return language;
    }

    public ObservableMap<KeyCode, KeyBinding> getKeyBindingsMap()
    {
        return keyBindingsMap.get();
    }

    public ReadOnlyMapProperty<KeyCode, KeyBinding> keyBindingsMapProperty()
    {
        return keyBindingsMap;
    }
}