package sep.fimball.model.blueprint.base;

import sep.fimball.general.data.Vector2;
import sep.fimball.model.physics.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Diese Klasse enthält alle Informationen zu den Physik-Eigenschaften eines BaseElements.
 */
public class BasePhysicsElement
{
    /**
     * Die Position des Pivot-Punktes des Elements.
     */
    private Vector2 pivotPoint;

    /**
     * Die Collider, die dieses Element hat.
     */
    private List<Collider> colliders;

    /**
     * Erstellt ein neues BasePhysicsElement.
     *
     * @param physicsElement Das PhysicElementJson, dessen Eigenschaften übernommen werden sollen.
     */
    public BasePhysicsElement(BaseElementJson.PhysicElementJson physicsElement)
    {
        if(physicsElement.pivotPoint == null) throw new NullPointerException("pivotPoint was null");
        pivotPoint = physicsElement.pivotPoint;
        colliders = new ArrayList<>();
        for (BaseElementJson.PhysicElementJson.PhysicColliderJson collider : physicsElement.colliders)
        {
            List<ColliderShape> shapes = new ArrayList<>();

            if (collider.polygonShapes != null)
                for (BaseElementJson.PhysicElementJson.PhysicColliderJson.PolygonJson polygonShape : collider.polygonShapes)
                {
                    PolygonColliderShape polygonColliderShape = new PolygonColliderShape(Arrays.asList(polygonShape.vertices));
                    shapes.add(polygonColliderShape);
                }

            if (collider.circleShapes != null)
                for (BaseElementJson.PhysicElementJson.PhysicColliderJson.CircleJson circleJson : collider.circleShapes)
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
