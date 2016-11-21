package sep.fimball.model.physics;

import javafx.scene.paint.Color;
import sep.fimball.general.Debug;
import sep.fimball.general.data.Vector2;

/**
 * Diese Klasse repräsentiert eine einfache Kollision, bei der der Ball einfach abprallt, es wirken keine weiteren Kräfte.
 */
public class NormalCollision implements CollisionType
{
    /**
     * Gibt an, wie viel Prozent der Geschwindigkeit nach der Kollision erhalten bleiben sollen.
     */
    private final double bounce = 0.7;

    @Override
    public void applyCollision(BallElement ball, Vector2 shortestIntersect, double rotation)
    {
        ball.setPosition(Vector2.add(ball.getPosition(), shortestIntersect));
        Vector2 shortestIntersectNorm = shortestIntersect.normalized();
        Debug.addDrawVector(ball.getPosition(), shortestIntersect.normalized(), Color.RED);
        //Debug.addDrawVector(ball.getPosition(), ball.getVelocity().normalized(), Color.GREEN);
        Vector2 newVel = Vector2.sub(ball.getVelocity(), Vector2.scale(shortestIntersectNorm, (1.0 + bounce) * Vector2.dot(ball.getVelocity(), shortestIntersectNorm)));
        ball.setVelocity(newVel);
        //Debug.addDrawVector(ball.getPosition(), ball.getVelocity().normalized(), Color.BLUE);
    }
}
