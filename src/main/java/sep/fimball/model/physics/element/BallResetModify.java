package sep.fimball.model.physics.element;

import sep.fimball.general.data.Vector2;

/**
 * Modify zum Zurücksetzten eines Balles.
 */
public interface BallResetModify extends Modify
{
    /**
     * Gibt die neue Position des Balles zurück.
     *
     * @return Die neue Position des Balles.
     */
    Vector2 getNewPosition();
}
