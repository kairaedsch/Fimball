package sep.fimball.model.physics.collision;

import sep.fimball.general.data.Vector2;

/**
 * Diese Klasse repräsentiert die Kollision des Balls mit einem Flipper.
 */
public class FlipperCollision extends NormalCollision
{
    @Override
    public void applyCollision(CollisionInfo info)
    {
        Vector2 flipperVel = new Vector2(0, -1).scale(info.getFlipper().getAngularVelocity()).rotate(Math.toRadians(info.getRotation()));
        Vector2 relativeVel = info.getBall().getVelocity().minus(flipperVel);
        Vector2 newVel = calculateNewSpeed(relativeVel, info.getShortestIntersect(), BOUNCE);
        info.getBall().setVelocity(newVel);
    }
}
