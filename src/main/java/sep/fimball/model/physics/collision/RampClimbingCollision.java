package sep.fimball.model.physics.collision;

import sep.fimball.general.data.Vector2;
import sep.fimball.model.physics.collider.ColliderShape;
import sep.fimball.model.physics.element.BallPhysicsElement;
import sep.fimball.model.physics.element.PhysicsElement;

public class RampClimbingCollision implements CollisionType
{
    @Override
    public void applyCollision(CollisionInfo info)
    {
        ColliderShape shape = info.getShape();
        PhysicsElement physicsElement = info.getPhysicsElement();
        Vector2 relativeBallPos = info.getBall().getPosition().minus(info.getPhysicsElement().getPosition()).plus(new Vector2(BallPhysicsElement.radius, BallPhysicsElement.radius));

        Vector2 maxPos = shape.getExtremePos(physicsElement.getRotation(), physicsElement.getBasePhysicsElement().getPivotPoint(), true);
        Vector2 minPos = shape.getExtremePos(physicsElement.getRotation(), physicsElement.getBasePhysicsElement().getPivotPoint(), false);

        if((physicsElement.getRotation() % 360) > 90 && (physicsElement.getRotation() % 360) < 270)
        {
            double maxPosY = maxPos.getY();
            maxPos = new Vector2(maxPos.getX(), minPos.getY());
            minPos = new Vector2(minPos.getX(), maxPosY);
        }

        if((physicsElement.getRotation() % 360) > 0 && (physicsElement.getRotation() % 360) < 180)
        {
            double maxPosX = maxPos.getX();
            maxPos = new Vector2(minPos.getX(), maxPos.getY());
            minPos = new Vector2(maxPosX, minPos.getY());
        }

        Vector2 gerade_direction = new Vector2(0, -1).rotate(Math.toRadians(physicsElement.getRotation()));
        Vector2 gerade_direction_normale = gerade_direction.normal();

        Vector2 schnittpunkt = schnittpunkt(maxPos, gerade_direction, minPos, gerade_direction_normale);
        double fieldLength = maxPos.minus(schnittpunkt).magnitude();

        Vector2 schnittpunkt2 = schnittpunkt(maxPos, gerade_direction, relativeBallPos, gerade_direction_normale);
        double ballPos = maxPos.minus(schnittpunkt2).magnitude();

        double height = (ballPos / fieldLength) * BallPhysicsElement.maxHeight;

        info.getBall().setHeight(Math.max(height, info.getBall().getHeight()));
    }

    private Vector2 schnittpunkt(Vector2 p1, Vector2 v1, Vector2 p3, Vector2 v3)
    {
        Vector2 p2 = p1.plus(v1);
        Vector2 p4 = p3.plus(v3);

        double xT = (p4.getX() - p3.getX()) * (p2.getX() * p1.getY() - p1.getX() * p2.getY()) - (p2.getX() - p1.getX()) * (p4.getX() * p3.getY() - p3.getX() * p4.getY());
        double yT = (p1.getY() - p2.getY()) * (p4.getX() * p3.getY() - p3.getX() * p4.getY()) - (p3.getY() - p4.getY()) * (p2.getX() * p1.getY() - p1.getX() * p2.getY());

        double divider = (p4.getY() - p3.getY()) * (p2.getX() - p1.getX()) - (p2.getY() - p1.getY()) * (p4.getX() - p3.getX());

        return new Vector2(xT / divider, yT / divider);
    }
}
