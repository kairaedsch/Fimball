package sep.fimball.model.physics.element;

import sep.fimball.general.data.Vector2;
import sep.fimball.model.physics.PhysicsHandler;

/**
 * Ein PlungerPhysicsElement stellt einen Plunger in der Physik dar.
 *
 * @param <GameElementT>
 */
public class PlungerPhysicsElement<GameElementT> extends PhysicsElementModifyAble<GameElementT, PlungerModify>
{
    /**
     * Gibt die Stärke an mit der der Plunger den Ball wegschießen soll.
     */
    private double strength;

    /**
     * Erstellt eine Instanz von PlungerPhysicsElement mit dem zugehörigen GameElement.
     *
     * @param physicsHandler     Der PhysicsHandler des PhysicsElements.
     * @param gameElement        Das zugehörige GameElement, welches von diesem PhysicsElement beeinflusst werden soll.
     * @param position           Die Position des PhysicsElements.
     * @param rotation           Die Rotation des PhysicsElement.
     * @param basePhysicsElement Das korrespondierende BasePhysicsElement.
     */
    public PlungerPhysicsElement(PhysicsHandler<GameElementT> physicsHandler, GameElementT gameElement, Vector2 position, double rotation, BasePhysicsElement basePhysicsElement)
    {
        super(physicsHandler, gameElement, position, rotation, basePhysicsElement);
        strength = 0;
    }

    /**
     * Gibt die Stärke zurück mit der der Plunger den Ball wegschießen soll.
     *
     * @return Die Stärke mit der der Plunger den Ball wegschießen soll.
     */
    public double getStrength()
    {
        return strength;
    }

    @Override
    public void applyModify(PlungerModify modify)
    {
        strength = modify.getForce();
    }
}
