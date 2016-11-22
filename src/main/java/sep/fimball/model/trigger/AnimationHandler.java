package sep.fimball.model.trigger;

import sep.fimball.model.blueprint.base.BaseMediaElementEvent;
import sep.fimball.model.game.GameElement;
import sep.fimball.model.media.Animation;

import java.util.Map;

/**
 * Handler, der bei einer Kollision die Animation des kollidierenden GameElements setzt.
 */
public class AnimationHandler implements ElementHandler
{
    @Override
    public void activateElementHandler(GameElement element, int colliderId)
    {
        Map<Integer, BaseMediaElementEvent> eventMap = element.getPlacedElement().getBaseElement().getMedia().getEventMap();

        if (eventMap.containsKey(colliderId))
        {
            Animation animation = eventMap.get(colliderId).getAnimation();
            if (animation != null)
                element.setCurrentAnimation(java.util.Optional.of(animation));
        }
    }
}
