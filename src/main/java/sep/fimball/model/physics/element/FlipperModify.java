package sep.fimball.model.physics.element;

/**
 * Modify zum zurücksetzten eines Balles.
 */
public interface FlipperModify extends Modify
{
    /**
     * Gibt true zurück, falls der Flipper nach oben gedreht werden soll.
     *
     * @return Falls der Flipper nach oben gedreht werden soll.
     */
    AngularDirection newAngularDirection();
}
