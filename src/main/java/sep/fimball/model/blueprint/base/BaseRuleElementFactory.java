package sep.fimball.model.blueprint.base;

import sep.fimball.model.handler.BaseRuleElement;
import sep.fimball.model.handler.BaseRuleElementEvent;

import java.util.HashMap;
import java.util.Map;

/**
 * Diese Klasse enthält alle Informationen zu den Physik-Eigenschaften eines BaseElements.
 */
public class BaseRuleElementFactory
{
    static BaseRuleElement generate(BaseElementJson.RuleElementJson ruleElement)
    {
        Map<Integer, BaseRuleElementEvent> eventMap = new HashMap<>();
        if (ruleElement.events != null)
        {
            for (BaseElementJson.RuleElementJson.RuleElementEventJson event : ruleElement.events)
            {
                // TODO hashCode musst not be unique
                eventMap.put(event.colliderId.hashCode(), new BaseRuleElementEvent());
            }
        }
        return new BaseRuleElement(ruleElement.general.givesPoints, eventMap);
    }
}
