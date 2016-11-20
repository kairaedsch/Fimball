package sep.fimball.model.physics;

import sep.fimball.general.data.Vector2;

/**
 * Diese Klasse repräsentiert die Kollision eines Flippers mti dem Ball.
 */
public class FlipperCollision implements CollisionType
{
    private NormalCollision normalCollision = new NormalCollision();

    private boolean moving = false;

    @Override
    public void applyCollision(BallElement ball, Vector2 shortestIntersect, double rotation)
    {
        normalCollision.applyCollision(ball, shortestIntersect, rotation);

        if (moving)
        {
            // TODO
        }
    }
}
