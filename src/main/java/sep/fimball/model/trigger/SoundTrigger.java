package sep.fimball.model.trigger;

import sep.fimball.model.blueprint.base.MediaElementEvent;
import sep.fimball.model.element.GameElement;
import sep.fimball.model.media.Sound;
import sep.fimball.model.media.SoundManager;

import java.util.Map;

/**
 * Trigger, der bei Kollision mit dem Ball ausgelöst wird, und einen SoundClip abspielt.
 */
public class SoundTrigger implements ElementTrigger
{
    @Override
    public void activateElementTrigger(GameElement element, int colliderID)
    {
        Map<Integer, MediaElementEvent> eventMap = element.getPlacedElement().getBaseElement().getMedia().getEventMap();

        if (eventMap.containsKey(colliderID))
        {
            Sound soundToPlay = eventMap.get(colliderID).getSound();

            if (soundToPlay != null)
            {
                if (soundToPlay.isRepeating())
                {
                    SoundManager.getInstance().addMediaToPlay(soundToPlay.getSoundName());
                }
                else
                {
                    SoundManager.getInstance().addClipToPlay(soundToPlay.getSoundName());
                }
            }
        }
    }
}
