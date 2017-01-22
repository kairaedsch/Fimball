package sep.fimball.model.handler;

import sep.fimball.model.media.BaseMediaElementEvent;
import sep.fimball.model.media.Sound;
import sep.fimball.model.media.SoundManager;
import sep.fimball.model.physics.game.CollisionEventType;

import java.util.Map;
import java.util.Optional;

/**
 * Handler, der bei Kollision mit dem Ball ausgelöst wird, und einen SoundClip abspielt.
 */
public class SoundHandler implements ElementHandler
{
    /**
     * Der SoundManager, an den die Sounds weitergeben werden.
     */
    private SoundManager soundManager;

    /**
     * Erstellt einen neuen SoundHandler.
     *
     * @param soundManager Der SoundManager, an den die Sounds weitergeben werden.
     */
    SoundHandler(SoundManager soundManager)
    {
        this.soundManager = soundManager;
    }

    @Override
    public void activateElementHandler(HandlerGameElement element, CollisionEventType collisionEventType, int colliderID)
    {
        Map<Integer, BaseMediaElementEvent> eventMap = element.getMediaElement().getEventMap();

        if (eventMap.containsKey(colliderID))
        {
            Optional<Sound> soundToPlay = eventMap.get(colliderID).getSound();

            soundToPlay.ifPresent(sound -> soundManager.addSoundToPlay(sound));
        }
    }
}
