package sep.fimball.model.physics.collision;

import sep.fimball.general.data.Vector2;
import sep.fimball.model.physics.collider.ColliderShape;
import sep.fimball.model.physics.element.BallPhysicsElement;
import sep.fimball.model.physics.element.PhysicsElement;

import java.util.List;

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
     * Die Form des Colliders des Elements mit dem der Ball kollidiert ist.
     */
    private List<ColliderShape> otherColliderShapes;

    /**
     * Das physikalische Element mit dem der Ball kollidiert ist.
     */
    private PhysicsElement otherPhysicsElement;

    /**
     * Erzeugt eine neue Instanz von CollisionInfo.
     *
     * @param ball                Das physikalische Element des Balls.
     * @param shortestIntersect   Der kürzeste Weg um den Ball aus dem anderen Element "hinauszuschieben".
     * @param otherPhysicsElement Das physikalische Element mit dem der Ball kollidiert ist.
     * @param otherColliderShapes Die Form des Colliders des Elements mit dem der Ball kollidiert ist.
     */
    public CollisionInfo(BallPhysicsElement ball, Vector2 shortestIntersect, PhysicsElement otherPhysicsElement, List<ColliderShape> otherColliderShapes)
    {
        this.ball = ball;
        this.shortestIntersect = shortestIntersect;
        this.otherPhysicsElement = otherPhysicsElement;
        this.otherColliderShapes = otherColliderShapes;
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
     * Gibt die Form des Colliders des Elements mit dem der Ball kollidiert ist zurück.
     *
     * @return Die Form des Colliders mit der der Ball kollidiert ist.
     */
    public List<ColliderShape> getOtherColliderShapes()
    {
        return otherColliderShapes;
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
