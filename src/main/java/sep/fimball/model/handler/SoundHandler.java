package sep.fimball.model.handler;

import sep.fimball.model.media.BaseMediaElementEvent;
import sep.fimball.model.media.Sound;
import sep.fimball.model.media.SoundManager;

import java.util.Map;
import java.util.Optional;

/**
 * Handler, der bei Kollision mit dem Ball ausgelöst wird, und einen SoundClip abspielt.
 */
public class SoundHandler implements ElementHandler
{
    private SoundManager soundManager;

    public SoundHandler(SoundManager soundManager) {
        this.soundManager = soundManager;
    }

    @Override
    public void activateElementHandler(HandlerGameElement element, int colliderID)
    {
        Map<Integer, BaseMediaElementEvent> eventMap = element.getMediaElement().getEventMap();

        if (eventMap.containsKey(colliderID))
        {
            Optional<Sound> soundToPlay = eventMap.get(colliderID).getSound();

            soundToPlay.ifPresent(sound -> soundManager.addSoundToPlay(sound));
        }
    }
}
