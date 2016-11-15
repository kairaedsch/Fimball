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

    private List<CollisionShape> shapes;

    private CollisionType type;

    public Collider(WorldLayer layer, List<CollisionShape> shapes, CollisionType type)
    {
        this.layer = layer;
        this.shapes = shapes;
        this.type = type;
    }

    public void checkCollision(BallElement ball)
    {
        for (CollisionShape shape : shapes)
        {
            HitInfo info = shape.calculateHitInfo(ball.getCollider()); // TODO idk ball is weird
            if (info.isHit())
                type.applyCollision(ball, info.getShortestIntersect());
        }
    }
}