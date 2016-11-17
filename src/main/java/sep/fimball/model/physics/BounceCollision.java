package sep.fimball.model.physics;

import sep.fimball.general.data.Vector2;

/**
 * Bei einem Collider mit dieser Art von Collision prallt der Ball ab, und bekommt zusätlich noch eine vorgegebene
 * Geschwindigkeit hinzugefügt.
 */
public class BounceCollision implements CollisionType
{
    /**
     * Wie viel Geschwindigkeit der Ball beim abprallen zusätzlich bekommt.
     */
    private double strength = 1.0;

    /**
     * Erstellt eine neue Instanz von BounceCollision.
     * @param strength Wie viel Geschwindigkeit der Ball beim abprallen zusätzlich bekommt.
     */
    public BounceCollision(double strength)
    {
        this.strength = strength;
    }

    @Override
    public void applyCollision(BallElement ball, Vector2 shortestIntersect)
    {
        ball.setPosition(Vector2.add(ball.getPosition(), shortestIntersect));
        Vector2 shortestIntersectNorm = shortestIntersect.normalized();
        Vector2 newVel = Vector2.sub(ball.getVelocity(), Vector2.scale(shortestIntersectNorm, 2 * Vector2.dot(ball.getVelocity(), shortestIntersectNorm)));
        Vector2 newVelBounce = Vector2.scale(newVel.normalized(), strength);
        ball.setVelocity(newVelBounce);
    }
}
