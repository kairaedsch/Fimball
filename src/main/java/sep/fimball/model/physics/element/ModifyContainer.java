package sep.fimball.model.physics.element;

/**
 * Ein ModifyContainer speichert ein Modify mit seinem PhysicsElement, an welchen das Modify angewandt werden kann.
 *
 * @param <ModifyT>
 * @param <PhysicsModifyableT>
 */
public class ModifyContainer<ModifyT extends Modify, PhysicsModifyableT extends PhysicsElementModifyAble<?, ModifyT>>
{
    /**
     * Das PhysicsElement, an welches das Modify angewandt werden kann.
     */
    private PhysicsModifyableT physicsElement;

    /**
     * Das Modify, welches an das PhysicsElement angewandt werden kann.
     */
    private ModifyT modify;

    /**
     * Erstellt ein neuen ModifyContainer.
     *
     * @param physicsElement Das PhysicsElement, an welches das Modify angewandt werden kann.
     * @param modify         Das Modify, welches an das PhysicsElement angewandt werden kann.
     */
    public ModifyContainer(PhysicsModifyableT physicsElement, ModifyT modify)
    {
        this.physicsElement = physicsElement;
        this.modify = modify;
    }

    /**
     * Wendet das Modify an das PhysicsElement an.
     */
    public void apply()
    {
        physicsElement.applyModify(modify);
    }
}
