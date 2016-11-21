package sep.fimball.model.trigger;

import sep.fimball.model.blueprint.base.MediaElementEvent;
import sep.fimball.model.element.GameElement;
import sep.fimball.model.media.Animation;

import java.util.Map;

/**
 * Trigger, der bei einer Kollision die Animation des kollidierenden GameElements setzt.
 */
public class AnimationTrigger implements ElementTrigger
{
    @Override
    public void activateElementTrigger(GameElement element, int colliderId)
    {
        Map<Integer, MediaElementEvent> eventMap = element.getPlacedElement().getBaseElement().getMedia().getEventMap();

        if (eventMap.containsKey(colliderId))
        {
            Animation animation = eventMap.get(colliderId).getAnimation();
            if (animation != null)
                element.setCurrentAnimation(java.util.Optional.of(animation));
        }
    }
}
