package sep.fimball.model.physics.element;

import sep.fimball.general.data.Vector2;

/**
 * Ein PlungerPhysicsElement stellt einen Plunger in der Physik dar.
 *
 * @param <GameElementT>
 */
public class PlungerPhysicsElement<GameElementT> extends PhysicsElement<GameElementT> implements PhysicsUpdateAble, PhysicsModifyAble<PlungerModify>
{
    /**
     * Gibt die Stärke an mit der der Plunger den Ball wegschießen soll.
     */
    private double strength = 0;

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

    @Override
    public void applyModify(PlungerModify modify)
    {
        strength = modify.getForce();
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

    /**
     * Setzt die Stärke mit der der Plunger den Ball wegschießen soll auf null zurück.
     */
    public void resetStrength()
    {
        strength = 0;
    }
}
