package sep.fimball.model.physics;

import sep.fimball.general.data.Vector2;

/**
 * Beschreibt eine Interaktion eines Colliders mit einem Ball.
 */
public interface CollisionType
{
    /**
     * Wendet eine Interaktion einer Fläche, die den Ball berührt, auf diesen Ball an.
     * @param ball Der Ball der sich mit dem Collider überschneidet.
     * @param shortestIntersect Der kürzeste Weg, um den Ball aus dem Collider heraus zu bewegen.
     */
    void applyCollision(BallElement ball, Vector2 shortestIntersect);
}
