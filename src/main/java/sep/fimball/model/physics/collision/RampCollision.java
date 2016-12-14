package sep.fimball.model.physics.collision;

import sep.fimball.general.data.PhysicsConfig;
import sep.fimball.model.physics.collider.WorldLayer;

/**
 * Repräsentiert die Kollision des Balls mit einem Rampen-Element. Bei jeder Kollision des Balls mit einer Rampe wird dessen
 * Höhe bis zu einem festgelegten Maximum erhöht. Ebenfalls wird der Ball von der Physik nach unten gezogen.
 */
public class RampCollision implements CollisionType
{
    @Override
    public void applyCollision(CollisionInfo info)
    {
        if (info.getBall().getLayer() == WorldLayer.RAMP)
        {
            /*
             * Multiplier ist ein Wert > 1 damit der Ball auf der Rampe gehalten wird solange er auf dieser ist und nicht durch die Physik, welche mit 1 * HEIGHT_GRAVITY den Ball nach
             * unten zieht, an Höhe verliert.
             */
            double multiplier = 1.5;
            info.getBall().setHeight(info.getBall().getHeight() + PhysicsConfig.HEIGHT_GRAVITY * multiplier * PhysicsConfig.TICK_RATE_SEC);
        }
    }
}
