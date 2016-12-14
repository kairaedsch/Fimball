package sep.fimball.model.physics.element;

public class Modifi<PhysicsModifiableT extends PhysicsModifiable>
{
    private PhysicsModifiableT physicsElement;

    public Modifi(PhysicsModifiableT physicsElement)
    {
        this.physicsElement = physicsElement;
    }

    public PhysicsModifiableT getPhysicsElement()
    {
        return physicsElement;
    }
}
