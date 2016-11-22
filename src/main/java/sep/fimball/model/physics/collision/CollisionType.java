package sep.fimball.model.physics.collision;

import sep.fimball.general.data.Vector2;
import sep.fimball.model.physics.element.BallPhysicsElement;

/**
 * Beschreibt eine Interaktion eines Colliders mit einem Ball.
 */
public interface CollisionType
{
    /**
     * Wendet eine Interaktion einer Fläche, die den Ball berührt, auf diesen Ball an.
     *
     * @param ball              Der Ball der sich mit dem Collider überschneidet.
     * @param shortestIntersect Der kürzeste Weg, um den Ball aus dem Collider heraus zu bewegen.
     * @param rotation          Die Drehung des Elements, mit dem kollidiert wird.
     */
    void applyCollision(BallPhysicsElement ball, Vector2 shortestIntersect, double rotation);
}
