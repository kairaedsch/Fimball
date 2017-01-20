package sep.fimball.model.blueprint.settings;

import javafx.scene.input.KeyCode;
import sep.fimball.general.data.Language;

import java.util.ArrayList;
import java.util.List;

/**
 * Diese Klasse erzeugt aus Settings-Objekten SettingsJson-Objekte und lädt Settings-Objekte aus SettingsJson-Objekten,
 * um die Serialisierung der Einstellungen zu ermöglichen.
 */
class SettingsFactory
{
    /**
     * Erstellt ein SettingsJson-Objekt, das das übergebene Settings-Objekt repräsentiert.
     *
     * @param settings Die zu übertragenden Einstellungen.
     * @return Ein SettingsJson-Objekt, das die übergebenen Einstellungen repräsentiert.
     */
    static SettingsJson createSettingsJson(Settings settings)
    {
        SettingsJson settingsJson = new SettingsJson();
        List<SettingsJson.KeyLayout> keyLayouts = new ArrayList<>();
        for (KeyCode keyCode : settings.keyBindingsMapProperty().keySet())
        {
            SettingsJson.KeyLayout keyLayout = new SettingsJson.KeyLayout();
            keyLayout.keyCode = keyCode.name();
            if (settings.getKeyBinding(keyCode).isPresent())
            {
                keyLayout.keyBinding = settings.getKeyBinding(keyCode).get();
            }
            keyLayouts.add(keyLayout);
        }
        settingsJson.keyLayouts = keyLayouts.toArray(new SettingsJson.KeyLayout[keyLayouts.size()]);
        settingsJson.language = settings.languageProperty().get().toString();
        settingsJson.fullscreen = settings.fullscreenProperty().get();
        settingsJson.masterVolume = settings.masterVolumeProperty().get();
        settingsJson.musicVolume = settings.musicVolumeProperty().get();
        settingsJson.sfxVolume = settings.sfxVolumeProperty().get();
        return settingsJson;
    }


    /**
     * Lädt die in {@code settingsJson} gespeicherten Einstellungen in die aktuellen Einstellungen.
     *
     * @param settingsJson Das Objekt, das die zu setzenden Einstellungen enthält.
     * @param settings     Das Settings-Objekt, in die die serialisierten Einstellungen geladen werden sollen.
     */
    static void fillSettingsFromSettingsJson(SettingsJson settingsJson, Settings settings)
    {
        settings.languageProperty().setValue(Language.valueOf(settingsJson.language));
        settings.fullscreenProperty().setValue(settingsJson.fullscreen);
        settings.masterVolumeProperty().setValue(settingsJson.masterVolume);
        settings.musicVolumeProperty().setValue(settingsJson.musicVolume);
        settings.sfxVolumeProperty().setValue(settingsJson.sfxVolume);
        settings.keyBindingsMapProperty().clear();
        // null checks are missing
        for (SettingsJson.KeyLayout layout : settingsJson.keyLayouts)
        {
            settings.setKeyBinding(KeyCode.valueOf(layout.keyCode), layout.keyBinding);
        }
    }
}
