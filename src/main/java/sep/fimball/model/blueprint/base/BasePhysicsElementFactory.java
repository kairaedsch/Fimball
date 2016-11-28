package sep.fimball.model.blueprint.base;

import sep.fimball.general.data.Vector2;
import sep.fimball.model.physics.collider.*;
import sep.fimball.model.physics.collision.*;
import sep.fimball.model.physics.element.BasePhysicsElement;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Diese Klasse enthält alle Informationen zu den Physik-Eigenschaften eines BaseElements.
 */
public class BasePhysicsElementFactory
{
    /**
     * Generiert ein BasePhysicsElement aus dem gegebenen PhysicElementJson.
     * @param physicsElement Die Vorlage, aus der das BasePhysicsElement erstellt wird.
     * @return Das generierte BasePhysicsElement.
     */
    static BasePhysicsElement generate(BaseElementJson.PhysicElementJson physicsElement)
    {
        if(physicsElement.pivotPoint == null) throw new NullPointerException("pivotPoint was null");
        Vector2 pivotPoint = physicsElement.pivotPoint;
        List<Collider> colliders = new ArrayList<>();
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
                case "flipper":
                    collisionType = new FlipperCollision();
                    break;
                case "empty":
                    collisionType = new EmptyCollision();
                    break;
                case "ramp":
                    //TODO: Add Ramp Collision
                    collisionType = new EmptyCollision();
                    break;
                default:
                    throw new NullPointerException();
            }

            Collider newCollider;
            if (collider.collisionType.type.equals("flipper"))
                newCollider = new FlipperCollider(collider.layer, shapes, collisionType, collider.colliderId.hashCode());
            else
                newCollider = new Collider(collider.layer, shapes, collisionType, collider.colliderId.hashCode());

            colliders.add(newCollider);
        }

        return new BasePhysicsElement(pivotPoint, colliders);
    }
}
