package sep.fimball.model.physics.collider;

import sep.fimball.model.physics.collision.CollisionInfo;
import sep.fimball.model.physics.collision.CollisionType;
import sep.fimball.model.physics.element.BallPhysicsElement;
import sep.fimball.model.physics.element.FlipperPhysicsElement;
import sep.fimball.model.physics.element.PhysicsElement;

import java.util.List;

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
                type.applyCollision(new CollisionInfo(ball, info.getShortestIntersect(), element.getRotation(), (FlipperPhysicsElement)element));
                hit = true;
            }
        }
        return hit;
    }
}
