package sep.fimball.model.physics.collision;

import sep.fimball.general.data.PhysicsConfig;
import sep.fimball.general.data.Vector2;
import sep.fimball.model.physics.element.PlungerPhysicsElement;

/**
 * Diese Klasse repräsentiert die Kollision des Balls mit einem Plunger.
 */
public class PlungerCollision extends NormalCollision
{

    @Override
    public void applyCollision(CollisionInfo info)
    {
        super.applyCollision(info);

        PlungerPhysicsElement plunger = (PlungerPhysicsElement) info.getOtherPhysicsElement();
        double plungerStrength = plunger.getStrength();

        if (plungerStrength > 0)
        {
            Vector2 plungerAxis = new Vector2(0, -1).rotate(Math.toRadians(plunger.getRotation())).normalized();
            Vector2 addForce = plungerAxis.scale(plunger.getStrength()).scale(PhysicsConfig.TICK_RATE_SEC);
            info.getBall().setVelocity(info.getBall().getVelocity().plus(addForce));
            plunger.resetStrength();
        }
    }
}
