package sep.fimball.model.physics.element;

import sep.fimball.general.data.Vector2;
import sep.fimball.model.physics.collider.Collider;
import sep.fimball.model.physics.game.CollisionEventArgs;

import java.util.List;

/**
 * TODO
 */
public class FlipperPhysicsElement<GameElementT> extends PhysicsElement<GameElementT> implements PhysicsUpdateable
{
    private static final double movingAngularVelocity = 500.0;
    private static final double minRotation = -15.0;
    private static final double maxRotation = 15.0;

    private double angularVelocity = 0.0;

    private boolean isLeft;

    public FlipperPhysicsElement(GameElementT gameElement, Vector2 position, BasePhysicsElement basePhysicsElement, boolean isLeft)
    {
        super(gameElement, position, isLeft ? maxRotation : minRotation, basePhysicsElement);
        this.isLeft = isLeft;
    }

    public boolean rotatingUp()
    {
        if (isLeft)
            return angularVelocity > 0;
        else
            return angularVelocity < 0;
    }

    public boolean rotatingDown()
    {
        if (isLeft)
            return angularVelocity < 0;
        else
            return angularVelocity > 0;
    }

    public void rotateUp()
    {
        if (isLeft)
        {
            if (getRotation() < maxRotation)
                angularVelocity = movingAngularVelocity;
            else
                angularVelocity = 0.0;
        }
        else
        {
            if (getRotation() > minRotation)
                angularVelocity = -movingAngularVelocity;
            else
                angularVelocity = 0.0;
        }
    }

    public void rotateDown()
    {
        if (isLeft)
        {
            if (getRotation() > minRotation)
                angularVelocity = -movingAngularVelocity;
            else
                angularVelocity = 0.0;
        }
        else
        {
            if (getRotation() < maxRotation)
                angularVelocity = movingAngularVelocity;
            else
                angularVelocity = 0.0;
        }
    }

    @Override
    public void update(double deltaTime)
    {
        // Rotate flipper
        double newRotation = getRotation() + angularVelocity * deltaTime;
        if (newRotation >= maxRotation)
        {
            setRotation(maxRotation);
            angularVelocity = 0.0;
        }
        else if (newRotation <= minRotation)
        {
            setRotation(minRotation);
            angularVelocity = 0.0;
        }
        else
        {
            setRotation(newRotation);
        }
        //Debug.addDrawVector(getPosition(), new Vector2(0, -1).rotate(Math.toRadians(getRotation())).scale(-angularVelocity).normalized(), Color.BLUE);
    }

    @Override
    public void checkCollision(List<CollisionEventArgs<GameElementT>> eventArgsList, BallPhysicsElement<GameElementT> ballPhysicsElement)
    {
        for (Collider collider : getColliders())
        {
            if (collider.checkCollision(ballPhysicsElement, this))
            {
                eventArgsList.add(new CollisionEventArgs<>(getGameElement(), collider.getId()));
            }
        }
    }

    public double getAngularVelocity()
    {
        return angularVelocity;
    }
}
