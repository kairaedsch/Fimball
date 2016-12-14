package sep.fimball.model.physics.element;

/**
 * Modify zum Bedienen des Plungers.
 */
public class PlungerModify extends Modify
{
    /**
     * Die vom Plunger anzuwendende Kraft.
     */
    private double force;

    /**
     * Erstellt ein neues PlungerModify.
     *
     * @param force Die vom Plunger anzuwendende Kraft.
     */
    public PlungerModify(double force)
    {
        this.force = force;
    }

    /**
     * Gibt die vom Plunger anzuwendende Kraft zurück.
     *
     * @return Die vom Plunger anzuwendende Kraft.
     */
    public double getForce()
    {
        return force;
    }
}
