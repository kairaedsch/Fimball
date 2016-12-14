package sep.fimball.model.physics.element;

/**
 * Ein ModifiContainer speichert ein Modifi mit seinem PhysicsElement, an welchen das Modifi angewandt werden kann.
 *
 * @param <ModifiT>
 * @param <PhysicsModifiableT>
 */
public class ModifiContainer<ModifiT extends Modify, PhysicsModifiableT extends PhysicsModifiable<ModifiT>>
{
    /**
     * Das PhysicsElement, an welches das Modifi angewandt werden kann.
     */
    private PhysicsModifiableT physicsElement;

    /**
     * Das Modifi, welches an das PhysicsElement angewandt werden kann.
     */
    private ModifiT modifi;

    /**
     * Erstellt ein neuen ModifiContainer.
     *
     * @param physicsElement Das PhysicsElement, an welches das Modifi angewandt werden kann.
     * @param modifi         Das Modifi, welches an das PhysicsElement angewandt werden kann.
     */
    public ModifiContainer(PhysicsModifiableT physicsElement, ModifiT modifi)
    {
        this.physicsElement = physicsElement;
        this.modifi = modifi;
    }

    /**
     * Wendet das Modifi an das PhysicsElement an.
     */
    public void apply()
    {
        physicsElement.applyModifi(modifi);
    }
}
