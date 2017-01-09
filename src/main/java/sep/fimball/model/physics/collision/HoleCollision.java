package sep.fimball.model.physics.collision;

import sep.fimball.general.data.Vector2;
import sep.fimball.model.physics.collider.CircleColliderShape;

/**
 * Created by TheAsuro on 09.01.2017.
 */
public class HoleCollision extends NormalCollision
{
    @Override
    public void applyCollision(CollisionInfo info)
    {
        Vector2 ballCenterOffset = new Vector2(info.getBall().getCollider().getRadius(), info.getBall().getCollider().getRadius());
        Vector2 ballCenterPosition = info.getBall().getPosition().plus(ballCenterOffset);
        double holeRadius = ((CircleColliderShape)info.getOtherColliderShapes().get(0)).getRadius();
        Vector2 holeCenterOffset = new Vector2(holeRadius, holeRadius);
        Vector2 holeCenterPosition = info.getOtherPhysicsElement().getPosition().plus(holeCenterOffset);

        if (ballCenterPosition.minus(holeCenterPosition).magnitude() < holeRadius)
        {
            Vector2 ballToHoleVector = holeCenterPosition.minus(ballCenterPosition);
            info.getBall().setVelocity(info.getBall().getVelocity().plus(ballToHoleVector.scale(10.0)));
        }
    }
}
