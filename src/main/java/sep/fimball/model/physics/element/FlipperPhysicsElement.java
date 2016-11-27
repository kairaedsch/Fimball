package sep.fimball.model.physics.element;

import sep.fimball.general.data.Vector2;

/**
 * TODO - flipper halt
 */
public class FlipperPhysicsElement<GameElementT> implements PhysicsUpdateable
{
    private final double movingAngularVelocity = 500.0;
    private final double minRotation = -15.0;
    private final double maxRotation = 15.0;

    private double angularVelocity = 0.0;

    /**
     * Darstellung des Balls als PhysicsElement.
     */
    private PhysicsElement<GameElementT> subElement;

    public FlipperPhysicsElement(GameElementT gameElement, Vector2 position, BasePhysicsElement basePhysicsElement)
    {
        subElement = new PhysicsElement<>(gameElement, position, maxRotation, basePhysicsElement);
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
        // Rotate flipper if it is moving
        double newRotation = subElement.getRotation() + angularVelocity * deltaTime;

        // Clamp flipper rotation to min/max
        subElement.setRotation(Math.min(Math.max(newRotation, minRotation), maxRotation));
    }

    private boolean isAtTop()
    {
        return subElement.getRotation() >= maxRotation;
    }

    private boolean isAtBottom()
    {
        return subElement.getRotation() <= minRotation;
    }

    /**
     * Gibt das PhysicsElement, welches die Position und physikalische Eigenschaften des Balls hat, zurück.
     *
     * @return Das PhysicsElement, welches die Position und physikalische Eigenschaften des Balls hat, zurück.
     */
    public PhysicsElement<GameElementT> getSubElement()
    {
        return subElement;
    }
}
