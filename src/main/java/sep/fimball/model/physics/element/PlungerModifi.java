package sep.fimball.model.physics.element;

public class PlungerModifi extends Modifi<PlungerPhysicsElement>
{
    private double force;

    public PlungerModifi(PlungerPhysicsElement physicsElement, double force)
    {
        super(physicsElement);
        this.force = force;
    }

    public double getForce()
    {
        return force;
    }
}
