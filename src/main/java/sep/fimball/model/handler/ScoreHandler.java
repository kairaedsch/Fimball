package sep.fimball.model.handler;

import javafx.beans.property.LongProperty;
import sep.fimball.model.blueprint.base.BaseElementType;

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
                && element.getElementType() != BaseElementType.SPINNER)
        {
            if (shouldElementGivePoints(element))
            {
                session.getCurrentPlayer().addPoints(element.getPointReward());
            }
        }
    }

    /**
     * Gibt zurück, ob das GameElement Punkte geben soll.
     *
     * @param handlerGameElement Das zu überprüfende GameElement.
     * @return Ob das GameElement Punkte geben soll.
     */
    private boolean shouldElementGivePoints(HandlerGameElement handlerGameElement)
    {
        int pointResetTime = handlerGameElement.getRuleElement().getPointResetTime();
        LongProperty lastTimeHit = handlerGameElement.lastTimeHitProperty();
        long currentMillis = System.currentTimeMillis();

        if (pointResetTime > 0)
        {
            if (currentMillis - lastTimeHit.get() >= pointResetTime)
            {
                lastTimeHit.set(currentMillis);
                return true;
            }
            else
            {
                return false;
            }
        }
        else
        {
            return true;
        }
    }
}
