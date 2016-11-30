package sep.fimball.model.physics.collision;

import sep.fimball.general.data.Vector2;
import sep.fimball.model.physics.element.BallPhysicsElement;
import sep.fimball.model.physics.element.FlipperPhysicsElement;

public class CollisionInfo
{
    private BallPhysicsElement ball;
    private Vector2 shortestIntersect;
    private double rotation;
    private FlipperPhysicsElement flipper;

    public CollisionInfo(BallPhysicsElement ball, Vector2 shortestIntersect, double rotation, FlipperPhysicsElement flipper)
    {
        this.ball = ball;
        this.shortestIntersect = shortestIntersect;
        this.flipper = flipper;
        this.rotation = rotation;
    }

    public CollisionInfo(BallPhysicsElement ball, Vector2 shortestIntersect, double rotation)
    {
        this.ball = ball;
        this.shortestIntersect = shortestIntersect;
        this.rotation = rotation;
        this.flipper = null;
    }

    public BallPhysicsElement getBall()
    {
        return ball;
    }

    public Vector2 getShortestIntersect()
    {
        return shortestIntersect;
    }

    public FlipperPhysicsElement getFlipper()
    {
        return flipper;
    }

    public double getRotation()
    {
        return rotation;
    }
}
