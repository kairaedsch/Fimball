package sep.fimball.model.trigger;

import sep.fimball.model.element.GameElement;

/**
 * Trigger, der bei Kollision mit dem Ball ausgelöst wird, und den HitCount eines Elements erhöht.
 */
public class HitTrigger implements ElementTrigger
{
    @Override
    public void activateTrigger(GameElement element, int colliderID)
    {
        element.setHitCount(element.getHitCount() + 1);
    }
}
