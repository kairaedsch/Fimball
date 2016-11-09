package sep.fimball.model.physics;

/**
 * PhysicForce gibt die Stärke und die Art der Kraft an die auf den Ball einwirken sobald dieser mit einem
 * Collider kollidiert. Die unterschiedlichen Collider haben genau eine PhysicForce.
 */
public class PhysicsForce
{
    /**
     * Multiplikator welcher vom Nutzer im Editor eingestellt wird.
     * Dieser Multiplikator wird mit dem im Bahnelement eingestellten Standardwert der Kraft multipliziert
     */
    private double force;
    /**
     * Typ der Kraft, wobei die Unterschiedlichen Arten in der ForceType Aufzählung erklärt werden
     */
    private ForceType type;

    /**
     * Erstellt ein neues PhysicsForce Objekt
     * @param force
     * @param type
     */
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
