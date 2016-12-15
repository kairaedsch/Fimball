package sep.fimball.model.blueprint.settings;

import javafx.scene.input.KeyCode;
import sep.fimball.general.data.Language;
import sep.fimball.model.input.data.KeyBinding;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Diese Klasse enthält Methoden zur Umwandlung von Settings- in SettingsJson-Objekte und umgekehrt.
 */
class SettingsFactory
{
    /**
     * Erstellt Einstellungen mit den Werten aus {@code settingsJson}.
     *
     * @param settingsJson Das Objekt, das die zu setzenden Einstellungen enthält.
     * @return Das aus dem SettingsJson-Objekt geladene Settings-Objekt.
     */
    static Settings createSettingsFromJson(SettingsJson settingsJson)
    {
        HashMap<KeyCode, KeyBinding> keyBindings = new HashMap<>();
        for (SettingsJson.KeyLayout layout : settingsJson.keyLayouts)
        {
            keyBindings.put(KeyCode.valueOf(layout.keyCode), layout.keyBinding);
        }
        return new Settings(keyBindings, Language.valueOf(settingsJson.language), settingsJson.fullscreen, settingsJson.masterVolume, settingsJson.musicVolume, settingsJson.sfxVolume);
    }

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
