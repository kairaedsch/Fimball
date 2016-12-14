package sep.fimball.model.physics.collision;

import javafx.scene.paint.Color;
import sep.fimball.general.data.Vector2;
import sep.fimball.general.debug.Debug;
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

        Vector2 plungerAxis = new Vector2(1, 0).rotate(Math.toRadians(plunger.getRotation())).normalized();
        Vector2 plungerPivot = plunger.getPosition().plus(plunger.getBasePhysicsElement().getPivotPoint());

        Vector2 ballPos = info.getBall().getPosition().plus(info.getBall().getBasePhysicsElement().getPivotPoint());

        double projectedPivotPosition = plungerPivot.dot(plungerAxis);
        double projectedBallPosition = ballPos.dot(plungerAxis);
        double distance = projectedBallPosition - projectedPivotPosition;

        Vector2 addForce = plungerAxis.normal().scale(plunger.getStrength()).scale(distance);
        Debug.addDrawVector(ballPos, addForce, Color.YELLOWGREEN);
        info.getBall().setVelocity(info.getBall().getVelocity().plus(addForce));
    }
}
