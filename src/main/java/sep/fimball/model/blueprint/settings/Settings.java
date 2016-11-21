package sep.fimball.model.blueprint.settings;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.scene.input.KeyCode;
import sep.fimball.general.data.Config;
import sep.fimball.general.data.Language;
import sep.fimball.model.blueprint.JsonFileManager;
import sep.fimball.model.input.KeyBinding;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

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
     *
     * @return Die Instanz von Settings.
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
     * Erzeugt eine neue Instanz von Settings, deren Eigenschaften aus der gespeicherten Settings-Datei geladen werden.
     */
    private Settings()
    {
        keyBindingsMap = new SimpleMapProperty<>(FXCollections.observableHashMap());

        language = new SimpleObjectProperty<>();

        fullscreen = new SimpleBooleanProperty(false);

        masterVolume = new SimpleDoubleProperty(100);
        musicVolume = new SimpleDoubleProperty(50);
        sfxVolume = new SimpleDoubleProperty(75);

        loadSettings(Paths.get(Config.pathToSettings()));
    }

    /**
     * Lädt die Einstellungen aus dem in {@code jsonPath} angegebenen Datei und setzt diese als die Attribute.
     *
     * @param jsonPath Der Pfad, wo die gespeicherte Settings-Datei liegt.
     */
    private void loadSettings(Path jsonPath)
    {

        Optional<SettingsJson> settingsOptional = JsonFileManager.loadFromJson(jsonPath, SettingsJson.class);

        if (settingsOptional.isPresent())
        {
            System.out.println("Settings loaded");

            SettingsJson settingsJson = settingsOptional.get();
            language.setValue(Language.valueOf(settingsJson.language));
            fullscreen.setValue(settingsJson.fullscreen);
            masterVolume.set(settingsJson.masterVolume);
            musicVolume.set(settingsJson.musicVolume);
            sfxVolume.set(settingsJson.sfxVolume);

            for (SettingsJson.KeyLayout layout : settingsJson.keyLayouts)
            {
                keyBindingsMap.put(KeyBinding.valueOf(layout.bindingName), KeyCode.valueOf(layout.keyCode));
            }
        }
        else
        {
            System.err.println("Settings not loaded");
        }
    }

    /**
     * Serialisiert und speichert die Einstelunngen in der Settings-Datei.
     */
    public void Serialize()
    {
        SettingsJson settingsJson = new SettingsJson();
        settingsJson.language = language.get().toString();
        settingsJson.fullscreen = fullscreen.get();
        settingsJson.masterVolume = masterVolume.get();
        settingsJson.musicVolume = musicVolume.get();
        settingsJson.sfxVolume = sfxVolume.get();
        settingsJson.keyLayouts = new SettingsJson.KeyLayout[keyBindingsMap.size()];

        int counter = 0;
        for (KeyBinding binding : keyBindingsMap.keySet())
        {
            settingsJson.keyLayouts[counter] = new SettingsJson.KeyLayout();
            settingsJson.keyLayouts[counter].bindingName = binding.name();
            settingsJson.keyLayouts[counter].keyCode = keyBindingsMap.get(binding).name();
            counter++;
        }

        JsonFileManager.saveToJson(Config.pathToSettings(), settingsJson);
    }

    /**
     * Gibt das Property der Master-Lautstärke zurück.
     *
     * @return Das Property der Master-Lautstärke.
     */
    public DoubleProperty masterVolumeProperty()
    {
        return masterVolume;
    }

    /**
     * Gibt das Property der Musik-Lautstärke zurück.
     *
     * @return Das Property der Musik-Lautstärke.
     */
    public DoubleProperty musicVolumeProperty()
    {
        return musicVolume;
    }

    /**
     * Gibt das Property der Soundeffekt-Lautstärke zurück.
     *
     * @return Das Property der Soundeffekt-Lautstärke.
     */
    public DoubleProperty sfxVolumeProperty()
    {
        return sfxVolume;
    }

    /**
     * Gibt das Property der eingestellten Sprache zurück.
     *
     * @return Das Property der eingestellten Sprache.
     */
    public ObjectProperty<Language> languageProperty()
    {
        return language;
    }

    /**
     * Gibt das Property der Map, die speichert, welche Taste auf welches durch Tastendruck ausgelöstes Spielergebnis gebunden ist, als ReadOnly zurück.
     *
     * @return Das Property der Map, die speichert, welche Taste auf welches durch Tastendruck ausgelöstes Spielergebnis gebunden ist.
     */
    public ReadOnlyMapProperty<KeyBinding, KeyCode> keyBindingsMapProperty()
    {
        return keyBindingsMap;
    }

    /**
     * Fügt das gegebene KeyBinding zusammen mit dem zugehörigen KeyCode zur Liste der Tastenbelegungen hinzu, falls die durch {@code keycode} beschriebene Taste nicht schon von einem anderen KeyBinding belegt ist.
     *
     * @param keyBinding
     * @param keyCode
     */
    public void setKeyBinding(KeyBinding keyBinding, KeyCode keyCode)
    {
        if (!keyBindingsMap.containsValue(keyCode))
        {
            keyBindingsMap.put(keyBinding, keyCode);
        }
    }

    /**
     * Gibt das Property zurück, das angibt, ob das Spiel im Vollbildmodus angzeigt werden soll, zurück.
     *
     * @return Das Property, das angibt, ob das Spiel im Vollbildmodus angzeigt werden soll, zurück.
     */
    public BooleanProperty fullscreenProperty()
    {
        return fullscreen;
    }

    public KeyBinding getKeyBinding(KeyCode code)
    {
        for (KeyBinding binding : keyBindingsMap.keySet())
        {
            if (keyBindingsMap.get(binding).equals(code))
            {
                return binding;
            }
        }
        return null;
    }
}