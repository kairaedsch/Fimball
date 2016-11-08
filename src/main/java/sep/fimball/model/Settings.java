package sep.fimball.model;

import javafx.beans.property.*;
import javafx.collections.ObservableMap;
import javafx.scene.input.KeyCode;
import sep.fimball.general.data.Language;
import sep.fimball.model.input.KeyBinding;

public class Settings
{
    private static Settings singletonInstance;

    public static Settings getSingletonInstance()
    {
        if (singletonInstance != null)
            singletonInstance = new Settings();

        return singletonInstance;
    }

    private DoubleProperty masterVolume;
    private DoubleProperty musicVolume;
    private DoubleProperty sfxVolume;
    private ObjectProperty<Language> language;
    private MapProperty<KeyCode, KeyBinding> keyBindingsMap;

    private Settings()
    {
        // TODO load settings file here
    }

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