package sep.fimball.model.blueprint.base;

import sep.fimball.general.data.Vector2;
import sep.fimball.model.physics.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by kaira on 15.11.2016.
 */
public class PhysicsElementType
{
    private Vector2 pivotPoint;

    private List<Collider> colliders;

    public PhysicsElementType(BaseElementJson.PhysicElementTypeJson physicsElement)
    {
        pivotPoint = physicsElement.pivotPoint;
        colliders = new ArrayList<>();
        for (BaseElementJson.PhysicElementTypeJson.PhysicColliderJson collider : physicsElement.colliders)
        {
            List<ColliderShape> shapes = new ArrayList<>();

            if(collider.polygonShapes != null) for (BaseElementJson.PhysicElementTypeJson.PhysicColliderJson.PolygonJson polygonShape : collider.polygonShapes)
            {
                PolygonColliderShape polygonColliderShape = new PolygonColliderShape(Arrays.asList(polygonShape.vertices));
                shapes.add(polygonColliderShape);
            }

            if(collider.circleShapes != null) for (BaseElementJson.PhysicElementTypeJson.PhysicColliderJson.CircleJson circleJson : collider.circleShapes)
            {
                CircleColliderShape circleColliderShape = new CircleColliderShape(new Vector2(circleJson.x, circleJson.y), circleJson.radius);
                shapes.add(circleColliderShape);
            }

            CollisionType collisionType;
            switch (collider.collisionType.type)
            {
                case "normal":
                    collisionType = new NormalCollision();
                    break;
                case "acceleration":
                    collisionType = new AccelerationCollision(collider.collisionType.strength);
                    break;
                case "bounce":
                    collisionType = new BounceCollision(collider.collisionType.strength);
                    break;
                default:
                    throw new NullPointerException();
            }

            colliders.add(new Collider(collider.layer, shapes, collisionType, collider.colliderId.hashCode()));
        }
    }

    public Vector2 getPivotPoint()
    {
        return pivotPoint;
    }

    public List<Collider> getColliders()
    {
        return colliders;
    }
}
