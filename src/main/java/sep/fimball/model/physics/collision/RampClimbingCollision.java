package sep.fimball.model.physics.collision;

import sep.fimball.general.data.Vector2;
import sep.fimball.model.physics.collider.ColliderShape;
import sep.fimball.model.physics.element.BallPhysicsElement;
import sep.fimball.model.physics.element.PhysicsElement;

import static sep.fimball.model.physics.element.BallPhysicsElement.RADIUS;

public class RampClimbingCollision implements CollisionType
{
    @Override
    public void applyCollision(CollisionInfo info)
    {
        ColliderShape rampColliderShape = info.getOtherColliderShape();
        PhysicsElement physicsElement = info.getOtherPhysicsElement();
        Vector2 relativeBallPos = info.getBall().getPosition().minus(info.getOtherPhysicsElement().getPosition()).plus(new Vector2(RADIUS, RADIUS));

        Vector2 maxPos = rampColliderShape.getExtremePos(physicsElement.getRotation(), physicsElement.getBasePhysicsElement().getPivotPoint(), true);
        Vector2 minPos = rampColliderShape.getExtremePos(physicsElement.getRotation(), physicsElement.getBasePhysicsElement().getPivotPoint(), false);

        if (relativeBallPos.getX() < minPos.getX() || relativeBallPos.getY() < minPos.getY() || relativeBallPos.getX() > maxPos.getX() || relativeBallPos.getY() > maxPos.getY())
        {
            return;
        }

        if ((physicsElement.getRotation() % 360) > 90 && (physicsElement.getRotation() % 360) < 270)
        {
            double maxPosY = maxPos.getY();
            maxPos = new Vector2(maxPos.getX(), minPos.getY());
            minPos = new Vector2(minPos.getX(), maxPosY);
        }

        if ((physicsElement.getRotation() % 360) > 0 && (physicsElement.getRotation() % 360) < 180)
        {
            double maxPosX = maxPos.getX();
            maxPos = new Vector2(minPos.getX(), maxPos.getY());
            minPos = new Vector2(maxPosX, minPos.getY());
        }

        //Eine Gerade die in Richtung der Rampe vom Beginn der Auffahrt zum höchsten Punkt der Auffahrt zeigt.
        Vector2 line = new Vector2(0, -1).rotate(Math.toRadians(physicsElement.getRotation()));
        //Die linksseitige Normale von line.
        Vector2 lineNormal = line.normal();

        Vector2 intersectionAtRampTop = getIntersection(maxPos, line, minPos, lineNormal);
        double rampLength = maxPos.minus(intersectionAtRampTop).magnitude();

        Vector2 intersectionAtBallPos = getIntersection(maxPos, line, relativeBallPos, lineNormal);
        double ballPos = maxPos.minus(intersectionAtBallPos).magnitude();

        //Rechnet die Höhe des Balls basierend auf der Position des Balls und der Länge der gesamten Rampe aus.
        double ballHeight = Math.max(0, Math.min(1, (ballPos / (rampLength - RADIUS)))) * BallPhysicsElement.MAX_HEIGHT;
        info.getBall().setHeight(Math.max(ballHeight, info.getBall().getHeight()));
    }

    /**
     * Berechnet den Schnittpunkt zwischen zwei Geraden welche durch einen Aufpunkt und eine Richtung gegeben sind.
     *
     * @param basePointOne Aufpunkt der ersten Gerade.
     * @param directionOne Richtung der ersten Gerade.
     * @param basePointTwo Aufpunkt der zweiten Gerade.
     * @param directionTwo Richtung der zweiten Gerade.
     *
     * @return Der Schnittpunkt zwischen den zwei Geraden.
     */
    private Vector2 getIntersection(Vector2 basePointOne, Vector2 directionOne, Vector2 basePointTwo, Vector2 directionTwo)
    {
        Vector2 lineOneSecondPoint = basePointOne.plus(directionOne);
        Vector2 lineTwoSecondPoint = basePointTwo.plus(directionTwo);

        double xT = (lineTwoSecondPoint.getX() - basePointTwo.getX()) * (lineOneSecondPoint.getX() * basePointOne.getY() - basePointOne.getX() * lineOneSecondPoint.getY()) - (lineOneSecondPoint.getX() - basePointOne.getX()) * (lineTwoSecondPoint.getX() * basePointTwo.getY() - basePointTwo.getX() * lineTwoSecondPoint.getY());
        double yT = (basePointOne.getY() - lineOneSecondPoint.getY()) * (lineTwoSecondPoint.getX() * basePointTwo.getY() - basePointTwo.getX() * lineTwoSecondPoint.getY()) - (basePointTwo.getY() - lineTwoSecondPoint.getY()) * (lineOneSecondPoint.getX() * basePointOne.getY() - basePointOne.getX() * lineOneSecondPoint.getY());

        double denominator = (lineTwoSecondPoint.getY() - basePointTwo.getY()) * (lineOneSecondPoint.getX() - basePointOne.getX()) - (lineOneSecondPoint.getY() - basePointOne.getY()) * (lineTwoSecondPoint.getX() - basePointTwo.getX());

        return new Vector2(xT / denominator, yT / denominator);
    }
}
