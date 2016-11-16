package sep.fimball.model.blueprint.base;

import sep.fimball.model.blueprint.json.BaseElementJson;

/**
 * Created by kaira on 15.11.2016.
 */
public class BaseElement
{
    private String id;

    private BaseElementType type;

    private PhysicsElementType physics;

    private MediaElementType media;

    private RuleElementType rule;

    public BaseElement(String id, BaseElementJson baseElementJson)
    {
        this.id = id;
        type = baseElementJson.elementType;

        physics = new PhysicsElementType(baseElementJson.physicElement);
        media = new MediaElementType(baseElementJson.mediaElement);
        rule = new RuleElementType(baseElementJson.ruleElement);
    }

    public BaseElementType getType()
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

    public String getId()
    {
        return id;
    }
}
