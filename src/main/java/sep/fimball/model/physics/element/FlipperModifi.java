package sep.fimball.model.physics.element;

/**
 * Modifi zum zurücksetzten eines Balles.
 */
public class FlipperModifi extends Modifi
{
    /**
     * True, falls der Flipper nach oben gedreht werden soll.
     */
    private boolean up;

    /**
     * Erstellt ein neues FlipperModifi.
     *
     * @param up True, falls der Flipper nach oben gedreht werden soll.
     */
    public FlipperModifi(boolean up)
    {
        this.up = up;
    }

    /**
     * Gibt true zurück, falls der Flipper nach oben gedreht werden soll.
     *
     * @return Falls der Flipper nach oben gedreht werden soll.
     */
    public boolean isUp()
    {
        return up;
    }
}
