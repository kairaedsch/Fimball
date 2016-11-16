package sep.fimball.model.physics;

/**
 * Created by TheAsuro on 15.11.2016.
 */
public interface ColliderShape
{
    HitInfo calculateHitInfo(CircleColliderShape ball);
}
