package sep.fimball.model.physics;

import sep.fimball.general.data.Vector2;

/**
 * Created by TheAsuro on 15.11.2016.
 */
public class AccelerationCollision implements CollisionType
{
    private float accelerationSpeed;

    public AccelerationCollision(float accelerationSpeed)
    {
        this.accelerationSpeed = accelerationSpeed;
    }

    @Override
    public void applyCollision(BallElement ball, Vector2 shortestIntersect)
    {
        Vector2 ballDirection = ball.getVelocity().normalized();
        ball.setVelocity(Vector2.add(ball.getVelocity(), Vector2.scale(ballDirection, accelerationSpeed)));
    }
}
