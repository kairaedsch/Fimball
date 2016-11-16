package sep.fimball.model.blueprint.elementtype;

import sep.fimball.model.blueprint.json.ElementTypeJson;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by kaira on 15.11.2016.
 */
public class RuleElementType
{
    private boolean givesPoints;

    private Map<Integer, RuleElementEvent> eventMap;

    public RuleElementType(ElementTypeJson.RuleElementTypeJson ruleElement)
    {
        eventMap = new HashMap<>();
        for (ElementTypeJson.RuleElementTypeJson.RuleElementEventJson event : ruleElement.events)
        {
            // TODO hashCode musst not be unique
            eventMap.put(event.colliderId.hashCode(), new RuleElementEvent(event));
        }
    }

    public boolean isGivesPoints()
    {
        return givesPoints;
    }

    public Map<Integer, RuleElementEvent> getEventMap()
    {
        return eventMap;
    }
}
