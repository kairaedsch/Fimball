package sep.fimball.model.physics.collider;

import sep.fimball.general.data.Vector2;
import sep.fimball.model.physics.collision.CollisionInfo;
import sep.fimball.model.physics.collision.CollisionType;
import sep.fimball.model.physics.element.BallPhysicsElement;
import sep.fimball.model.physics.element.PhysicsElement;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Repräsentiert eine Barriere für den Ball, an der dieser abprallt und/oder mögliche weitere physikalische Kräfte auf ihn einwirken.
 */
public class Collider
{
    /**
     * Ebene, auf der die Barriere sich befindet. Diese kann sich entweder auf Bodenhöhe des Flipperautomaten befinden, oder auf der Höhe einer Rampe.
     */
    private WorldLayer layer;

    /**
     * Eine Liste der ColliderShapes des Colliders.
     */
    protected List<ColliderShape> shapes;

    /**
     * Der Typ der Kollision.
     */
    protected CollisionType type;

    /**
     * Die ID des Colliders.
     */
    private int id;

    /**
     * Erzeugt einen neuen Collider.
     *
     * @param layer  Die Ebene, auf der sich der Collider befindet.
     * @param shapes Die ColliderShapes des Colliders.
     * @param type   Der Typ der Kollision.
     * @param id     Die ID des Colliders.
     */
    public Collider(WorldLayer layer, List<ColliderShape> shapes, CollisionType type, int id)
    {
        this.layer = layer;
        this.shapes = shapes;
        this.type = type;
        this.id = id;
    }

    /**
     * Überprüft, ob das gegebene BallPhysicsElement eine Kollision mit einer der ColliderShapes des Colliders hat.
     *
     * @param ball    Der Ball, dessen Kollisionen überprüft werden sollen.
     * @param element Das Element, bei dem die Kollision mit dem Ball überprüft wird
     * @return {@code true}, wenn eine Kollision stattfindet, {@code false} sonst.
     */
    public boolean checkCollision(BallPhysicsElement ball, PhysicsElement element)
    {
        if (ball.getLayer() != layer && layer != WorldLayer.BOTH)
            return false;

        List<HitInfo> hits = new ArrayList<>();
        List<ColliderShape> collidedShapes = new ArrayList<>();

        // Find all shapes the ball collides with
        for (ColliderShape shape : shapes)
        {
            HitInfo info = shape.calculateHitInfo(ball.getCollider(), ball.getPosition(), element.getPosition(), element.getRotation(), element.getBasePhysicsElement().getPivotPoint());
            if (info.isHit())
            {
                hits.add(info);
                collidedShapes.add(shape);
            }
        }

        // Join shapes if they share vertices
        List<PolygonColliderShape> polys = collidedShapes
                .stream()
                .filter(shape -> shape.getClass().equals(PolygonColliderShape.class))
                .map(shape -> (PolygonColliderShape)shape)
                .filter(shape -> shape.getVertices().size() == 4)
                .collect(Collectors.toList());
        List<PolygonColliderShape> markedForRemoveShapes = new ArrayList<>();
        List<PolygonColliderShape> combinedShapes = new ArrayList<>();
        for (PolygonColliderShape hitShape : polys)
        {
            for (PolygonColliderShape otherHitShape : polys)
            {
                List<Integer> contactPoints = new ArrayList<>();
                List<Integer> otherContactPoints = new ArrayList<>();

                for (int i = 0; i < hitShape.getVertices().size(); i++)
                {
                    for (int j = 0; j < otherHitShape.getVertices().size(); j++)
                    {
                        if (hitShape.getVertices().get(i).equals(otherHitShape.getVertices().get(j)))
                        {
                            contactPoints.add(i);
                            otherContactPoints.add(j);
                        }
                    }
                }

                if (contactPoints.size() == 2)
                {
                    List<Vector2> newVertices = new ArrayList<>();

                    int firstShapeIndex = 0;
                    while (!contactPoints.contains(firstShapeIndex))
                    {
                        newVertices.add(hitShape.getVertices().get(firstShapeIndex));
                        firstShapeIndex++;
                    }

                    firstShapeIndex++;
                    newVertices.add(hitShape.getVertices().get(firstShapeIndex));

                    int otherShapeIndex = 0;
                    do
                    {
                        newVertices.add(otherHitShape.getVertices().get(otherShapeIndex));
                        otherShapeIndex++;
                    } while (!otherContactPoints.contains(otherShapeIndex));

                    for (int i = firstShapeIndex; i < hitShape.getVertices().size(); i++)
                    {
                        newVertices.add(hitShape.getVertices().get(i));
                    }

                    combinedShapes.add(new PolygonColliderShape(newVertices));
                    markedForRemoveShapes.add(hitShape);
                    markedForRemoveShapes.add(otherHitShape);
                }
            }
        }

        if (collidedShapes.size() > 1)
        {
            collidedShapes.removeAll(markedForRemoveShapes);
            collidedShapes.addAll(combinedShapes);
        }

        if (hits.size() > 0)
        {
            Vector2 shortestCombinedIntersect = new Vector2();
            for (HitInfo hit : hits)
            {
                shortestCombinedIntersect = shortestCombinedIntersect.plus(hit.getShortestIntersect());
            }
            shortestCombinedIntersect.scale(1 / hits.size());
            type.applyCollision(new CollisionInfo(ball, shortestCombinedIntersect, element, collidedShapes));

            return true;
        }

        return false;
    }

    /**
     * Gibt die Liste der ColliderShapes des Colliders zurück.
     *
     * @return Die Liste der ColliderShapes des Colliders.
     */
    public List<ColliderShape> getShapes()
    {
        return shapes;
    }

    /**
     * Gibt die ID des Colliders zurück.
     *
     * @return Die ID des Colliders.
     */
    public int getId()
    {
        return id;
    }
}