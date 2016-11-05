package sep.fimball.model;

import javafx.scene.input.KeyCode;
import sep.fimball.general.Language;

import java.util.HashMap;

public class Settings
{
    private static Settings singletonInstance;

    public static Settings getSingletonInstance()
    {
        return singletonInstance;
    }

    private float masterVolume;
    private float musicVolume;
    private float sfxVolume;
    private Language language;
    private HashMap<KeyCode, KeyBinding> keyBindingsMap;

    private Settings()
    {

    }

    public float getMasterVolume()
    {
        return masterVolume;
    }

    public void setMasterVolume(float masterVolume)
    {
        this.masterVolume = masterVolume;
    }

    public float getMusicVolume()
    {
        return musicVolume;
    }

    public void setMusicVolume(float musicVolume)
    {
        this.musicVolume = musicVolume;
    }

    public float getSfxVolume()
    {
        return sfxVolume;
    }

    public void setSfxVolume(float sfxVolume)
    {
        this.sfxVolume = sfxVolume;
    }

    public Language getLanguage()
    {
        return this.language;
    }

    public void setLanguage(Language language)
    {
        this.language = language;
    }

    public HashMap<KeyCode, KeyBinding> getKeyBindingsMap()
    {
        return keyBindingsMap;
    }
}