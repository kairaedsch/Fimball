package sep.fimball.model.physics.collision;

import sep.fimball.general.data.Vector2;
/**
 * Diese Klasse repräsentiert eine Kollision, bei der der Ball einfach abprallt und dadurch an Geschwindigkeit verliert, es wirken keine weiteren Kräfte.
 */
public class NormalCollision implements CollisionType
{

    @Override
    public void applyCollision(CollisionInfo info)
    {
        info.getBall().setPosition(info.getBall().getPosition().plus(info.getShortestIntersect()));
        Vector2 shortestIntersectNorm = info.getShortestIntersect().normalized();
        Vector2 newVel = calculateNewSpeed(info.getBall().getVelocity(), shortestIntersectNorm);
        info.getBall().setVelocity(newVel);
    }

    private Vector2 calculateNewSpeed(Vector2 ballVelocity, Vector2 normal)
    {
        //Bounce ist ein Wert der angibt wie stark sich die Kollision auf die Geschwindigkeit des Balls auswirkt. Ein Wert < 1 bedeutet das der Ball nach der Kollision langsamer wird.
        double bounce = 0.7;
        return ballVelocity.minus(normal.scale((1.0 + bounce) * ballVelocity.dot(normal)));
    }
}
