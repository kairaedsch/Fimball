package sep.fimball.model.physics.element;

/**
 * Ein ModifyContainer speichert ein Modify mit seinem PhysicsElement, an welchen das Modify angewandt werden kann.
 *
 * @param <ModifyT>            Der Typ des Modify's, welches an das PhysicsElement angewandt werden kann.
 * @param <PhysicsModifiableT> Der Typ des PhysicsElements, an welches das Modify angewandt werden kann.
 */
public class ModifyContainer<ModifyT extends Modify, PhysicsModifiableT extends PhysicsElementModifyAble<?, ModifyT>>
{
    /**
     * Das PhysicsElement, an welches das Modify angewandt werden kann.
     */
    private PhysicsModifiableT physicsElement;

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
    public ModifyContainer(PhysicsModifiableT physicsElement, ModifyT modify)
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
