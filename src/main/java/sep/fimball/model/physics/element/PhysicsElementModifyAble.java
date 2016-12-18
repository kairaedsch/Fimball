package sep.fimball.model.physics.element;

import sep.fimball.general.data.Vector2;
import sep.fimball.model.physics.PhysicsHandler;

/**
 * Ein PhysicsModifyable ist ein PhysicsElement, an welches ein Modify angewandt werden kann.
 *
 * @param <ModifyT> Der Typ des Modifys, welches an das PhysicsElement angewandt werden kann.
 */
public abstract class PhysicsElementModifyAble<GameElementT, ModifyT extends Modify> extends PhysicsElement<GameElementT>
{
    /**
     * Der PhysicsHandler des PhysicsElements.
     */
    protected PhysicsHandler<GameElementT> physicsHandler;

    /**
     * Erstellt eine Instanz von PhysicsElement mit dem zugehörigen GameElement.
     *
     * @param physicsHandler     Der PhysicsHandler des PhysicsElements.
     * @param gameElement        Das zugehörige GameElement, welches von diesem PhysicsElement beeinflusst werden soll.
     * @param position           Die Position des PhysicsElements.
     * @param rotation           Die Rotation des PhysicsElement.
     * @param basePhysicsElement Das korrespondierende BasePhysicsElement.
     */
    public PhysicsElementModifyAble(PhysicsHandler<GameElementT> physicsHandler, GameElementT gameElement, Vector2 position, double rotation, BasePhysicsElement basePhysicsElement)
    {
        super(gameElement, position, rotation, basePhysicsElement);
        this.physicsHandler = physicsHandler;
    }

    /**
     * Wendet das Modify an das PhysicsElement an.
     *
     * @param modify Das anzuwendende Modify.
     */
    public abstract void applyModify(ModifyT modify);

    /**
     * Fügt das Modify zu einer Liste hinzu um es später an das PhysicsElement anzuwenden.
     *
     * @param modify Das anzuwendende Modify.
     */
    public void addModify(ModifyT modify)
    {
        physicsHandler.addModify(this, modify);
    }
}
