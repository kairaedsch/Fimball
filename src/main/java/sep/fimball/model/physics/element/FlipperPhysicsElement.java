package sep.fimball.model.physics.element;

import sep.fimball.general.data.Vector2;
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
        angularVelocity = movingAngularVelocity;
    }

    public void rotateDown()
    {
        angularVelocity = -movingAngularVelocity;
    }

    @Override
    public void update(double deltaTime)
    {
        // Rotate flipper
        double newRotation = getRotation() + angularVelocity * deltaTime;
        setRotation(Math.min(Math.max(newRotation, minRotation), maxRotation));
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
