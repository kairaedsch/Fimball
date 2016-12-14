package sep.fimball.model.physics.element;

/**
 * Modifi zum Bedienen des Plungers.
 */
public class PlungerModifi extends Modifi
{
    /**
     * Die vom Plunger anzuwendende Kraft.
     */
    private double force;

    /**
     * Erstellt ein neues PlungerModifi.
     *
     * @param force Die vom Plunger anzuwendende Kraft.
     */
    public PlungerModifi(double force)
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
