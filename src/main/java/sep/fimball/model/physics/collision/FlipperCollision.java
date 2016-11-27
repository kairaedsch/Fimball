package sep.fimball.model.physics.collision;

import javafx.scene.paint.Color;
import sep.fimball.general.data.Vector2;
import sep.fimball.general.debug.Debug;

/**
 * Diese Klasse repräsentiert die Kollision des Balls mit einem Flipper.
 */
public class FlipperCollision extends NormalCollision
{
    //public FlipperCollision()

    @Override
    public void applyCollision(CollisionInfo info)
    {
        super.applyCollision(info);
        Vector2 force = new Vector2(0, -1).scale(info.getAngularVelocity()).rotate(Math.toRadians(info.getRotation()));
        Debug.addDrawVector(info.getBall().getPosition(), force, Color.BLUE);
        info.getBall().setVelocity(info.getBall().getVelocity().plus(force));
    }
}
