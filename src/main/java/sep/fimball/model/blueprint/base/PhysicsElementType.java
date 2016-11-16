package sep.fimball.model.blueprint.base;

import sep.fimball.general.data.Vector2;
import sep.fimball.model.blueprint.json.BaseElementJson;
import sep.fimball.model.physics.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by kaira on 15.11.2016.
 */
public class PhysicsElementType
{
    private List<Collider> colliders;

    public PhysicsElementType(BaseElementJson.PhysicElementTypeJson physicElement)
    {
        colliders = new ArrayList<>();
        for (BaseElementJson.PhysicElementTypeJson.PhysicColliderJson collider : physicElement.colliders)
        {
            List<CollisionShape> shapes = new ArrayList<>();

            if(collider.polygonShapes != null) for (BaseElementJson.PhysicElementTypeJson.PhysicColliderJson.PolygonJson polygonShape : collider.polygonShapes)
            {
                PolygonCollider polygonCollider = new PolygonCollider(Arrays.asList(polygonShape.vertices));
                shapes.add(polygonCollider);
            }

            if(collider.circleShapes != null) for (BaseElementJson.PhysicElementTypeJson.PhysicColliderJson.CircleJson circleJson : collider.circleShapes)
            {
                CircleCollider circleCollider = new CircleCollider(new Vector2(circleJson.x, circleJson.y), circleJson.radius);
                shapes.add(circleCollider);
            }

            CollisionType collisionType;
            switch (collider.collisionType.type)
            {
                case "normal":
                    collisionType = new NormalCollision();
                    break;
                case "acceleration":
                    collisionType = new AccelerationCollision(new Vector2(collider.collisionType.accelX, collider.collisionType.accelY));
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

    public List<Collider> getColliders()
    {
        return colliders;
    }
}
