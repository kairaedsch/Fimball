package sep.fimball.model.physics.collision;

import sep.fimball.general.data.Vector2;

/**
 * Bei einem Collider mit dieser Art von Collision prallt der Ball ab und bekommt zusätzlich noch eine vorgegebene
 * Geschwindigkeit hinzugefügt.
 */
public class BounceCollision implements CollisionType
{
    /**
     * Gibt an wie stark der Ball zusätzlich abgestoßen werden soll.
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
        //Schiebe den Ball auf dem kürzesten Weg aus dem Collider mit dem er zusammengestoßen ist heraus.
        info.getBall().setPosition(info.getBall().getPosition().plus(info.getShortestIntersect()));
        Vector2 shortestIntersectNorm = info.getShortestIntersect().normalized();
        //Berechne die neuen Geschwindigkeit mithilfe von Spiegelung am shortestIntersect Vektor.
        Vector2 newVel = info.getBall().getVelocity().minus(shortestIntersectNorm.scale(2.0 * info.getBall().getVelocity().dot(shortestIntersectNorm)));
        //Skaliere den shortestIntersect welcher eine Normale der Kollisionskante ist mit der strength, addiere dies auf newVel und setze dies als neue Ball Geschwindigkeit.
        info.getBall().setVelocity(newVel.plus(info.getShortestIntersect().normalized().scale(strength)));
    }
}
