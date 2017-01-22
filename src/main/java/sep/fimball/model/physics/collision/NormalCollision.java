package sep.fimball.model.physics.collision;

import sep.fimball.general.data.PhysicsConfig;
import sep.fimball.general.data.Vector2;


/**
 * Diese Klasse repräsentiert eine Kollision, bei der der Ball einfach abprallt und dadurch an Geschwindigkeit verliert, es wirken keine weiteren Kräfte.
 */
public class NormalCollision implements CollisionType
{
    @Override
    public void applyCollision(CollisionInfo info)
    {
        //Schiebe den Ball auf dem kürzesten Weg aus dem Collider mit dem er zusammengestoßen ist heraus.
        info.getBall().setPosition(info.getBall().getPosition().plus(info.getShortestIntersect()));
        Vector2 shortestIntersectNorm = info.getShortestIntersect().normalized();

        //Überprüfe ob der Ball in die Richtung dieses Colliders fliegt.
        if (info.getBall().getVelocity().normalized().dot(shortestIntersectNorm) <= 0)
        {
            Vector2 newVel = calculateNewSpeed(info.getBall().getVelocity(), shortestIntersectNorm);
            //Setze die Berechnete neue Geschwindigkeit als Geschwindigkeit des Balls.
            info.getBall().setVelocity(newVel);
        }
    }

    /**
     * Berechnet die neue Geschwindigkeit des Balls durch Spiegelung an der übergebenen Normale.
     *
     * @param ballVelocity Die aktuelle Geschwindigkeit des Balls.
     * @param normal       Die Normale an der die Geschwindigkeit des Balls gespiegelt werden soll.
     * @return Die neue Geschwindigkeit des Balls.
     */
    private Vector2 calculateNewSpeed(Vector2 ballVelocity, Vector2 normal)
    {
        double velocityProjectedOnNormal = ballVelocity.dot(normal);
        Vector2 scaledNormal = normal.scale(PhysicsConfig.BOUNCE_NORMAL_COLLISION * velocityProjectedOnNormal);
        return ballVelocity.minus(scaledNormal);
    }
}
