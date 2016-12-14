package sep.fimball.model.physics.element;

/**
 * Ein PhysicsModifiable ist ein PhysicsElement, an welches ein Modifi angewandt werden kann.
 *
 * @param <ModifiT> Der Typ des Modifis, welches an das PhysicsElement angewandt werden kann.
 */
public interface PhysicsModifiable<ModifiT extends Modify>
{
    /**
     * Wendet das Modifi an das PhysicsElement an.
     *
     * @param modifi Das anzuwendende Modifi.
     */
    void applyModifi(ModifiT modifi);
}
