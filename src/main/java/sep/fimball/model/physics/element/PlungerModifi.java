package sep.fimball.model.physics.element;

public class PlungerModifi extends Modifi
{
    private double force;

    public PlungerModifi(double force)
    {
        this.force = force;
    }

    public double getForce()
    {
        return force;
    }
}
