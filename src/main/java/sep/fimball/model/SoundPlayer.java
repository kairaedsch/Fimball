package sep.fimball.model;

public class SoundPlayer
{
    private SoundClip currentClip;

    public SoundPlayer(SoundClip currentClip)
    {
        this.currentClip = currentClip;
    }

    public SoundClip getCurrentClip()
    {
        return currentClip;
    }

    public void setCurrentClip(SoundClip currentClip)
    {
        this.currentClip = currentClip;
    }
}