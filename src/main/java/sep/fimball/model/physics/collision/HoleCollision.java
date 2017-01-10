package sep.fimball.model.physics.collision;

import sep.fimball.general.data.Vector2;
import sep.fimball.model.physics.collider.CircleColliderShape;

/**
 * Created by TheAsuro on 09.01.2017.
 */
public class HoleCollision extends NormalCollision
{
    /**
     * If the ball is this close to the center of the hole, it will ~do stuff~
     */
    private final double CENTER_AREA_RADIUS = 0.6;
    private final double MIN_HOLE_SPEED = 3.0;
    private final double HOLE_DIRECTION_CHANGE_RATE = 0.25;

    @Override
    public void applyCollision(CollisionInfo info)
    {
        Vector2 ballCenterOffset = new Vector2(info.getBall().getCollider().getRadius(), info.getBall().getCollider().getRadius());
        Vector2 ballCenterPosition = info.getBall().getPosition().plus(ballCenterOffset);
        double holeRadius = ((CircleColliderShape)info.getOtherColliderShapes().get(0)).getRadius();
        Vector2 holeCenterOffset = new Vector2(holeRadius, holeRadius);
        Vector2 holeCenterPosition = info.getOtherPhysicsElement().getPosition().plus(holeCenterOffset);
        double distanceToCenter = ballCenterPosition.minus(holeCenterPosition).magnitude();

        if (distanceToCenter <= CENTER_AREA_RADIUS)
        {
            // TODO freeze ball for a while, then release it
            info.getBall().setVelocity(new Vector2(0.0, 0.0));
            info.getBall().setPosition(holeCenterPosition.minus(ballCenterOffset));
        }
        else if (distanceToCenter < holeRadius)
        {
            // Ball is over the edge of the hole, it will start rolling towards the center
            Vector2 oldVel = info.getBall().getVelocity();
            Vector2 combinedVel = holeCenterPosition.minus(ballCenterPosition).plus(oldVel);
            double ballSpeed = Math.max(oldVel.magnitude(), MIN_HOLE_SPEED);
            Vector2 newVel = combinedVel.normalized().scale(ballSpeed);
            info.getBall().setVelocity(oldVel.lerp(newVel, HOLE_DIRECTION_CHANGE_RATE));
        }
    }
}
