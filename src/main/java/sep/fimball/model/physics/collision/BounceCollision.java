package sep.fimball.model.physics.collision;

import sep.fimball.general.data.Vector2;
import sep.fimball.model.physics.element.BallPhysicsElement;

/**
 * Bei einem Collider mit dieser Art von Collision prallt der Ball ab und bekommt zusätzlich noch eine vorgegebene
 * Geschwindigkeit hinzugefügt.
 */
public class BounceCollision implements CollisionType
{
    /**
     * Gibt an, wie viel Geschwindigkeit der Ball beim abprallen zusätzlich bekommt.
     */
    private double strength;

    /**
     * Erstellt eine neue Instanz von BounceCollision.
     *
     * @param strength Wie viel Geschwindigkeit der Ball beim abprallen zusätzlich bekommt.
     */
    public BounceCollision(double strength)
    {
        this.strength = strength;
    }

    @Override
    public void applyCollision(BallPhysicsElement ball, Vector2 shortestIntersect, double rotation)
    {
        ball.setPosition(ball.getPosition().add(shortestIntersect));
        Vector2 shortestIntersectNorm = shortestIntersect.normalized();
        Vector2 newVel = ball.getVelocity().sub(shortestIntersectNorm.scale(2.0 * ball.getVelocity().dot(shortestIntersectNorm)));
        ball.setVelocity(newVel.add(shortestIntersect.normalized().scale(strength)));
    }
}
