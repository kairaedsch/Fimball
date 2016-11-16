package sep.fimball.model.blueprint.elementtype;

import sep.fimball.model.blueprint.json.ElementTypeJson;

/**
 * Created by kaira on 15.11.2016.
 */
public class ElementType
{
    private ElementTypeType type;

    private PhysicsElementType physics;

    private MediaElementType media;

    private RuleElementType rule;

    public ElementType(ElementTypeJson elementTypeJson)
    {
        type = elementTypeJson.elementType;

        physics = new PhysicsElementType(elementTypeJson.physicElement);
        media = new MediaElementType(elementTypeJson.mediaElement);
        rule = new RuleElementType(elementTypeJson.ruleElement);
    }

    public ElementTypeType getType()
    {
        return type;
    }

    public PhysicsElementType getPhysics()
    {
        return physics;
    }

    public MediaElementType getMedia()
    {
        return media;
    }

    public RuleElementType getRule()
    {
        return rule;
    }
}
