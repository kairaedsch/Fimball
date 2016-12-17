package sep.fimball.model.handler;

import sep.fimball.model.media.Animation;
import sep.fimball.model.media.BaseMediaElementEvent;

import java.util.Map;
import java.util.Optional;

/**
 * Handler, der bei einer Kollision die Animation des kollidierenden GameElements setzt.
 */
public class AnimationHandler implements ElementHandler
{
    @Override
    public void activateElementHandler(HandlerGameElement element, int colliderId)
    {
        Map<Integer, BaseMediaElementEvent> eventMap = element.getMediaElement().getEventMap();

        if (eventMap.containsKey(colliderId))
        {
            Optional<Animation> animation = eventMap.get(colliderId).getAnimation();
            if (animation.isPresent())
                element.setCurrentAnimation(animation);
        }
    }
}
