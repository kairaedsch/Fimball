package sep.fimball.model.blueprint.elementtype;

/**
 * Created by kaira on 16.11.2016.
 */
public class Sound
{
    private String soundName;

    private boolean repeating;

    public Sound(String soundName, boolean repeating)
    {
        this.soundName = soundName;
        this.repeating = repeating;
    }

    public String getSoundName()
    {
        return soundName;
    }

    public boolean isRepeating()
    {
        return repeating;
    }
}
