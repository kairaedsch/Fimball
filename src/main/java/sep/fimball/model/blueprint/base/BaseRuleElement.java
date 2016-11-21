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
     * Enthält die RuleElementEvents. TODO
     */
    private Map<Integer, RuleElementEvent> eventMap;

    /**
     * Erstellt ein neues BaseRuleElement.
     *
     * @param ruleElement Das RuleElementJson, dessen Eigenschaften übernpmmen werden sollen.
     */
    public BaseRuleElement(BaseElementJson.RuleElementJson ruleElement)
    {
        eventMap = new HashMap<>();
        if (ruleElement.events != null)
        {
            for (BaseElementJson.RuleElementJson.RuleElementEventJson event : ruleElement.events)
            {
                // TODO hashCode musst not be unique
                eventMap.put(event.colliderId.hashCode(), new RuleElementEvent(event));
            }
        }
    }

    public boolean givesPoints()
    {
        return givesPoints;
    }

    public Map<Integer, RuleElementEvent> getEventMap()
    {
        return eventMap;
    }
}
