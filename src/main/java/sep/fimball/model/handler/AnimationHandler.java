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
    public void activateElementHandler(HandlerGameElement element, ElementHandlerArgs elementHandlerArgs)
    {
        Map<Integer, BaseMediaElementEvent> eventMap = element.getMediaElement().getEventMap();

        // Falls eine Animation für diesen Collider vorliegt, wird diese aktiviert
        if (eventMap.containsKey(elementHandlerArgs.getColliderId()))
        {
            Optional<ElementImage> animation = eventMap.get(elementHandlerArgs.getColliderId()).getAnimation();

            if (animation.isPresent())
                element.setCurrentAnimation(animation);
        }
    }
}
