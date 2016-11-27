package sep.fimball.model.physics.element;

import sep.fimball.general.data.Vector2;

/**
 * Created by TheAsuro on 27.11.2016.
 */
public class FlipperPhysicsElement<GameElementT> implements PhysicsUpdateable
{
    private final double minRotation = -15.0;
    private final double maxRotation = 15.0;
    private final double movingAngularVelocity = 5.0;

    private double angularVelocity = 0.0;

    /**
     * Darstellung des Balls als PhysicsElement.
     */
    private PhysicsElement<GameElementT> subElement;

    public FlipperPhysicsElement(GameElementT gameElement, Vector2 position, double rotation, BasePhysicsElement basePhysicsElement)
    {
        subElement = new PhysicsElement<>(gameElement, position, rotation, basePhysicsElement);
    }

    public void rotateUp()
    {
        if (!isAtTop()) angularVelocity = movingAngularVelocity;
    }

    public void rotateDown()
    {
        if (!isAtBottom()) angularVelocity = -movingAngularVelocity;
    }

    @Override
    public void update(double deltaTime)
    {
        // Rotate flipper if it is moving
        subElement.setRotation(subElement.getRotation() + angularVelocity * deltaTime);

        if (subElement.getRotation() != 0.0)
            System.out.println("FLIPPER ROTATION: " + subElement.getRotation());
        if (angularVelocity != 0.0)
            System.out.println("ANGVEL: " + angularVelocity);

        // Clamp flipper rotation to min/max
        if (isAtTop())
        {
            subElement.setRotation(Math.max(subElement.getRotation(), minRotation));
            angularVelocity = 0.0;
        }
        if (isAtBottom())
        {
            subElement.setRotation(Math.min(subElement.getRotation(), maxRotation));
            angularVelocity = 0.0;
        }
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
