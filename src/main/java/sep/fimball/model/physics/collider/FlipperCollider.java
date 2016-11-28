package sep.fimball.model.physics.collider;

import sep.fimball.model.physics.collision.CollisionInfo;
import sep.fimball.model.physics.collision.CollisionType;
import sep.fimball.model.physics.element.BallPhysicsElement;
import sep.fimball.model.physics.element.FlipperPhysicsElement;
import sep.fimball.model.physics.element.PhysicsElement;

import java.util.List;

/**
 * Created by TheAsuro on 28.11.2016.
 */
public class FlipperCollider extends Collider
{
    public FlipperCollider(WorldLayer layer, List<ColliderShape> shapes, CollisionType type, int id)
    {
        super(layer, shapes, type, id);
    }

    @Override
    public boolean checkCollision(BallPhysicsElement ball, PhysicsElement element)
    {
        boolean hit = false;
        for (ColliderShape shape : shapes)
        {
            HitInfo info = shape.calculateHitInfo(ball, element.getPosition(), element.getRotation(), element.getBasePhysicsElement().getPivotPoint());
            if (info.isHit())
            {
                type.applyCollision(new CollisionInfo(ball, info.getShortestIntersect(), ((FlipperPhysicsElement)element).getAngularVelocity(), element.getRotation()));
                hit = true;
            }
        }
        return hit;
    }
}
