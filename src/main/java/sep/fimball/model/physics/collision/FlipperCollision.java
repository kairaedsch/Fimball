package sep.fimball.model.physics.collision;

import sep.fimball.general.data.Vector2;
import sep.fimball.model.physics.element.FlipperPhysicsElement;

/**
 * Diese Klasse repräsentiert die Kollision des Balls mit einem Flipper.
 */
public class FlipperCollision extends NormalCollision
{
    @Override
    public void applyCollision(CollisionInfo info)
    {
        super.applyCollision(info);

        FlipperPhysicsElement flipper = (FlipperPhysicsElement) info.getPhysicsElement();

        Vector2 flipperVel = new Vector2(0, -1).scale(-flipper.getAngularVelocity()).rotate(Math.toRadians(info.getPhysicsElement().getRotation()));
        info.getBall().setVelocity(info.getBall().getVelocity().plus(flipperVel));
    }
}
