package sep.fimball.model;

public class SettingsJson
{
    public String language;
    public boolean fullscreen;
    public double masterVolume;
    public double musicVolume;
    public double sfxVolume;

    public KeyLayout[] keyLayouts;

    public static class KeyLayout {
        String bindingName;
        String keyCode;
    }

}
