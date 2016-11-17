package sep.fimball.model.trigger;

import sep.fimball.model.media.SoundManager;
import sep.fimball.model.media.Sound;
import sep.fimball.model.element.GameElement;

/**
 * TODO
 */
public class SoundTrigger implements ElementTrigger
{
    @Override
    public void activateTrigger(GameElement element, int colliderID)
    {
        Sound soundToPlay = element.getPlacedElement().getBaseElement().getMedia().getEventMap().get(colliderID).getSound();

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
