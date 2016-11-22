package sep.fimball.model.physics.collider;

import sep.fimball.general.data.Vector2;
import sep.fimball.model.physics.element.BallPhysicsElement;
import sep.fimball.model.physics.collision.CollisionType;

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
     * @param ball                   Der Ball, dessen Kollisionen überprüft werden sollen.
     * @param colliderObjectPosition Die Position des Elements, mit dem kollidiert wird.
     * @param rotation               Die Drehung des Elements, mit dem kollidiert wird.
     * @param pivotPoint             Der Pivot-Punkt des Elements, mit dem kollidiert wird.
     * @return {@code true}, wenn eine Kollision stattfindet, {@code false} sonst.
     */
    public boolean checkCollision(BallPhysicsElement ball, Vector2 colliderObjectPosition, double rotation, Vector2 pivotPoint)
    {
        boolean hit = false;
        for (ColliderShape shape : shapes)
        {
            HitInfo info = shape.calculateHitInfo(ball, colliderObjectPosition, rotation, pivotPoint);
            if (info.isHit())
            {
                type.applyCollision(ball, info.getShortestIntersect(), rotation);
                hit = true;
            }
        }
        return hit;
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

    public int getId()
    {
        return id;
    }
}