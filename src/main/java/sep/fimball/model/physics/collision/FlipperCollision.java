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

        //Drehe die Achse welche vom Pivot Punkt des Flippers genau mittig durch die Spitze des Flippers zeigt entsprechend der Rotation des Flippers.
        Vector2 flipperAxis = new Vector2(1, 0).rotate(Math.toRadians(flipper.getRotation())).normalized();
        Vector2 flipperPivot = flipper.getPosition().plus(flipper.getBasePhysicsElement().getPivotPoint());

        Vector2 ballPos = info.getBall().getPosition().plus(info.getBall().getBasePhysicsElement().getPivotPoint());

        //Projiziere den Pivot Punkt auf die Achse des Flippers.
        double projectedPivotPosition = flipperPivot.dot(flipperAxis);
        //Projiziere die Mitte des Balls auf die Achse des Flippers.
        double projectedBallPosition = ballPos.dot(flipperAxis);
        //Berechne die Distanz zwischen Pivot Punkt des Flippers und Mitte des Balls.
        double distance = projectedBallPosition - projectedPivotPosition;

        //Berechne den Punkt auf der die Kollision auf der Achse stattgefunden hat.
        Vector2 collisionPoint = flipperPivot.plus(flipperAxis.scale(distance));

        if (((ballPos.getY() > collisionPoint.getY()) && flipper.rotatingUp()) || ((ballPos.getY() < collisionPoint.getY()) && flipper.rotatingDown()))
        {
            //Berechne den Winkel zwischen dem Polygon des Flippers und der Achse des Flippers.
            double angle = flipperAxis.dot(info.getShortestIntersect().normalized());
            System.out.println(angle);
            //Berechne die Kraft die der Flipper auf den Ball überträgt mithilfe der linksseitigen Normale der Achse des Flippers.
            Vector2 addForce = flipperAxis.normal().scale(flipper.getAngularVelocity() * -0.01).scale(distance).scale(1 - angle);
            //Addiere die berechnete Kraft auf die Geschwindigkeit des Balls.
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
