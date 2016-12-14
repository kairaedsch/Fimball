package sep.fimball.model.physics.collision;

import javafx.scene.paint.Color;
import sep.fimball.general.data.Vector2;
import sep.fimball.general.debug.Debug;

import java.util.ArrayList;
import java.util.List;

/**
 * Diese Klasse repräsentiert eine Kollision, bei der der Ball einfach abprallt und dadurch an Geschwindigkeit verliert, es wirken keine weiteren Kräfte.
 */
public class NormalCollision implements CollisionType
{
    private static List<Log> logs = new ArrayList<>();

    @Override
    public void applyCollision(CollisionInfo info)
    {
        //Schiebe den Ball auf dem kürzesten Weg aus dem Collider mit dem er zusammengestoßen ist heraus.
        info.getBall().setPosition(info.getBall().getPosition().plus(info.getShortestIntersect()));
        Vector2 shortestIntersectNorm = info.getShortestIntersect().normalized();

        if (info.getBall().getVelocity().normalized().dot(shortestIntersectNorm) <= 0)
        {
            Vector2 newVel = calculateNewSpeed(info.getBall().getVelocity(), shortestIntersectNorm, this.getClass().equals(FlipperCollision.class));
            Debug.addDrawVector(info.getBall().getPosition(), newVel, Color.BLACK);
            //Setze die berechnete neue Geschwindigkeit als Geschwindigkeit des Balls.
            info.getBall().setVelocity(newVel);
        }
    }

    private Vector2 calculateNewSpeed(Vector2 ballVelocity, Vector2 normal, boolean doLog)
    {
        //Bounce ist ein Wert der angibt wie stark sich die Kollision auf die Geschwindigkeit des Balls auswirkt. Ein Wert < 1 bedeutet das der Ball nach der Kollision langsamer wird.
        double bounce = 0.7;
        //Spiegle die Ball Geschwindigkeit am normierten shortestIntersect welcher eine Normale auf die Kollisionskante ist.

        Log log = new Log();
        log.oldVel = ballVelocity;

        double bounceVal = 1.0 + bounce;
        log.bounceVal = bounceVal;

        log.normal = normal;

        double velocityDotNormal = ballVelocity.dot(normal);
        log.velocityDotNormal = velocityDotNormal;

        double bounceTimesVelocityDotNormal = bounceVal * velocityDotNormal;
        log.bounceTimesVelocityDotNormal = bounceTimesVelocityDotNormal;

        Vector2 scaledNormal = normal.scale(bounceTimesVelocityDotNormal);
        log.scaledNormal = scaledNormal;

        Vector2 newVel = ballVelocity.minus(scaledNormal);
        log.newVel = newVel;

        log.time = System.currentTimeMillis();
        if (doLog)
            logs.add(log);

        return newVel;
    }

    class Log
    {
        Vector2 oldVel;
        double bounceVal;
        Vector2 normal;
        double velocityDotNormal;
        double bounceTimesVelocityDotNormal;
        Vector2 scaledNormal;
        Vector2 newVel;
        long time;
    }
}
