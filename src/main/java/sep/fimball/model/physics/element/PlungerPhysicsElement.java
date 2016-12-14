package sep.fimball.model.physics.element;

import sep.fimball.general.data.Vector2;

/**
 * Ein PlungerPhysicsElement stellt einen Plunger in der Physik dar.
 *
 * @param <GameElementT>
 */
public class PlungerPhysicsElement<GameElementT> extends PhysicsElement<GameElementT> implements PhysicsUpdateable, PhysicsModifiable<PlungerModify>
{

    double strength = 0;

    /**
     * Erstellt eine Instanz von PlungerPhysicsElement mit dem zugehörigen GameElement.
     *
     * @param gameElement        Das zugehörige GameElement, welches von diesem PhysicsElement beeinflusst werden soll.
     * @param position           Die Position des PhysicsElements.
     * @param rotation           Die Rotation des PhysicsElement.
     * @param basePhysicsElement Das korrespondierende BasePhysicsElement.
     */
    public PlungerPhysicsElement(GameElementT gameElement, Vector2 position, double rotation, BasePhysicsElement
            basePhysicsElement)
    {
        super(gameElement, position, rotation, basePhysicsElement);
    }

    @Override
    public void update(double deltaTime)
    {

    }

    @Override
    public void applyModifi(PlungerModify modifi)
    {
        strength = modifi.getForce();
    }

    public double getStrength()
    {
        return strength;
    }
}
