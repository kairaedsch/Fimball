package sep.fimball.model;

/**
 * Created by wuebbers on 07.11.16.
 */
public class PhysicsForce
{
    private double force;
    private ForceType type;

    public PhysicsForce(double force, ForceType type)
    {
        this.force = force;
        this.type = type;
    }

    public double getForce()
    {
        return force;
    }

    public ForceType getType()
    {
        return type;
    }
}
