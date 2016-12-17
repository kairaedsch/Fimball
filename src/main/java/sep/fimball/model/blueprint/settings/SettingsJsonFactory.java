package sep.fimball.model.blueprint.settings;

import javafx.scene.input.KeyCode;

import java.util.ArrayList;
import java.util.List;

/**
 * Diese Klasse erzeugt aus Settings-Objekten SettingsJson-Objekte, um die Serialisierung der Einstellungen zu ermöglichen.
 */
class SettingsJsonFactory
{
    /**
     * Erstellt ein SettingsJson-Objekt, das das übergebene Settings-Objekt repräsentiert.
     *
     * @param settings Die zu übertragenden Einstellungen.
     * @return Ein SettingsJson-Objekt, das die übergebenen Einstellungen repräsentiert.
     */
    static SettingsJson createJsonFromSettings(Settings settings)
    {
        SettingsJson settingsJson = new SettingsJson();
        List<SettingsJson.KeyLayout> keyLayouts = new ArrayList<>();
        for (KeyCode keyCode : settings.keyBindingsMapProperty().keySet())
        {
            SettingsJson.KeyLayout keyLayout = new SettingsJson.KeyLayout();
            keyLayout.keyCode = keyCode.name();
            keyLayout.keyBinding = settings.getKeyBinding(keyCode);
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
}
