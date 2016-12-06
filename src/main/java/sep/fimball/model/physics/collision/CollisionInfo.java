package sep.fimball.model.physics.collision;

import sep.fimball.general.data.Vector2;
import sep.fimball.model.physics.collider.ColliderShape;
import sep.fimball.model.physics.element.BallPhysicsElement;
import sep.fimball.model.physics.element.PhysicsElement;

public class CollisionInfo
{
    private BallPhysicsElement ball;
    private Vector2 shortestIntersect;
    private ColliderShape shape;
    private PhysicsElement physicsElement;

    public CollisionInfo(BallPhysicsElement ball, Vector2 shortestIntersect, PhysicsElement physicsElement, ColliderShape shape)
    {
        this.ball = ball;
        this.shortestIntersect = shortestIntersect;
        this.physicsElement = physicsElement;
        this.shape = shape;
    }

    public BallPhysicsElement getBall()
    {
        return ball;
    }

    public Vector2 getShortestIntersect()
    {
        return shortestIntersect;
    }

    public PhysicsElement getPhysicsElement()
    {
        return physicsElement;
    }

    public ColliderShape getShape()
    {
        return shape;
    }
}
