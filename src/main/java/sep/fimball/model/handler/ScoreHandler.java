package sep.fimball.model.handler;

import sep.fimball.model.blueprint.base.BaseElementType;
import sep.fimball.model.physics.game.CollisionEventType;

import java.util.Map;

/**
 * Handler, der bei Kollision mit dem Ball ausgelöst wird, und die Punkte des aktuellen Spielers erhöht.
 */
public class ScoreHandler implements ElementHandler
{
    /**
     * Aktuelle GameSession.
     */
    private HandlerGameSession session;

    /**
     * Erstellt einen neuen Handler.
     *
     * @param session Aktuelle GameSession.
     */
    ScoreHandler(HandlerGameSession session)
    {
        this.session = session;
    }


    @Override
    public void activateElementHandler(HandlerGameElement element, ElementHandlerArgs elementHandlerArgs)
    {
        Map<Integer, BaseRuleElementEvent> eventMap = element.getRuleElement().getEventMap();

        if (eventMap.containsKey(elementHandlerArgs.getColliderId()) && eventMap.get(elementHandlerArgs.getColliderId()).givesPoints()
                && element.getElementType() != BaseElementType.SPINNER && elementHandlerArgs.getCollisionEventType() == CollisionEventType.ENTERED)
        {
                session.getCurrentPlayer().addPoints(element.getPointReward());
        }
    }
}
