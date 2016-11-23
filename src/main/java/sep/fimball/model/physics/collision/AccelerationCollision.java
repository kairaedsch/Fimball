package sep.fimball.model.physics.collision;

import sep.fimball.general.data.Vector2;
import sep.fimball.model.physics.element.BallPhysicsElement;

/**
 * Bei einem Collider mit dieser Art von Collision prallt der Ball nicht ab, sondern wird in eine vorher festgelegte
 * Richtung beschleunigt.
 */
public class AccelerationCollision implements CollisionType
{
    /**
     * Gibt an, wie stark der Ball in Richtung des Elements beschleunigt wird.
     */
    private double strength;

    /**
     * Erstellt eine neue Instanz von AccelerationCollision.
     *
     * @param strength Wie stark der Ball in Richtung des Elements beschleunigt wird.
     */
    public AccelerationCollision(double strength)
    {
        this.strength = strength;
    }

    @Override
    public void applyCollision(BallPhysicsElement ball, Vector2 shortestIntersect, double rotation)
    {
        Vector2 direction = new Vector2(0, -1).rotate(Math.toRadians(rotation));
        ball.setVelocity(ball.getVelocity().plus(direction.scale(strength)));
    }
}
