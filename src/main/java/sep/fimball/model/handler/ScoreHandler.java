package sep.fimball.model.handler;

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
    public void activateElementHandler(HandlerGameElement element, int colliderID)
    {
        Map<Integer, BaseRuleElementEvent> eventMap = element.getRuleElement().getEventMap();

        if (eventMap.containsKey(colliderID) && eventMap.get(colliderID).givesPoints())
            session.getCurrentPlayer().addPoints(element.getPointReward());
    }
}
