package sep.fimball.model.physics.collision;

import javafx.scene.paint.Color;
import sep.fimball.general.data.Vector2;
import sep.fimball.general.debug.Debug;
import sep.fimball.model.physics.element.BallPhysicsElement;

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
    public void applyCollision(BallPhysicsElement ball, Vector2 shortestIntersect, double rotation)
    {
        ball.setPosition(ball.getPosition().add(shortestIntersect));
        Vector2 shortestIntersectNorm = shortestIntersect.normalized();
        Debug.addDrawVector(ball.getPosition(), shortestIntersect.normalized(), Color.RED);
        //Debug.addDrawVector(ball.getPosition(), ball.getVelocity().normalized(), Color.GREEN);
        Vector2 newVel = ball.getVelocity().sub(shortestIntersectNorm.scale((1.0 + bounce) * ball.getVelocity().dot(shortestIntersectNorm)));
        ball.setVelocity(newVel);
        //Debug.addDrawVector(ball.getPosition(), ball.getVelocity().normalized(), Color.BLUE);
    }
}
