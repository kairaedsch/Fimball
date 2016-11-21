package sep.fimball.model.physics;

import sep.fimball.general.data.Vector2;
import sep.fimball.model.RectangleDouble;

/**
 * Beschreibt die Form einer Fläche die mit dem Ball interagieren kann.
 */
public interface ColliderShape
{
    /**
     * Gibt Informationen zurück, ob der Ball in der Fläche liegt.
     * @param ball Der Ball, der auf Überschneidung mit der Fläche geprüft werden soll.
     * @return Informationen über eine mögliche Überschneidung der Fläche mit dem Ball.
     */
    HitInfo calculateHitInfo(BallElement ball, Vector2 colliderObjectPosition, double rotation, Vector2 pivotPoint);

    /**
     * Gibt eine axis-aligned Box zurück, welche die Form der Fläche enthält.
     * @param rotation Rotation der Form.
     * @param pivotPoint Punkt, um den die Form rotiert wird.
     * @return Axis-aligned Box zurück, welche die Form der Fläche enthält.
     */
    RectangleDouble getBoundingBox(double rotation, Vector2 pivotPoint);
}
