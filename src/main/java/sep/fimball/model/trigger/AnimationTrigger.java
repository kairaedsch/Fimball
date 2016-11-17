package sep.fimball.model.trigger;

import sep.fimball.model.element.GameElement;

/**
 * TODO
 */
public class AnimationTrigger implements ElementTrigger
{
    @Override
    public void activateTrigger(GameElement element, int colliderId)
    {
        element.currentAnimationProperty().set(element.getPlacedElement().getBaseElement().getMedia().getEventMap().get(colliderId).getAnimation());
    }
}
