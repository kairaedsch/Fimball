package sep.fimball.model.blueprint.base;

import sep.fimball.model.handler.BaseRuleElement;
import sep.fimball.model.handler.BaseRuleElementEvent;

import java.util.HashMap;
import java.util.Map;

import static sep.fimball.model.blueprint.json.JsonUtil.nullCheck;

/**
 * Diese Klasse enthält alle Informationen zu den Physik-Eigenschaften eines BaseElements.
 */
public class BaseRuleElementFactory
{
    /**
     * Generiert ein BaseRuleElement aus dem gegebenen RuleElementJson.
     *
     * @param ruleElement Die Vorlage, aus der das BaseRuleElement erstellt wird.
     * @return Das generierte BaseRuleElement.
     */
    static BaseRuleElement create(BaseElementJson.RuleElementJson ruleElement)
    {
        nullCheck(ruleElement);
        nullCheck(ruleElement.general);

        Map<Integer, BaseRuleElementEvent> eventMap = new HashMap<>();
        if (ruleElement.events != null)
        {
            for (BaseElementJson.RuleElementJson.RuleElementEventJson event : ruleElement.events)
            {
                eventMap.put(event.colliderId, new BaseRuleElementEvent());
            }
        }
        return new BaseRuleElement(ruleElement.general.givesPoints, eventMap);
    }
}
