package sep.fimball.model.physics.collider;

import sep.fimball.general.data.Vector2;
import sep.fimball.model.physics.collision.CollisionInfo;
import sep.fimball.model.physics.collision.CollisionType;
import sep.fimball.model.physics.element.BallPhysicsElement;
import sep.fimball.model.physics.element.PhysicsElement;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
    private List<ColliderShape> shapes;

    /**
     * Der Typ der Kollision.
     */
    private CollisionType type;

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
        return Collections.unmodifiableList(shapes);
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