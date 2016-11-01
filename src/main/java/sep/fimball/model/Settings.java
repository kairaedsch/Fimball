package sep.fimball.model;

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
}