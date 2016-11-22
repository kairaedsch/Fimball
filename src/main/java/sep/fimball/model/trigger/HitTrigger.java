package sep.fimball.model.trigger;

import sep.fimball.model.game.GameElement;

/**
 * Trigger, der bei Kollision mit dem Ball ausgelöst wird, und den HitCount eines Elements erhöht.
 */
public class HitTrigger implements ElementTrigger
{
    @Override
    public void activateElementTrigger(GameElement element, int colliderID)
    {
        element.setHitCount(element.getHitCount() + 1);
    }
}
