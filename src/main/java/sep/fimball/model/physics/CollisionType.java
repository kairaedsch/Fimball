package sep.fimball.model.physics;

import sep.fimball.general.data.Vector2;

/**
 * TODO
 */
public interface CollisionType
{
    /**
     * TODO
     * @param ball
     * @param shortestIntersect
     */
    void applyCollision(BallElement ball, Vector2 shortestIntersect);
}
