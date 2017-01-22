package sep.fimball.model.handler;

import sep.fimball.model.physics.game.CollisionEventType;

/**
 * Handler, der bei Kollision mit dem Ball ausgelöst wird, und den HitCount eines Elements erhöht.
 */
public class HitHandler implements ElementHandler
{
    @Override
    public void activateElementHandler(HandlerGameElement element, CollisionEventType collisionEventType, int colliderID)
    {
        element.setHitCount(element.getHitCount() + 1);
    }
}
