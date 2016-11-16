package sep.fimball.model.physics;

import java.util.List;

/**
 * Reprästentiert eine Barriere für den Ball, an der dieser abprallt und/oder mögliche weitere physikalische Kräfte auf ihn einwirken. Außerdem kann bei Berührung eine Animation ausgelöst werden.
 */
public class Collider
{
    /**
     * Ebene, auf der die Barriere sich befindet. Diese kann sich entweder auf Bodenhöhe des Flipperautomaten befinden, oder auf der Höhe einer Rampe.
     */
    private WorldLayer layer;

    private List<ColliderShape> shapes;

    private CollisionType type;

    private int id;

    public Collider(WorldLayer layer, List<ColliderShape> shapes, CollisionType type, int id)
    {
        this.layer = layer;
        this.shapes = shapes;
        this.type = type;
        this.id = id;
    }

    public void checkCollision(BallElement ball)
    {
        for (ColliderShape shape : shapes)
        {
            HitInfo info = shape.calculateHitInfo(ball.getCollider());
            if (info.isHit())
                type.applyCollision(ball, info.getShortestIntersect());
        }
    }

    public List<ColliderShape> getShapes()
    {
        return shapes;
    }
}