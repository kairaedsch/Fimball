package sep.fimball.model.blueprint.settings;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.scene.input.KeyCode;
import sep.fimball.general.data.Config;
import sep.fimball.general.data.Language;
import sep.fimball.model.blueprint.JsonFileManager;

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
     * Gibt an ob der Vollbild- oder Fenstermodus eingestellt ist.
     */
    private BooleanProperty fullscreen;

    /**
     * Allgemeine Lautstärke von 0-100\%, die Werte von musicVolume und sfxVolume werden mit diesem Wert multipliziert.
     */
    private IntegerProperty masterVolume;

    /**
     * Lautstärke der Musik von 0-100%.
     */
    private IntegerProperty musicVolume;

    /**
     * Lautstärke der Soundeffekte von 0-100%.
     */
    private IntegerProperty sfxVolume;

    /**
     * Aktuell ausgewählte Sprache, ein Teil der Aufzählung <enum> Language.
     */
    private ObjectProperty<Language> language;

    /**
     * Speichert, welche Taste auf welches durch Tastendruck ausgelöstes Spielereignis gebunden ist.
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

        masterVolume = new SimpleIntegerProperty();
        musicVolume = new SimpleIntegerProperty();
        sfxVolume = new SimpleIntegerProperty();

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
     * Serialisiert und speichert die Einstellungen in der Settings-Datei.
     */
    public void saveToDisk()
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
     * Gibt die Master-Lautstärke zurück.
     *
     * @return Die Master-Lautstärke.
     */
    public IntegerProperty masterVolumeProperty()
    {
        return masterVolume;
    }

    /**
     * Gibt die Musik-Lautstärke zurück.
     *
     * @return Die Musik-Lautstärke.
     */
    public IntegerProperty musicVolumeProperty()
    {
        return musicVolume;
    }

    /**
     * Gibt die Soundeffekt-Lautstärke zurück.
     *
     * @return Die Soundeffekt-Lautstärke.
     */
    public IntegerProperty sfxVolumeProperty()
    {
        return sfxVolume;
    }

    /**
     * Gibt die eingestellte Sprache zurück.
     *
     * @return Die eingestellte Sprache.
     */
    public ObjectProperty<Language> languageProperty()
    {
        return language;
    }

    /**
     * Gibt die Map, die speichert, welche Taste auf welches durch Tastendruck ausgelöstes Spielergebnis gebunden ist, zurück.
     *
     * @return Die Map, die speichert, welche Taste auf welches durch Tastendruck ausgelöstes Spielergebnis gebunden ist.
     */
    public ReadOnlyMapProperty<KeyBinding, KeyCode> keyBindingsMapProperty()
    {
        return keyBindingsMap;
    }

    /**
     * Fügt das gegebene KeyBinding zusammen mit dem zugehörigen KeyCode zur Liste der Tastenbelegungen hinzu, falls die durch {@code keyCode} beschriebene Taste nicht schon von einem anderen KeyBinding belegt ist.
     *
     * @param keyBinding Das KeyBinding, das hinzugefügt werden soll.
     * @param keyCode    Der KeyCode, der hinzugefügt werden soll.
     */
    public void setKeyBinding(KeyBinding keyBinding, KeyCode keyCode)
    {
        if (!keyBindingsMap.containsValue(keyCode))
        {
            keyBindingsMap.put(keyBinding, keyCode);
        }
    }

    /**
     * Gibt für den gegebenen KeyCode das entsprechende KeyBinding zurück.
     *
     * @param code Der KeyCode für den das entsprechende KeyBinding gesucht werden soll
     * @return Das KeyBinding welches zum übergebenen KeyCode passt
     */
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

    /**
     * Gibt zurück, ob das Spiel im Vollbildmodus angezeigt werden soll.
     *
     * @return {@code true}, falls das Spiel im Vollbildmodus angezeigt werden soll, {@code false} sonst.
     */
    public BooleanProperty fullscreenProperty()
    {
        return fullscreen;
    }
}