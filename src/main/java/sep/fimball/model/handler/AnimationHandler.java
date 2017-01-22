package sep.fimball.model.handler;

import sep.fimball.model.media.BaseMediaElementEvent;
import sep.fimball.model.media.ElementImage;

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

        // Falls eine Animation für diesen Collider vorliegt, wird diese aktiviert
        if (eventMap.containsKey(colliderId))
        {
            Optional<ElementImage> animation = eventMap.get(colliderId).getAnimation();

            if (animation.isPresent())
                element.setCurrentAnimation(animation);
        }
    }
}
