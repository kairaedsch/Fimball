package sep.fimball.model.handler;

import java.util.Collections;
import java.util.Map;

/**
 * Diese Klasse enthält alle Informationen zu den Regel-Eigenschaften eines BaseElements.
 */
public class BaseRuleElement
{
    /**
     * Gibt an, wie viel Millisekunden vergehen müssen, bis das Element wieder Punkte geben kann.
     */
    private int pointResetTime;

    /**
     * Enthält die RuleElementEvents zusammen mit der Id des zugehörigen Colliders, bei dem sie eintreten können.
     */
    private Map<Integer, BaseRuleElementEvent> eventMap;

    /**
     * Erstellt ein neues BaseRuleElement.
     *
     * @param eventMap       Enthält die RuleElementEvents zusammen mit der Id des zugehörigen Colliders, bei dem sie eintreten können.
     * @param pointResetTime Wie viel Millisekunden vergehen müssen, bis das Element wieder Punkte gibt.
     */
    public BaseRuleElement(Map<Integer, BaseRuleElementEvent> eventMap, int pointResetTime)
    {
        this.eventMap = eventMap;
        this.pointResetTime = pointResetTime;
    }

    /**
     * Gibt die RuleElementEvents zusammen mit der Id des zugehörigen Colliders, bei dem sie eintreten können, zurück.
     *
     * @return RuleElementEvents zusammen mit der Id des zugehörigen Colliders.
     */
    public Map<Integer, BaseRuleElementEvent> getEventMap()
    {
        return Collections.unmodifiableMap(eventMap);
    }

    /**
     * Gibt an, wie viel Millisekunden vergehen müssen, bis das Element wieder Punkte geben kann.
     *
     * @return Wie viel Millisekunden vergehen müssen, bis das Element wieder Punkte gibt.
     */
    public int getPointResetTime()
    {
        return pointResetTime;
    }
}
