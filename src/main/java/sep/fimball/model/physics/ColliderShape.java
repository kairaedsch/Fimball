package sep.fimball.model.physics;

/**
 * TODO
 */
public interface ColliderShape
{
    /**
     * TODO
     * @param ball
     * @return
     */
    HitInfo calculateHitInfo(CircleColliderShape ball);
}
