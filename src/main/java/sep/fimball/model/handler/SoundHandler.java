package sep.fimball.model.handler;

import sep.fimball.model.blueprint.base.BaseElementCategory;
import sep.fimball.model.blueprint.base.BaseElementType;
import sep.fimball.model.media.BaseMediaElement;
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

        if (elementHandlerArgs.getCollisionEventType() == CollisionEventType.ENTERED)
        {
            if (element.getElementType() == BaseElementType.NORMAL && elementHandlerArgs.getDepth() >= minimumDepthForSound)
            {
                playSound(element, elementHandlerArgs.getColliderId());
            }
            if (element.getElementType() != BaseElementType.NORMAL)
            {
                playSound(element, elementHandlerArgs.getColliderId());
            }
        }
    }

    private void playSound(HandlerGameElement element, int colliderId)
    {
        Map<Integer, BaseMediaElementEvent> eventMap = element.getMediaElement().getEventMap();

        if (eventMap.containsKey(colliderId))
        {
            Optional<Sound> soundToPlay = eventMap.get(colliderId).getSound();

            soundToPlay.ifPresent(sound -> soundManager.addSoundToPlay(sound));
        }
    }
}
