package sep.fimball.model.blueprint.elementtype;

/**
 * Created by kaira on 15.11.2016.
 */
public class ElementType
{
    private ElementTypeType type;

    private PhysicsElementType physics;

    private MediaElementType media;

    private RuleElementType rule;

    public ElementType(ElementTypeType type, PhysicsElementType physics, MediaElementType media, RuleElementType rule)
    {
        this.type = type;
        this.physics = physics;
        this.media = media;
        this.rule = rule;
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
