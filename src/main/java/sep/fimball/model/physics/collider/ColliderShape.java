package sep.fimball.model.physics.collider;

import sep.fimball.general.data.Vector2;
import sep.fimball.general.data.RectangleDouble;
import sep.fimball.model.physics.element.BallPhysicsElement;

/**
 * Beschreibt die Form einer Fläche die mit dem Ball interagieren kann.
 */
public interface ColliderShape
{
    /**
     * Gibt Informationen zurück, ob der Ball in der Fläche liegt.
     *
     * @param ball                   Der Ball, der auf Überschneidung mit der Fläche geprüft werden soll.
     * @param colliderObjectPosition Die Position des Elements, mit dem kollidiert wird.
     * @param rotation               Die Drehung des Elements, mit dem kollidiert wird.
     * @param pivotPoint             Der Pivot-Punkt des Elements, mit dem kollidiert wird.
     * @return Informationen, ob der Ball in der Fläche liegt.
     */
    HitInfo calculateHitInfo(BallPhysicsElement ball, Vector2 colliderObjectPosition, double rotation, Vector2 pivotPoint);

    /**
     * Gibt eine Box zurück, welche die Form der Fläche enthält und deren Achsen nach den Koordinatenachsen ausgerichtet sind.
     *
     * @param rotation   Rotation der Form.
     * @param pivotPoint Punkt, um den die Form rotiert wird.
     * @return Eine Box, welche die Form der Fläche enthält und deren Achsen nach den Koordinatenachsen ausgerichtet sind.
     */
    RectangleDouble getBoundingBox(double rotation, Vector2 pivotPoint);

    /**
     * Gibt den Vektor mit minimaler/maximaler X und Y Position dieser Form zurück je nachdem ob {@code max} true oder false ist.
     *
     * @param rotation   Die rotation des Colliders.
     * @param pivotPoint Der Drehpunkt des Colliders.
     * @param max Gibt an ob die maximale oder minimale Position gesucht ist.
     * @return Die Maximale Position dieses Colliders bei der gegebenen Rotation.
     */
    Vector2 getExtremePos(double rotation, Vector2 pivotPoint, boolean max);
}
