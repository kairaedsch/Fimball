package sep.fimball.model.physics.collision;

import sep.fimball.general.data.Vector2;

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
    public void applyCollision(CollisionInfo info)
    {
        info.getBall().setPosition(info.getBall().getPosition().plus(info.getShortestIntersect()));
        Vector2 shortestIntersectNorm = info.getShortestIntersect().normalized();
        Vector2 newVel = info.getBall().getVelocity().minus(shortestIntersectNorm.scale(2.0 * info.getBall().getVelocity().dot(shortestIntersectNorm)));
        info.getBall().setVelocity(newVel.plus(info.getShortestIntersect().normalized().scale(strength)));
    }
}
