package sep.fimball.model.handler;

import sep.fimball.model.physics.game.CollisionEventType;

/**
 * Created by alexcekay on 23.01.17.
 */
public class ElementHandlerArgs
{
    private CollisionEventType collisionEventType;
    private double depth;
    private int colliderId;

    public ElementHandlerArgs(CollisionEventType collisionEventType, double depth, int colliderId)
    {
        this.collisionEventType = collisionEventType;
        this.depth = depth;
        this.colliderId = colliderId;
    }

    public CollisionEventType getCollisionEventType()
    {
        return collisionEventType;
    }

    public double getDepth()
    {
        return depth;
    }

    public int getColliderId()
    {
        return colliderId;
    }
}
