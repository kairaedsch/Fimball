package sep.fimball.model.physics;

/**
 * Reprästentiert eine Barriere für den Ball, an der dieser abprallt und/oder mögliche weitere physikalische Kräfte auf ihn einwirken. Außerdem kann bei Berührung eine Animation ausgelöst werden.
 */
public class Collider
{
    /**
     * Ebene, auf der die Barriere sich befindet. Diese kann sich entweder auf Bodenhöhe des Flipperautomaten befinden, oder auf der Höhe einer Rampe.
     */
    private WorldLayer layer;

    private CollisionShape shape;

    private CollisionType type;

    public Collider(WorldLayer layer, CollisionShape shape, CollisionType type)
    {
        this.layer = layer;
        this.shape = shape;
        this.type = type;
    }

    public void checkCollision(BallElement ball)
    {
        HitInfo info = shape.calculateHitInfo(ball.getCollider()); // TODO idk ball is weird
        if (info.isHit())
            type.applyCollision(ball, info.getShortestIntersect());
    }
}