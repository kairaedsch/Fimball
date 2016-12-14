package sep.fimball.model.physics.element;

public interface PhysicsModifiable<ModifiT extends Modifi>
{
    void applyModifi(ModifiT modifi);
}
