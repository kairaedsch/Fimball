package sep.fimball.model.physics.element;

/**
 * Modify zum Bedienen des Plungers.
 */
public interface PlungerModify extends Modify
{
    /**
     * Gibt die vom Plunger anzuwendende Kraft zurück.
     *
     * @return Die vom Plunger anzuwendende Kraft.
     */
    double getForce();
}
