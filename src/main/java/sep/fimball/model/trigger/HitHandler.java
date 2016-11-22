package sep.fimball.model.trigger;

import sep.fimball.model.game.GameElement;

/**
 * Handler, der bei Kollision mit dem Ball ausgelöst wird, und den HitCount eines Elements erhöht.
 */
public class HitHandler implements ElementHandler
{
    @Override
    public void activateElementHandler(GameElement element, int colliderID)
    {
        element.setHitCount(element.getHitCount() + 1);
    }
}
