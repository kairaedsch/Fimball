package sep.fimball.model.physics.element;

public class ModifiContainer<ModifiT extends Modifi, PhysicsModifiableT extends PhysicsModifiable<ModifiT>>
{
    private PhysicsModifiableT physicsElement;
    private ModifiT modifi;

    public ModifiContainer(PhysicsModifiableT physicsElement, ModifiT modifi)
    {
        this.physicsElement = physicsElement;
        this.modifi = modifi;
    }

    public void apply()
    {
        physicsElement.applyModifi(modifi);
    }
}
