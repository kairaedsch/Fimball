package sep.fimball.model.physics;

import sep.fimball.general.data.Vector2;

/**
 * Created by TheAsuro on 15.11.2016.
 */
public class AccelerationCollision implements CollisionType
{
    private Vector2 acceleration;

    public AccelerationCollision(Vector2 acceleration)
    {
        this.acceleration = acceleration;
    }

    @Override
    public void applyCollision(BallElement ball, Vector2 shortestIntersect)
    {
        Vector2 ballDirection = ball.getVelocity().normalized();
        ball.setVelocity(Vector2.add(ball.getVelocity(), Vector2.add(ballDirection, acceleration)));
    }
}
