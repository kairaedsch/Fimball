package sep.fimball.model.blueprint.base;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by kaira on 15.11.2016.
 */
public class BaseRuleElement
{
    private boolean givesPoints;

    private Map<Integer, RuleElementEvent> eventMap;

    public BaseRuleElement(BaseElementJson.RuleElementTypeJson ruleElement)
    {
        eventMap = new HashMap<>();
        if (ruleElement.events != null)
        {
            for (BaseElementJson.RuleElementTypeJson.RuleElementEventJson event : ruleElement.events)
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
