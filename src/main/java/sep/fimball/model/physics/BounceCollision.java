package sep.fimball.model.physics;

import sep.fimball.general.data.Vector2;

/**
 * Created by TheAsuro on 15.11.2016.
 */
public class BounceCollision implements CollisionType
{
    private double strength = 1.0;

    public BounceCollision(double strength)
    {
        this.strength = strength;
    }

    @Override
    public void applyCollision(PhysicsElement ball, Vector2 shortestIntersect)
    {
        ball.setPosition(Vector2.add(ball.getPosition(), shortestIntersect));
        Vector2 shortestIntersectNorm = shortestIntersect.normalized();
        Vector2 newVel = Vector2.sub(ball.getVelocity(), Vector2.scale(shortestIntersectNorm, 2 * Vector2.dot(ball.getVelocity(), shortestIntersectNorm)));
        Vector2 newVelBounce = Vector2.scale(newVel.normalized(), strength);
        ball.setVelocity(newVelBounce);
    }
}
