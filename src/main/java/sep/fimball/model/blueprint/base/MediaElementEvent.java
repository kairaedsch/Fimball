package sep.fimball.model.blueprint.base;

import sep.fimball.model.Animation;
import sep.fimball.model.blueprint.json.BaseElementJson;

/**
 * Created by kaira on 15.11.2016.
 */
public class MediaElementEvent
{
    private Animation animation;
    private Sound sound;

    public MediaElementEvent(BaseElementJson.MediaElementTypeJson.MediaElementEventJson event)
    {
        animation = new Animation(event.animation);
        sound = new Sound(event.soundName, false);
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
