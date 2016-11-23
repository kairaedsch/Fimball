package sep.fimball.model.physics.collision;

import sep.fimball.general.data.Vector2;
import sep.fimball.model.physics.element.BallPhysicsElement;

/**
 * TODO
 */
public class EmptyCollision implements CollisionType
{
    @Override
    public void applyCollision(BallPhysicsElement ball, Vector2 shortestIntersect, double rotation)
    {

    }
}
