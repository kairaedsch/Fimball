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
        callNormalCollision(info);
        FlipperPhysicsElement flipper = (FlipperPhysicsElement) info.getOtherPhysicsElement();

        Vector2 flipperAxis = new Vector2(1, 0).rotate(Math.toRadians(flipper.getRotation())).normalized();
        Vector2 flipperPivot = flipper.getPosition().plus(flipper.getBasePhysicsElement().getPivotPoint());

        Vector2 ballPos = info.getBall().getPosition().plus(info.getBall().getBasePhysicsElement().getPivotPoint());

        double projectedPivotPosition = flipperPivot.dot(flipperAxis);
        double projectedBallPosition = ballPos.dot(flipperAxis);
        double distance = projectedBallPosition - projectedPivotPosition;

        Vector2 collisionPoint = flipperPivot.plus(flipperAxis.scale(distance));

        if (((ballPos.getY() > collisionPoint.getY()) && flipper.rotatingUp()) || ((ballPos.getY() < collisionPoint.getY()) && flipper.rotatingDown()))
        {
            double angle = flipperAxis.dot(info.getShortestIntersect().normalized());
            Vector2 addForce = flipperAxis.normal().scale(flipper.getAngularVelocity() * -0.1).scale(distance).scale(1 - angle);
            info.getBall().setVelocity(info.getBall().getVelocity().plus(addForce));
        }
    }

    /**
     * Der Aufruf der super Methode wurde in diese Methode verlagert um bessere Testbarkeit zu erreichen.
     *
     * @param info Information über die aufgetretene Kollision.
     */
    protected void callNormalCollision(CollisionInfo info)
    {
        super.applyCollision(info);
    }
}
