package sep.fimball.model.physics.element;

import javafx.scene.paint.Color;
import sep.fimball.general.data.Vector2;
import sep.fimball.general.debug.Debug;
import sep.fimball.model.physics.collider.Collider;
import sep.fimball.model.physics.game.CollisionEventArgs;

/**
 * TODO - flipper halt
 */
public class FlipperPhysicsElement<GameElementT> extends PhysicsElement<GameElementT> implements PhysicsUpdateable
{
    private static final double movingAngularVelocity = 500.0;
    private static final double minRotation = -15.0;
    private static final double maxRotation = 15.0;

    private double angularVelocity = 0.0;

    public FlipperPhysicsElement(GameElementT gameElement, Vector2 position, BasePhysicsElement basePhysicsElement)
    {
        super(gameElement, position, maxRotation, basePhysicsElement);
    }

    public void rotateUp()
    {
        if (getRotation() < maxRotation)
            angularVelocity = movingAngularVelocity;
        else
            angularVelocity = 0.0;
    }

    public void rotateDown()
    {
        if (getRotation() > minRotation)
            angularVelocity = -movingAngularVelocity;
        else
            angularVelocity = 0.0;
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
        Debug.addDrawVector(getPosition(), new Vector2(0, -1).rotate(Math.toRadians(getRotation())).scale(-angularVelocity).normalized(), Color.BLUE);
    }

    @Override
    public CollisionEventArgs checkCollision(BallPhysicsElement<GameElementT> ballPhysicsElement)
    {
        for (Collider collider : getColliders())
        {
            if (collider.checkCollision(ballPhysicsElement, this))
            {
                // TODO allow for multiple collisions in one object?
                return new CollisionEventArgs<>(getGameElement(), collider.getId());
            }
        }
        return null;
    }

    public double getAngularVelocity()
    {
        return angularVelocity;
    }
}
