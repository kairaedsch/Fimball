package sep.fimball.model.physics.element;

/**
 * Ein PhysicsModifyable ist ein PhysicsElement, an welches ein Modify angewandt werden kann.
 *
 * @param <ModifyT> Der Typ des Modifys, welches an das PhysicsElement angewandt werden kann.
 */
public interface PhysicsModifyAble<ModifyT extends Modify>
{
    /**
     * Wendet das Modify an das PhysicsElement an.
     *
     * @param modify Das anzuwendende Modify.
     */
    void applyModify(ModifyT modify);
}
