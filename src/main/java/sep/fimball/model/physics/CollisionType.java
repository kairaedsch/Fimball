package sep.fimball.model.physics;

import sep.fimball.general.data.Vector2;

/**
 * Created by TheAsuro on 15.11.2016.
 */
public interface CollisionType
{
    void applyCollision(PhysicsElement ball, Vector2 shortestIntersect);
}
