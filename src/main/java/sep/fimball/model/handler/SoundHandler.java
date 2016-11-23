package sep.fimball.model.handler;

import sep.fimball.model.media.BaseMediaElementEvent;
import sep.fimball.model.media.Sound;
import sep.fimball.model.media.SoundManager;

import java.util.Map;

/**
 * Handler, der bei Kollision mit dem Ball ausgelöst wird, und einen SoundClip abspielt.
 */
public class SoundHandler implements ElementHandler
{
    @Override
    public void activateElementHandler(HandlerGameElement element, int colliderID)
    {
        Map<Integer, BaseMediaElementEvent> eventMap = element.getMediaElement().getEventMap();

        if (eventMap.containsKey(colliderID))
        {
            Sound soundToPlay = eventMap.get(colliderID).getSound();

            if (soundToPlay != null)
            {
                SoundManager.getInstance().addSoundToPlay(soundToPlay);
            }
        }
    }
}
