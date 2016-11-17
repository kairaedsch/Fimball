package sep.fimball.model.physics;

import sep.fimball.general.data.Vector2;

/**
 * Diese Klasse repäsentiert die Kollision des Balls mit einem "normalen" Element (kein Flipper oder Plunger).
 */
public class NormalCollision implements CollisionType
{
    private final double bounce = 0.1;

    @Override
    public void applyCollision(BallElement ball, Vector2 shortestIntersect)
    {
        ball.setPosition(Vector2.add(ball.getPosition(), shortestIntersect));
        Vector2 shortestIntersectNorm = shortestIntersect.normalized();
        Vector2 newVel = Vector2.sub(ball.getVelocity(), Vector2.scale(shortestIntersectNorm, 2.0 * Vector2.dot(ball.getVelocity(), shortestIntersectNorm)));
        ball.setVelocity(Vector2.scale(newVel, bounce));
    }
}
