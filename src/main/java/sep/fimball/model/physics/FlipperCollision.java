package sep.fimball.model.physics;

import sep.fimball.general.data.Vector2;

/**
 * Diese Klasse repräsentiert die Kollision eines Flippers mit dem Ball.
 */
public class FlipperCollision extends NormalCollision
{
    @Override
    public void applyCollision(BallElement ball, Vector2 shortestIntersect, double rotation)
    {
        super.applyCollision(ball, shortestIntersect, rotation);

        // TODO get angular velocity, etc.
    }
}
