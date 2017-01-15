package sep.fimball.model.physics.collision;

import sep.fimball.general.data.PhysicsConfig;
import sep.fimball.general.data.Vector2;
import sep.fimball.model.physics.collider.CircleColliderShape;
import sep.fimball.model.physics.element.HolePhysicsElement;

/**
 * Verarbeitet die Überschneidung des Balls mit einem Loch.
 */
public class HoleCollision extends NormalCollision
{
    @Override
    public void applyCollision(CollisionInfo info)
    {
        Vector2 ballCenterOffset = new Vector2(info.getBall().getCollider().getRadius(), info.getBall().getCollider().getRadius());
        Vector2 ballCenterPosition = info.getBall().getPosition().plus(ballCenterOffset);
        double holeRadius = ((CircleColliderShape) info.getOtherColliderShapes().get(0)).getRadius();
        Vector2 holeCenterOffset = new Vector2(holeRadius, holeRadius);
        Vector2 holeCenterPosition = info.getOtherPhysicsElement().getPosition().plus(holeCenterOffset);
        double distanceToCenter = ballCenterPosition.minus(holeCenterPosition).magnitude();

        HolePhysicsElement physicsElement = (HolePhysicsElement) info.getOtherPhysicsElement();

        if (physicsElement.canAffectBall())
        {
            if (distanceToCenter <= PhysicsConfig.CENTER_AREA_RADIUS)
            {
                physicsElement.tryFreezeBall(info.getBall());
                info.getBall().setPosition(holeCenterPosition.minus(ballCenterOffset));
            }
            else if (distanceToCenter < holeRadius)
            {
                // Ball is over the edge of the hole, it will start rolling towards the center
                Vector2 oldVel = info.getBall().getVelocity();
                Vector2 combinedVel = holeCenterPosition.minus(ballCenterPosition).plus(oldVel);
                double ballSpeed = Math.max(oldVel.magnitude(), PhysicsConfig.MIN_HOLE_SPEED);
                Vector2 newVel = combinedVel.normalized().scale(ballSpeed);
                info.getBall().setVelocity(oldVel.lerp(newVel, PhysicsConfig.HOLE_DIRECTION_CHANGE_RATE));
            }
        }
    }
}
