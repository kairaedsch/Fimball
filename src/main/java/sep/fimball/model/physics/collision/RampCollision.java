package sep.fimball.model.physics.collision;

import sep.fimball.model.physics.PhysicsHandler;
import sep.fimball.model.physics.collider.WorldLayer;
import sep.fimball.model.physics.element.BallPhysicsElement;

/**
 * Repräsentiert die Kollision des Balls mit einem Rampenelement. Bei jeder Kollision des Balls mit einer Rampe wird dessen
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
             * Multiplier ist ein Wert > 1 damit der Ball auf der Rampe gehalten wird solange er auf dieser ist und nicht durch die Physik, welche mit 1 * GRAVITY_HEIGHT den Ball nach
             * unten zieht, an Höhe verliert.
             */
            double multiplier = 1.5;
            info.getBall().setHeight(info.getBall().getHeight() + BallPhysicsElement.GRAVITY_HEIGHT * multiplier * PhysicsHandler.TICK_RATE_MILLIS);
        }
    }
}
