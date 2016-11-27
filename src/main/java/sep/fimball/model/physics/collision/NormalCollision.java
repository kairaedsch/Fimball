package sep.fimball.model.physics.collision;

import javafx.scene.paint.Color;
import sep.fimball.general.data.Vector2;
import sep.fimball.general.debug.Debug;

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
    public void applyCollision(CollisionInfo info)
    {
        info.getBall().setPosition(info.getBall().getPosition().plus(info.getShortestIntersect()));
        Vector2 shortestIntersectNorm = info.getShortestIntersect().normalized();
        Debug.addDrawVector(info.getBall().getPosition().plus(new Vector2(info.getBall().getCollider().getRadius(), info.getBall().getCollider().getRadius())), info.getShortestIntersect().normalized(), Color.RED);
        //Debug.addDrawVector(ball.getPosition(), ball.getVelocity().normalized(), Color.GREEN);
        Vector2 newVel = info.getBall().getVelocity().minus(shortestIntersectNorm.scale((1.0 + bounce) * info.getBall().getVelocity().dot(shortestIntersectNorm)));
        info.getBall().setVelocity(newVel);
        //Debug.addDrawVector(ball.getPosition(), ball.getVelocity().normalized(), Color.BLUE);
    }
}
