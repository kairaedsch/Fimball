package sep.fimball.model.physics.element;

import sep.fimball.general.data.Vector2;
import sep.fimball.model.game.GameElement;

/**
 * Created by alexcekay on 15.01.17.
 */
public class SpinnerPhysicsElement extends PhysicsElement<GameElement>
{
    private double spinnerAcceleration;
    private final Object spinnerMonitor = new Object();

    /**
     * Erstellt eine Instanz von PhysicsElement mit dem zugehörigen GameElement.
     *
     * @param gameElement        Das zugehörige GameElement, welches von diesem PhysicsElement beeinflusst werden soll.
     * @param position           Die Position des PhysicsElements.
     * @param rotation           Die Rotation des PhysicsElement.
     * @param basePhysicsElement Das korrespondierende BasePhysicsElement.
     */
    public SpinnerPhysicsElement(GameElement gameElement, Vector2 position, double rotation, BasePhysicsElement basePhysicsElement)
    {
        super(gameElement, position, rotation, 1.0, basePhysicsElement);
    }

    public double getSpinnerAcceleration()
    {
        return spinnerAcceleration;
    }

    public void setSpinnerAcceleration(double acceleration)
    {
        //TODO: Find better sync
        synchronized (spinnerMonitor)
        {
            this.spinnerAcceleration = acceleration;
        }
    }
}
