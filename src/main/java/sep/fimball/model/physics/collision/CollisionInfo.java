package sep.fimball.model.physics.collision;

import sep.fimball.general.data.Vector2;
import sep.fimball.model.physics.element.BallPhysicsElement;

/**
 * Created by TheAsuro on 27.11.2016.
 */
public class CollisionInfo
{
    private BallPhysicsElement ball;
    private Vector2 shortestIntersect;
    private double angularVelocity;
    private double rotation;

    public CollisionInfo(BallPhysicsElement ball, Vector2 shortestIntersect, double angularVelocity, double rotation)
    {
        this.ball = ball;
        this.shortestIntersect = shortestIntersect;
        this.angularVelocity = angularVelocity;
        this.rotation = rotation;
    }

    public CollisionInfo(BallPhysicsElement ball, Vector2 shortestIntersect, double rotation)
    {
        this.ball = ball;
        this.shortestIntersect = shortestIntersect;
        this.angularVelocity = 0;
        this.rotation = rotation;
    }

    public BallPhysicsElement getBall()
    {
        return ball;
    }

    public Vector2 getShortestIntersect()
    {
        return shortestIntersect;
    }

    public double getAngularVelocity()
    {
        return angularVelocity;
    }

    public double getRotation()
    {
        return rotation;
    }
}
