package sep.fimball.model.blueprint.settings;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.MapProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyMapProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleMapProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.scene.input.KeyCode;
import sep.fimball.general.data.DataPath;
import sep.fimball.general.data.Language;
import sep.fimball.model.blueprint.json.JsonFileManager;
import sep.fimball.model.input.data.KeyBinding;

import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Settings speichert die aktuellen Spieleinstellungen, die vom Spieler im Einstellungsdialog geändert werden können.
 */
public class Settings
{
    /**
     * Stellt sicher, dass es nur eine Instanz von Settings gibt.
     */
    private static Settings singletonInstance;

    /**
     * Gibt an, ob der Vollbild- oder Fenstermodus eingestellt ist.
     */
    private BooleanProperty fullscreen;

    /**
     * Allgemeine Lautstärke, die Werte von musicVolume und sfxVolume sollten mit diesem Wert multipliziert werden.
     */
    private IntegerProperty masterVolume;

    /**
     * Lautstärke der Musik.
     */
    private IntegerProperty musicVolume;

    /**
     * Lautstärke der Soundeffekte.
     */
    private IntegerProperty sfxVolume;

    /**
     * Aktuell ausgewählte Sprache, ein Teil der Aufzählung {@link sep.fimball.general.data.Language}.
     */
    private ObjectProperty<Language> language;

    /**
     * Speichert, welche Taste welches Spielereignis auslöst.
     */
    private MapProperty<KeyCode, KeyBinding> keyBindingsMap;

    /**
     * Erzeugt eine neue, leere Instanz von Settings.
     */
    private Settings()
    {
        Map<KeyCode, KeyBinding> keyBindings = new HashMap<>();
        keyBindings.put(KeyCode.A, KeyBinding.LEFT_FLIPPER);
        keyBindings.put(KeyCode.R, KeyBinding.EDITOR_ROTATE);
        keyBindings.put(KeyCode.E, KeyBinding.NUDGE_RIGHT);
        keyBindings.put(KeyCode.Q, KeyBinding.NUDGE_LEFT);
        keyBindings.put(KeyCode.ESCAPE, KeyBinding.PAUSE);
        keyBindings.put(KeyCode.DELETE, KeyBinding.EDITOR_DELETE);
        keyBindings.put(KeyCode.D, KeyBinding.RIGHT_FLIPPER);
        keyBindings.put(KeyCode.ALT, KeyBinding.EDITOR_MOVE);
        keyBindings.put(KeyCode.SPACE, KeyBinding.PLUNGER);
        keyBindingsMap = new SimpleMapProperty<>(FXCollections.observableMap(keyBindings));
        language = new SimpleObjectProperty<>(Language.GERMAN);
        fullscreen = new SimpleBooleanProperty(false);
        masterVolume = new SimpleIntegerProperty(100);
        musicVolume = new SimpleIntegerProperty(100);
        sfxVolume = new SimpleIntegerProperty(100);
    }

    /**
     * Erzeugt eine neue Instanz von Settings mit den angegebenen Werten.
     */
    Settings(Map<KeyCode, KeyBinding> keyBindings, Language language, boolean fullscreen, int masterVolume, int musicVolume, int sfxVolume)
    {
        keyBindingsMap = new SimpleMapProperty<>(FXCollections.observableMap(keyBindings));
        this.language = new SimpleObjectProperty<>(language);
        this.fullscreen = new SimpleBooleanProperty(fullscreen);
        this.masterVolume = new SimpleIntegerProperty(masterVolume);
        this.musicVolume = new SimpleIntegerProperty(musicVolume);
        this.sfxVolume = new SimpleIntegerProperty(sfxVolume);
    }

    /**
     * Gibt die bereits existierenden Settings oder neu Angelegte zurück, falls
     * noch keine existieren.
     *
     * @return Die Instanz von Settings.
     */
    public static Settings getSingletonInstance()
    {
        if (singletonInstance == null)
        {
            Optional<SettingsJson> settingsOptional = JsonFileManager.loadFromJson(Paths.get(DataPath.pathToSettings()), SettingsJson.class);
            if (settingsOptional.isPresent())
            {
                singletonInstance = SettingsFactory.createSettingsFromJson(settingsOptional.get());
            }
            else
            {
                System.err.println("Settings not loaded");
                singletonInstance = new Settings();
            }
        }
        return singletonInstance;
    }

    /**
     * Serialisiert und speichert die Einstellungen in der Settings-Datei.
     *
     * @param filePath Der Pfad zur Datei, in der die Einstellungen gespeichert werden sollen.
     */
    public void saveToDisk(String filePath)
    {
        SettingsJson settingsJson = SettingsFactory.createJsonFromSettings(this);
        JsonFileManager.saveToJson(filePath, settingsJson);
    }

    /**
     * Gibt die Master-Lautstärke als Property zurück.
     *
     * @return Die Master-Lautstärke.
     */
    public IntegerProperty masterVolumeProperty()
    {
        return masterVolume;
    }

    /**
     * Gibt die Musik-Lautstärke als Property zurück.
     *
     * @return Die Musik-Lautstärke.
     */
    public IntegerProperty musicVolumeProperty()
    {
        return musicVolume;
    }

    /**
     * Gibt die Soundeffekt-Lautstärke als Property zurück.
     *
     * @return Die Soundeffekt-Lautstärke.
     */
    public IntegerProperty sfxVolumeProperty()
    {
        return sfxVolume;
    }

    /**
     * Gibt die eingestellte Sprache als Property zurück.
     *
     * @return Die eingestellte Sprache.
     */
    public ObjectProperty<Language> languageProperty()
    {
        return language;
    }

    /**
     * Gibt die Map, die speichert, welche Taste auf welches durch Tastendruck
     * ausgelöstes Spielereignis gebunden ist, als Property ohne Schreibzugriff
     * zurück.
     *
     * @return Die Map, die speichert, welche Taste auf welches durch Tastendruck ausgelöstes Spielergebnis gebunden
     * ist.
     */
    public ReadOnlyMapProperty<KeyCode, KeyBinding> keyBindingsMapProperty()
    {
        return keyBindingsMap;
    }

    /**
     * Fügt das gegebene KeyBinding zusammen mit dem zugehörigen KeyCode zur
     * Liste der Tastenbelegungen hinzu, falls die durch {@code keyCode}
     * beschriebene Taste nicht schon durch ein anderes KeyBinding belegt ist.
     *
     * @param keyBinding Das KeyBinding, das hinzugefügt werden soll.
     * @param keyCode Der KeyCode, der hinzugefügt werden soll.
     */
    public void setKeyBinding(KeyBinding keyBinding, KeyCode keyCode)
    {
        if (!keyBindingsMap.containsKey(keyCode))
        {
            keyBindingsMap.put(keyCode, keyBinding);
        }
    }

    /**
     * Gibt für das gegebene KeyBinding den entsprechenden KeyCode zurück oder {@code null}, falls kein KeyCode existiert.
     *
     * @param keyBinding Das gesuchte KeyBinding.
     * @return Der KeyCode, der das übergebene Spielereignis auslöst.
     */
    public KeyCode getKeyCode(KeyBinding keyBinding) {
        if(keyBindingsMap.containsValue(keyBinding))
        {
            for(KeyCode keyCode : keyBindingsMap.keySet()) {
                if(keyBindingsMap.get(keyCode).equals(keyBinding)) {
                    return keyCode;
                }
            }
        }
        return null;
    }

    /**
     * Gibt für den gegebenen KeyCode das entsprechende KeyBinding zurück oder
     * {@code null}, falls kein KeyBinding existiert.
     *
     * @param code Der KeyCode der Taste.
     * @return Das KeyBinding, das das auszulösende Spielereignis repräsentiert.
     */
    public KeyBinding getKeyBinding(KeyCode code)
    {
        return keyBindingsMap.get(code);
    }

    /**
     * Gibt die Property zurück, die bestimmt, ob das Spiel im Vollbildmodus angezeigt werden soll.
     *
     * @return Die Property, die {@code true} enthält, falls das Spiel im Vollbildmodus angezeigt werden soll oder
     * {@code false} sonst.
     */
    public BooleanProperty fullscreenProperty()
    {
        return fullscreen;
    }
}