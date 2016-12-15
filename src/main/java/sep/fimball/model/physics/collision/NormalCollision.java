package sep.fimball.model.physics.collision;

import javafx.scene.paint.Color;
import sep.fimball.general.data.Vector2;
import sep.fimball.general.debug.Debug;


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
            Debug.addDrawVector(info.getBall().getPosition(), newVel, Color.BLACK);
            //Setze die berechnete neue Geschwindigkeit als Geschwindigkeit des Balls.
            info.getBall().setVelocity(newVel);
        }
    }

    private Vector2 calculateNewSpeed(Vector2 ballVelocity, Vector2 normal)
    {
        //Bounce ist ein Wert der angibt wie stark sich die Kollision auf die Geschwindigkeit des Balls auswirkt. Ein Wert < 1 bedeutet das der Ball nach der Kollision langsamer wird.
        final double bounce = 0.4;
        //Spiegle die Ball Geschwindigkeit am normierten shortestIntersect welcher eine Normale auf die Kollisionskante ist.

        double bounceVal = 1.0 + bounce;
        double velocityDotNormal = ballVelocity.dot(normal);
        double bounceTimesVelocityDotNormal = bounceVal * velocityDotNormal;
        Vector2 scaledNormal = normal.scale(bounceTimesVelocityDotNormal);
        return ballVelocity.minus(scaledNormal);
    }
}
