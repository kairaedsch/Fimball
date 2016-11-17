package sep.fimball.model.blueprint.base;

import sep.fimball.model.media.Animation;
import sep.fimball.model.media.Sound;

/**
 * Created by kaira on 15.11.2016.
 */
public class MediaElementEvent
{
    private Animation animation = null;
    private Sound sound = null;

    public MediaElementEvent(BaseElementJson.MediaElementTypeJson.MediaElementEventJson event)
    {
        if (event.animation != null) animation = new Animation(event.animation);
        if (event.soundName != null) sound = new Sound(event.soundName, false);
    }

    public Animation getAnimation()
    {
        return animation;
    }

    public Sound getSound()
    {
        return sound;
    }
}
