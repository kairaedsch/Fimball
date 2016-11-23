package sep.fimball.model.blueprint.base;

import java.util.HashMap;
import java.util.Map;

/**
 * Diese Klasse enthält alle Informationen zu den Regel-Eigenschaften eines BaseElements.
 */
public class BaseRuleElement
{
    /**
     * Gibt an, ob ein Zusammenprallen der Kugel mit dem BaseElement Punkte bringt.
     */
    private boolean givesPoints;

    /**
     * Enthält die RuleElementEvents zusammen mit der Id des zugehörigen Colliders, bei dem sie eintreten können.
     */
    private Map<Integer, BaseRuleElementEvent> eventMap;

    /**
     * Erstellt ein neues BaseRuleElement.
     *
     * @param ruleElement Das RuleElementJson, dessen Eigenschaften übernommen werden sollen.
     */
    public BaseRuleElement(BaseElementJson.RuleElementJson ruleElement)
    {
        eventMap = new HashMap<>();
        if (ruleElement.events != null)
        {
            for (BaseElementJson.RuleElementJson.RuleElementEventJson event : ruleElement.events)
            {
                // TODO hashCode musst not be unique
                eventMap.put(event.colliderId.hashCode(), new BaseRuleElementEvent(event));
            }
        }
    }

    /**
     * Gibt zurück, ob ein Zusammenprallen der Kugel mit dem BaseElement Punkte bringt.
     * @return {@code true} falls ein Treffer Punkte bringt, {@code false} sonst.
     */
    public boolean givesPoints()
    {
        return givesPoints;
    }

    /**
     * Gibt die RuleElementEvents zusammen mit der Id des zugehörigen Colliders, bei dem sie eintreten können, zurück.
     * @return RuleElementEvents zusammen mit der Id des zugehörigen Colliders.
     */
    public Map<Integer, BaseRuleElementEvent> getEventMap()
    {
        return eventMap;
    }
}
