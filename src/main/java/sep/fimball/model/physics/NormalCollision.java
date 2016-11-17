package sep.fimball.model.physics;

import sep.fimball.general.data.Vector2;

/**
 * Diese Klasse repäsentiert die Kollision des Balls mit einem "normalen" Element (kein Flipper oder Plunger).
 */
public class NormalCollision implements CollisionType
{
    @Override
    public void applyCollision(BallElement ball, Vector2 shortestIntersect)
    {
        ball.setPosition(Vector2.add(ball.getPosition(), shortestIntersect));
        Vector2 shortestIntersectNorm = shortestIntersect.normalized();
        Vector2 newVel = Vector2.sub(ball.getVelocity(), Vector2.scale(shortestIntersectNorm, 2 * Vector2.dot(ball.getVelocity(), shortestIntersectNorm)));
        ball.setVelocity(newVel);
    }
}
