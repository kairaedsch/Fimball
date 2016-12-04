package sep.fimball.model.physics.collision;

import sep.fimball.model.physics.PhysicsHandler;

public class RampClimbingCollision implements CollisionType
{
    @Override
    public void applyCollision(CollisionInfo info)
    {
        info.getBall().setHeight(info.getBall().getHeight() + 20 * (PhysicsHandler.TICK_RATE / 1000.0));
    }
}
