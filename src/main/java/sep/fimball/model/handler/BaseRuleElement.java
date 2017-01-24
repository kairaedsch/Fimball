package sep.fimball.model.handler;

import java.util.Collections;
import java.util.Map;

/**
 * Diese Klasse enthält alle Informationen zu den Regel-Eigenschaften eines BaseElements.
 */
public class BaseRuleElement
{
    /**
     * Enthält die RuleElementEvents zusammen mit der Id des zugehörigen Colliders, bei dem sie eintreten können.
     */
    private Map<Integer, BaseRuleElementEvent> eventMap;

    /**
     * Erstellt ein neues BaseRuleElement.
     *
     * @param eventMap       Enthält die RuleElementEvents zusammen mit der Id des zugehörigen Colliders, bei dem sie eintreten können.
     */
    public BaseRuleElement(Map<Integer, BaseRuleElementEvent> eventMap)
    {
        this.eventMap = eventMap;
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
}
