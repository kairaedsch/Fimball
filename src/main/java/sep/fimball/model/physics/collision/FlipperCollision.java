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
        Vector2 flipperVel = new Vector2(0, -1).scale(-((FlipperPhysicsElement) info.getPhysicsElement()).getAngularVelocity()).rotate(Math.toRadians(info.getPhysicsElement().getRotation()));
        Vector2 relativeVel = info.getBall().getVelocity().minus(flipperVel);
        Vector2 newVel = calculateNewSpeed(relativeVel, info.getShortestIntersect().normalized(), BOUNCE);
        info.getBall().setVelocity(newVel);
    }
}
