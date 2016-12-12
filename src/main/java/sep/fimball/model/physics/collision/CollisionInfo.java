package sep.fimball.model.physics.collision;

import sep.fimball.general.data.Vector2;
import sep.fimball.model.physics.collider.ColliderShape;
import sep.fimball.model.physics.element.BallPhysicsElement;
import sep.fimball.model.physics.element.PhysicsElement;

/**
 * CollisionInfo gibt Auskunft über eine aufgetretene Kollision.
 */
public class CollisionInfo
{
    /**
     * Das physikalische Element des Balls der bei jeder Kollision beteiligt ist.
     */
    private BallPhysicsElement ball;

    /**
     * Der kürzeste Weg um den Ball aus dem anderen Element "hinauszuschieben".
     */
    private Vector2 shortestIntersect;

    /**
     * Die Kollisionsform des Elements mit dem der Ball kollidiert ist.
     */
    private ColliderShape otherColliderShape;

    /**
     * Das physikalische Element mit dem der Ball kollidiert ist.
     */
    private PhysicsElement otherPhysicsElement;

    /**
     * Erzeugt eine neue Instanz von CollisionInfo.
     *
     * @param ball Das physikalische Element des Balls.
     * @param shortestIntersect Der kürzeste Weg um den Ball aus dem anderen Element "hinauszuschieben".
     * @param otherPhysicsElement Das physikalische Element mit dem der Ball kollidiert ist.
     * @param otherColliderShape Die Kollisionsform des Elements mit dem der Ball kollidiert ist.
     */
    public CollisionInfo(BallPhysicsElement ball, Vector2 shortestIntersect, PhysicsElement otherPhysicsElement, ColliderShape otherColliderShape)
    {
        this.ball = ball;
        this.shortestIntersect = shortestIntersect;
        this.otherPhysicsElement = otherPhysicsElement;
        this.otherColliderShape = otherColliderShape;
    }

    /**
     * Gibt das physikalische Element des Balls zurück.
     *
     * @return Das physikalische Element des Balls.
     */
    public BallPhysicsElement getBall()
    {
        return ball;
    }

    /**
     * Gibt den kürzesten Weg um den Ball aus dem anderen Element "hinauszuschieben" zurück.
     *
     * @return Der kürzeste Weg um den Ball aus dem anderen Element "hinauszuschieben".
     */
    public Vector2 getShortestIntersect()
    {
        return shortestIntersect;
    }

    /**
     * Gibt die Kollisionsform des Elements mit dem der Ball kollidiert ist zurück.
     *
     * @return Die Kollisionsform mit der der Ball kollidiert ist.
     */
    public ColliderShape getOtherColliderShape()
    {
        return otherColliderShape;
    }

    /**
     * Gibt das andere physikalische Element mit dem der Ball kollidiert ist zurück.
     *
     * @return Das physikalische Element mit dem der Ball kollidiert ist.
     */
    public PhysicsElement getOtherPhysicsElement()
    {
        return otherPhysicsElement;
    }
}
