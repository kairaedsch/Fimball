package sep.fimball.model.blueprint.elementtype;

import java.util.Map;

/**
 * Created by kaira on 15.11.2016.
 */
public class RuleElementType
{
    private boolean givesPoints;

    private Map<Integer, RuleElementEvent> eventMap;

    public RuleElementType(boolean givesPoints, Map<Integer, RuleElementEvent> eventMap)
    {
        this.givesPoints = givesPoints;
        this.eventMap = eventMap;
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
