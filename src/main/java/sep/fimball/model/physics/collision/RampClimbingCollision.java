package sep.fimball.model.physics.collision;

import sep.fimball.general.data.PhysicsConfig;
import sep.fimball.general.data.Vector2;
import sep.fimball.model.physics.collider.ColliderShape;
import sep.fimball.model.physics.element.PhysicsElement;
import sun.plugin.dom.exception.InvalidStateException;

import java.util.List;
import java.util.Optional;

public class RampClimbingCollision implements CollisionType
{
    @Override
    public void applyCollision(CollisionInfo info)
    {
        double ballRadius = info.getBall().getCollider().getRadius();

        List<ColliderShape> rampColliderShapes = info.getOtherColliderShapes();
        PhysicsElement physicsElement = info.getOtherPhysicsElement();
        Vector2 relativeBallPos = info.getBall().getPosition().minus(info.getOtherPhysicsElement().getPosition()).plus(new Vector2(ballRadius, ballRadius));

        //Maximale x und y Position der Rampen ColliderShape.
        Optional<Vector2> maxPosOptional = rampColliderShapes.stream()
                .map(shape -> shape.getExtremePos(physicsElement.getRotation(), physicsElement.getBasePhysicsElement().getPivotPoint(), true))
                .reduce(Vector2::max);
        if (!maxPosOptional.isPresent())
            throw new InvalidStateException("Shape didn't have any positions.");
        Vector2 maxPos = maxPosOptional.get();

        //Minimale x und y Position der Rampen ColliderShape.
        Optional<Vector2> minPosOptinal = rampColliderShapes.stream()
                .map(shape -> shape.getExtremePos(physicsElement.getRotation(), physicsElement.getBasePhysicsElement().getPivotPoint(), false))
                .reduce(Vector2::min);
        if (!minPosOptinal.isPresent())
            throw new InvalidStateException("Shape didn't have any positions.");
        Vector2 minPos = minPosOptinal.get();

        //Wenn sich der Ball nicht auf der Rampe befindet kann abgebrochen werden.
        if (relativeBallPos.getX() < minPos.getX() || relativeBallPos.getY() < minPos.getY() || relativeBallPos.getX() > maxPos.getX() || relativeBallPos.getY() > maxPos.getY())
        {
            return;
        }

        //Anpassung der Extremwerte bei Rotationen zwischen > 90 und < 270
        if ((physicsElement.getRotation() % 360) > 90 && (physicsElement.getRotation() % 360) < 270)
        {
            double maxPosY = maxPos.getY();
            maxPos = new Vector2(maxPos.getX(), minPos.getY());
            minPos = new Vector2(minPos.getX(), maxPosY);
        }

        //Anpassung der Extremwerte bei Rotationen zwischen > 0 und < 180
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
        double ballHeight = Math.max(0, Math.min(1, (ballPos / (rampLength - ballRadius)))) * PhysicsConfig.MAX_BALL_HEIGHT;
        //Da der Ball an Höhe gewinnen soll setze die Höhe des Balls auf das Größere von ballHeight und der aktuellen Ball Höhe.
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
        //Berechne den zweiten Punkt der ersten Gerade durch Aufpunkt + Richtung.
        Vector2 lineOneSecondPoint = basePointOne.plus(directionOne);
        //Berechne den zweiten Punkt der zweiten Gerade durch Aufpunkt + Richtung.
        Vector2 lineTwoSecondPoint = basePointTwo.plus(directionTwo);

        //Berechne den Schnittpunkt zwischen den beiden Geraden mithilfe der Formel für den Schnittpunkt von zwei Geraden in der Zweipunkteform.
        double xT = (lineTwoSecondPoint.getX() - basePointTwo.getX()) * (lineOneSecondPoint.getX() * basePointOne.getY() - basePointOne.getX() * lineOneSecondPoint.getY()) - (lineOneSecondPoint.getX() - basePointOne.getX()) * (lineTwoSecondPoint.getX() * basePointTwo.getY() - basePointTwo.getX() * lineTwoSecondPoint.getY());
        double yT = (basePointOne.getY() - lineOneSecondPoint.getY()) * (lineTwoSecondPoint.getX() * basePointTwo.getY() - basePointTwo.getX() * lineTwoSecondPoint.getY()) - (basePointTwo.getY() - lineTwoSecondPoint.getY()) * (lineOneSecondPoint.getX() * basePointOne.getY() - basePointOne.getX() * lineOneSecondPoint.getY());
        double denominator = (lineTwoSecondPoint.getY() - basePointTwo.getY()) * (lineOneSecondPoint.getX() - basePointOne.getX()) - (lineOneSecondPoint.getY() - basePointOne.getY()) * (lineTwoSecondPoint.getX() - basePointTwo.getX());

        return new Vector2(xT / denominator, yT / denominator);
    }
}
