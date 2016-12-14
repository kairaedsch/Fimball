package sep.fimball.model.physics.element;

import sep.fimball.general.data.Vector2;

import static sep.fimball.general.data.PhysicsConfig.FLIPPER_ANGULAR_VELOCITY;
import static sep.fimball.general.data.PhysicsConfig.FLIPPER_MAX_ROTATION;
import static sep.fimball.general.data.PhysicsConfig.FLIPPER_MIN_ROTATION;

/**
 * Das FlipperPhysicsElement stellt einen Flipperarm in der Physic da.
 */
public class FlipperPhysicsElement<GameElementT> extends PhysicsElement<GameElementT> implements PhysicsUpdateAble, PhysicsModifyAble<FlipperModify>
{
    /**
     * Die aktuelle Winkelgeschwindigkeit des Flipperarms.
     */
    private double angularVelocity = 0.0;

    /**
     * Gibt an, ob dieser Flipperarm der rechte oder linke ist.
     */
    private boolean isLeft;

    /**
     * Erstellt ein neues FlipperPhysicsElement.
     *
     * @param gameElement        Das zugehörige GameElement, welches von diesem FlipperPhysicsElement beeinflusst werden soll.
     * @param position           Die Position des PhysicsElements.
     * @param basePhysicsElement Das korrespondierende BasePhysicsElement.
     * @param isLeft             Gibt an , ob er rechte oder linke Flipperarm gemeint ist.
     */
    public FlipperPhysicsElement(GameElementT gameElement, Vector2 position, BasePhysicsElement basePhysicsElement, boolean isLeft)
    {
        super(gameElement, position, isLeft ? FLIPPER_MAX_ROTATION : FLIPPER_MIN_ROTATION, basePhysicsElement);
        this.isLeft = isLeft;
    }

    /**
     * Gibt an, ob sich der Flipperarm nach oben bewegt.
     *
     * @return Ob sich der Flipperarm nach oben bewegt.
     */
    public boolean rotatingUp()
    {
        if (isLeft)
            return angularVelocity > 0;
        else
            return angularVelocity < 0;
    }

    /**
     * Gibt an, ob sich der Flipperarm nach unten bewegt.
     *
     * @return Ob sich der Flipperarm nach unten bewegt.
     */
    public boolean rotatingDown()
    {
        if (isLeft)
            return angularVelocity < 0;
        else
            return angularVelocity > 0;
    }

    /**
     * Lässt den Flipperarm nach oben drehen.
     */
    public void rotateUp()
    {
        if (isLeft)
        {
            if (getRotation() < FLIPPER_MAX_ROTATION)
                angularVelocity = FLIPPER_ANGULAR_VELOCITY;
            else
                angularVelocity = 0.0;
        }
        else
        {
            if (getRotation() > FLIPPER_MIN_ROTATION)
                angularVelocity = -FLIPPER_ANGULAR_VELOCITY;
            else
                angularVelocity = 0.0;
        }
    }

    /**
     * Lässt den Flipperarm nach unten drehen.
     */
    public void rotateDown()
    {
        if (isLeft)
        {
            if (getRotation() > FLIPPER_MIN_ROTATION)
                angularVelocity = -FLIPPER_ANGULAR_VELOCITY;
            else
                angularVelocity = 0.0;
        }
        else
        {
            if (getRotation() < FLIPPER_MAX_ROTATION)
                angularVelocity = FLIPPER_ANGULAR_VELOCITY;
            else
                angularVelocity = 0.0;
        }
    }

    @Override
    public void update(double deltaTime)
    {
        // Rotate flipper
        double newRotation = getRotation() + angularVelocity * deltaTime;
        if (newRotation >= FLIPPER_MAX_ROTATION)
        {
            setRotation(FLIPPER_MAX_ROTATION);
            angularVelocity = 0.0;
        }
        else if (newRotation <= FLIPPER_MIN_ROTATION)
        {
            setRotation(FLIPPER_MIN_ROTATION);
            angularVelocity = 0.0;
        }
        else
        {
            setRotation(newRotation);
        }
        //Debug.addDrawVector(getPosition(), new Vector2(0, -1).rotate(Math.toRadians(getRotation())).scale(-angularVelocity).normalized(), Color.BLUE);
    }

    /**
     * Gibt die aktuelle Winkelheschwindigkeit zurück.
     *
     * @return die aktuelle Winkelheschwindigkeit zurück.
     */
    public double getAngularVelocity()
    {
        return angularVelocity;
    }

    @Override
    public void applyModify(FlipperModify modify)
    {
        if(modify.isUp())
            rotateUp();
        else
            rotateDown();
    }
}
