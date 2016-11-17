package sep.fimball.model.physics;

import sep.fimball.general.data.Vector2;

/**
 * TODO
 */
public class AccelerationCollision implements CollisionType
{
    /**
     * TODO
     */
    private Vector2 acceleration;

    /**
     * TODO
     * @param acceleration
     */
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
