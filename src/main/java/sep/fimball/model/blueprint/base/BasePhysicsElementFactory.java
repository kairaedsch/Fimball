package sep.fimball.model.blueprint.base;

import sep.fimball.general.data.Vector2;
import sep.fimball.model.physics.collider.*;
import sep.fimball.model.physics.collision.*;
import sep.fimball.model.physics.element.BasePhysicsElement;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static sep.fimball.model.blueprint.json.JsonUtil.nullCheck;

/**
 * Diese Klasse ist für die Erstellung des BasePhysicsElement eines BaseElement zuständig.
 */
public class BasePhysicsElementFactory
{
    /**
     * Generiert ein BasePhysicsElement aus dem gegebenen PhysicElementJson.
     *
     * @param physicsElement Die Vorlage, aus der das BasePhysicsElement erstellt wird.
     * @return Das generierte BasePhysicsElement.
     */
    static BasePhysicsElement create(BaseElementJson.PhysicElementJson physicsElement)
    {
        nullCheck(physicsElement);
        nullCheck(physicsElement.pivotPoint);
        nullCheck(physicsElement.colliders);

        boolean strengthModificationPossible = false;
        Vector2 pivotPoint = physicsElement.pivotPoint;
        List<Collider> colliders = new ArrayList<>();
        for (BaseElementJson.PhysicElementJson.PhysicColliderJson collider : physicsElement.colliders)
        {
            nullCheck(collider.collisionType);
            nullCheck(collider.collisionType.type);

            List<ColliderShape> shapes = new ArrayList<>();

            if (collider.polygonShapes != null)
            {
                for (BaseElementJson.PhysicElementJson.PhysicColliderJson.PolygonJson polygonShape : collider.polygonShapes)
                {
                    nullCheck(polygonShape.vertices);
                    PolygonColliderShape polygonColliderShape = new PolygonColliderShape(Arrays.asList(polygonShape.vertices));
                    shapes.add(polygonColliderShape);
                }
            }

            if (collider.circleShapes != null)
            {
                for (BaseElementJson.PhysicElementJson.PhysicColliderJson.CircleJson circleJson : collider.circleShapes)
                {
                    CircleColliderShape circleColliderShape = new CircleColliderShape(new Vector2(circleJson.x, circleJson.y), circleJson.radius);
                    shapes.add(circleColliderShape);
                }
            }

            CollisionType collisionType;
            switch (collider.collisionType.type)
            {
                case "normal":
                    collisionType = new NormalCollision();
                    break;
                case "acceleration":
                    nullCheck(collider.collisionType.acceleration);
                    collisionType = new AccelerationCollision(collider.collisionType.acceleration);
                    strengthModificationPossible = true;
                    break;
                case "bounce":
                    collisionType = new BounceCollision(collider.collisionType.strength);
                    strengthModificationPossible = true;
                    break;
                case "flipper":
                    collisionType = new FlipperCollision();
                    strengthModificationPossible = true;
                    break;
                case "empty":
                    collisionType = new EmptyCollision();
                    break;
                case "ramp":
                    collisionType = new RampCollision();
                    break;
                case "rampClimbing":
                    collisionType = new RampClimbingCollision();
                    break;
                case "plunger":
                    collisionType = new PlungerCollision();
                    strengthModificationPossible = true;
                    break;
                case "hole":
                    collisionType = new HoleCollision();
                    break;
                default:
                    throw new IllegalArgumentException("Unknown collision type '" + collider.collisionType.type + "' in PhysicsElement!");
            }

            Collider newCollider;
            WorldLayer layer = collider.layer;
            if (!(collider.collisionType.type).equals("empty"))
                nullCheck(layer);

            newCollider = new Collider(layer, shapes, collisionType, collider.colliderId);
            colliders.add(newCollider);
        }
        return new BasePhysicsElement(pivotPoint, colliders, strengthModificationPossible);
    }
}
