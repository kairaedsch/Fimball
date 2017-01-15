package sep.fimball.model.physics.collision;

import sep.fimball.general.data.PhysicsConfig;
import sep.fimball.general.data.Vector2;
import sep.fimball.model.physics.element.SpinnerPhysicsElement;

/**
 * Created by alexcekay on 15.01.17.
 */
public class SpinnerCollision implements CollisionType
{
    @Override
    public void applyCollision(CollisionInfo info)
    {
        SpinnerPhysicsElement spinnerPhysicsElement = (SpinnerPhysicsElement)info.getOtherPhysicsElement();
        Vector2 spinnerDirection = new Vector2(0, -1).rotate(Math.toRadians(spinnerPhysicsElement.getRotation())).scale(PhysicsConfig.TICK_RATE_SEC);
        double spinnerAcceleration = Math.abs(spinnerDirection.normalized().dot(info.getBall().getVelocity().normalized()));
        spinnerPhysicsElement.setSpinnerAcceleration(spinnerAcceleration);
    }
}
