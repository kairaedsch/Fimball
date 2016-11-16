package sep.fimball.model.element;

import sep.fimball.model.SoundManager;
import sep.fimball.model.blueprint.base.Sound;

/**
 * Created by TheAsuro on 15.11.2016.
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
