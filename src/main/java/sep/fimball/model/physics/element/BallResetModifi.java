package sep.fimball.model.physics.element;

import sep.fimball.general.data.Vector2;

/**
 * Modifi zum zurücksetzten eines Balles.
 */
public class BallResetModifi extends Modifi
{
    /**
     * Die neue Position des Balles.
     */
    private Vector2 newPosition;

    /**
     * Erstellt ein neues BallResetModifi.
     *
     * @param newPosition Die neue Position des Balles.
     */
    public BallResetModifi(Vector2 newPosition)
    {
        this.newPosition = newPosition;
    }

    /**
     * gibt die neue Position des Balles zurück.
     *
     * @return ie neue Position des Balles.
     */
    public Vector2 getNewPosition()
    {
        return newPosition;
    }
}
