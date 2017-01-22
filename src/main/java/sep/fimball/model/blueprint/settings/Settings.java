package sep.fimball.model.blueprint.settings;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.scene.input.KeyCode;
import sep.fimball.general.data.DataPath;
import sep.fimball.general.data.Language;
import sep.fimball.model.blueprint.json.JsonFileManager;
import sep.fimball.model.input.data.KeyBinding;

import java.nio.file.Paths;
import java.util.AbstractMap.SimpleEntry;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
        keyBindingsMap = new SimpleMapProperty<>(FXCollections.observableMap(getDefaultBindings()));
        language = new SimpleObjectProperty<>(Language.ENGLISH);
        fullscreen = new SimpleBooleanProperty(false);
        masterVolume = new SimpleIntegerProperty(100);
        musicVolume = new SimpleIntegerProperty(100);
        sfxVolume = new SimpleIntegerProperty(100);
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
                singletonInstance = new Settings();
                SettingsFactory.fillSettingsFromSettingsJson(settingsOptional.get(), singletonInstance);
            }
            else
            {
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
        SettingsJson settingsJson = SettingsFactory.createSettingsJson(this);
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
     * Fügt das gegebene KeyBinding zusammen mit dem zugehörigen KeyCode zur Liste der Tastenbelegungen hinzu, falls die
     * durch {@code keyCode} beschriebene Taste nicht schon durch ein anderes KeyBinding belegt ist und löscht
     * gegebenenfalls die vorhandene Belegung des übergebenen KeyBindings.
     *
     * @param keyCode    Der KeyCode der Taste, an die das Spielereignis gebunden werden soll.
     * @param keyBinding Das Spielereignis, an das eine Teste gebunden werden soll.
     */
    public void setKeyBinding(KeyCode keyCode, KeyBinding keyBinding)
    {
        if (keyBindingsMap.get(keyCode) == null)
        {
            if (getKeyCode(keyBinding).isPresent())
            {
                keyBindingsMap.remove(getKeyCode(keyBinding).get());
            }
            if (keyBinding != null)
            {
                keyBindingsMap.put(keyCode, keyBinding);
            }
        }
    }

    /**
     * Gibt für das gegebene KeyBinding den entsprechenden KeyCode zurück, falls er existiert.
     *
     * @param keyBinding Das gesuchte KeyBinding.
     * @return Der KeyCode, der das übergebene Spielereignis auslöst.
     */
    public Optional<KeyCode> getKeyCode(KeyBinding keyBinding)
    {
        if (keyBindingsMap.containsValue(keyBinding))
        {
            for (KeyCode keyCode : keyBindingsMap.keySet())
            {
                if (keyBindingsMap.get(keyCode).equals(keyBinding))
                {
                    return Optional.of(keyCode);
                }
            }
        }
        return Optional.empty();
    }

    /**
     * Gibt für den gegebenen KeyCode das entsprechende KeyBinding zurück, falls es existiert.
     *
     * @param code Der KeyCode der Taste.
     * @return Das KeyBinding, das das auszulösende Spielereignis repräsentiert.
     */
    public Optional<KeyBinding> getKeyBinding(KeyCode code)
    {
        return keyBindingsMap.get(code) == null ? Optional.empty() : Optional.of(keyBindingsMap.get(code));
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

    /**
     * Erzeugt die Standardeinstellung für Tastendrücke.
     *
     * @return Die Standardbelegung der Tastatur.
     */
    private Map<KeyCode, KeyBinding> getDefaultBindings()
    {
        return Stream.of(new SimpleEntry<>(KeyCode.A, KeyBinding.LEFT_FLIPPER), new SimpleEntry<>(KeyCode.R, KeyBinding.EDITOR_ROTATE), new SimpleEntry<>(KeyCode.INSERT, KeyBinding.EDITOR_DUPLICATE), new SimpleEntry<>(KeyCode.E, KeyBinding.NUDGE_RIGHT), new SimpleEntry<>(KeyCode.Q, KeyBinding.NUDGE_LEFT), new SimpleEntry<>(KeyCode.ESCAPE, KeyBinding.PAUSE), new SimpleEntry<>(KeyCode.DELETE, KeyBinding.EDITOR_DELETE), new SimpleEntry<>(KeyCode.D, KeyBinding.RIGHT_FLIPPER), new SimpleEntry<>(KeyCode.ALT, KeyBinding.EDITOR_MOVE), new SimpleEntry<>(KeyCode.SPACE, KeyBinding.PLUNGER)).collect(Collectors.toMap(SimpleEntry::getKey, SimpleEntry::getValue));
    }
}