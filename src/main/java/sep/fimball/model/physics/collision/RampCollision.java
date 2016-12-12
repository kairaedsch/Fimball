package sep.fimball.model.physics.collision;

import sep.fimball.model.physics.PhysicsHandler;
import sep.fimball.model.physics.collider.WorldLayer;
import sep.fimball.model.physics.element.BallPhysicsElement;

/**
 * Repräsentiert die Kollision des Balls mit einem Rampenelement. Bei jeder Kollision des Balls mit einer Rampe wird dessen
 * Höhe bis zu einem festgelegten Maximum erhöht.
 */
public class RampCollision implements CollisionType
{
    @Override
    public void applyCollision(CollisionInfo info)
    {
        if (info.getBall().getLayer() == WorldLayer.RAMP)
        {
            info.getBall().setHeight(info.getBall().getHeight() + BallPhysicsElement.GRAVITY_HEIGHT * 1.5 * PhysicsHandler.TICK_RATE_MILLIS);
        }
    }
}
