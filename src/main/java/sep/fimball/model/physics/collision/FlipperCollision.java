package sep.fimball.model.physics.collision;

import sep.fimball.general.data.Vector2;
import sep.fimball.model.physics.element.BallPhysicsElement;

/**
 * Diese Klasse repräsentiert die Kollision des Balls mit einem Flipper.
 */
public class FlipperCollision extends NormalCollision
{
    @Override
    public void applyCollision(BallPhysicsElement ball, Vector2 shortestIntersect, double rotation)
    {
        super.applyCollision(ball, shortestIntersect, rotation);

        // TODO get angular velocity, etc.
    }
}
