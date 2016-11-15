package sep.fimball.model.blueprint.elementtype;

import sep.fimball.model.Animation;
import sep.fimball.model.SoundClip;

/**
 * Created by kaira on 15.11.2016.
 */
public class MediaElementEvent
{
    Animation animation;
    SoundClip soundClip;

    public MediaElementEvent(Animation animation, SoundClip soundClip)
    {
        this.animation = animation;
        this.soundClip = soundClip;
    }

    public Animation getAnimation()
    {
        return animation;
    }

    public SoundClip getSoundClip()
    {
        return soundClip;
    }
}
