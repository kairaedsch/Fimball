package sep.fimball.model.physics.element;

import sep.fimball.general.data.Vector2;

public class PlungerPhysicsElement<GameElementT> extends PhysicsElement<GameElementT> implements PhysicsUpdateable
{
    double

    /**
     * Erstellt eine Instanz von PlungerPhysicsElement mit dem zugehörigen GameElement.
     *
     * @param gameElement        Das zugehörige GameElement, welches von diesem PhysicsElement beeinflusst werden soll.
     * @param position           Die Position des PhysicsElements.
     * @param rotation           Die Rotation des PhysicsElement.
     * @param basePhysicsElement Das korrespondierende BasePhysicsElement.
     */
    public PlungerPhysicsElement(GameElementT gameElement, Vector2 position, double rotation, BasePhysicsElement basePhysicsElement)
    {
        super(gameElement, position, rotation, basePhysicsElement);
    }

    @Override
    public void update(double deltaTime)
    {

    }
}
