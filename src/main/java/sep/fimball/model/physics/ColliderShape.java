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
     * @param colliderObjectPosition Die Position des Elements, mit dem kollidiert wird.
     * @param rotation Die Drehung des Elements, mit dem kollidiert wird.
     * @param pivotPoint Der Pivot-Punkt des Elements, mit dem kollidiert wird.
     * @return Informationen, ob der Ball in der Fläche liegt.
     */
    HitInfo calculateHitInfo(BallElement ball, Vector2 colliderObjectPosition, double rotation, Vector2 pivotPoint);

    /**
     * Gibt eine Box zurück, welche die Form der Fläche enthält und deren Achsen nach den Koordinatenachsen ausgerichtet sind.
     * @param rotation Rotation der Form.
     * @param pivotPoint Punkt, um den die Form rotiert wird.
     * @return Eine Box, welche die Form der Fläche enthält und deren Achsen nach den Koordinatenachsen ausgerichtet sind..
     */
    RectangleDouble getBoundingBox(double rotation, Vector2 pivotPoint);
}
