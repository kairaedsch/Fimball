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
    public void activateElementHandler(HandlerGameElement element, ElementHandlerArgs elementHandlerArgs)
    {
        double minimumDepthForSound = 0.01;
        int colliderId = elementHandlerArgs.getColliderId();

        if (elementHandlerArgs.getCollisionEventType() == CollisionEventType.ENTERED)
        {
            Map<Integer, BaseMediaElementEvent> eventMap = element.getMediaElement().getEventMap();

            if (eventMap.containsKey(colliderId))
            {
                Optional<Sound> soundToPlay = eventMap.get(colliderId).getSound();
                boolean soundRestricted = eventMap.get(colliderId).isSoundSpeedRestricted();

                if (!soundRestricted)
                {
                    soundToPlay.ifPresent(sound -> soundManager.addSoundToPlay(sound));
                }
                else if (elementHandlerArgs.getDepth() >= minimumDepthForSound)
                {
                    soundToPlay.ifPresent(sound -> soundManager.addSoundToPlay(sound));
                }
            }
        }
    }
}
