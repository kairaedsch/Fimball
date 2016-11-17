package sep.fimball.model.trigger;

import sep.fimball.model.element.GameElement;

/**
 * Trigger, der bei einer Kollision die Animation des kollidierenden GameElements setzt.
 */
public class AnimationTrigger implements ElementTrigger
{
    @Override
    public void activateTrigger(GameElement element, int colliderId)
    {
        element.currentAnimationProperty().set(element.getPlacedElement().getBaseElement().getMedia().getEventMap().get(colliderId).getAnimation());
    }
}
