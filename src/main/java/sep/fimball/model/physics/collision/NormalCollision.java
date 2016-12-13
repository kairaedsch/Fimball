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
        //Schiebe den Ball auf dem kürzesten Weg aus dem Collider mit dem er zusammengestoßen ist heraus.
        info.getBall().setPosition(info.getBall().getPosition().plus(info.getShortestIntersect()));
        Vector2 shortestIntersectNorm = info.getShortestIntersect().normalized();
        Vector2 newVel = calculateNewSpeed(info.getBall().getVelocity(), shortestIntersectNorm);
        //Setze die berechnete neue Geschwindigkeit als Geschwindigkeit des Balls.
        info.getBall().setVelocity(newVel);
    }

    private Vector2 calculateNewSpeed(Vector2 ballVelocity, Vector2 normal)
    {
        //Bounce ist ein Wert der angibt wie stark sich die Kollision auf die Geschwindigkeit des Balls auswirkt. Ein Wert < 1 bedeutet das der Ball nach der Kollision langsamer wird.
        double bounce = 0.7;
        //Spiegle die Ball Geschwindigkeit am normierten shortestIntersect welcher eine Normale auf die Kollisionskante ist.
        return ballVelocity.minus(normal.scale((1.0 + bounce) * ballVelocity.dot(normal)));
    }
}
