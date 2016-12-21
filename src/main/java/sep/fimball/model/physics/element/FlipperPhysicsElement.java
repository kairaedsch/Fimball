package sep.fimball.model.physics.element;

import sep.fimball.general.data.Vector2;
import sep.fimball.model.physics.PhysicsHandler;

import static sep.fimball.general.data.PhysicsConfig.*;
import static sep.fimball.model.physics.element.AngularDirection.*;

/**
 * Das FlipperPhysicsElement stellt einen Flipperarm in der Physic da.
 *
 * @param <GameElementT> Die Klasse des korrespondierenden GameElements.
 */
public class FlipperPhysicsElement<GameElementT> extends PhysicsElementModifyAble<GameElementT, FlipperModify> implements PhysicsUpdatable
{
    /**
     * Die aktuelle Winkelgeschwindigkeit des Flipperarms.
     */
    private AngularDirection angularDirection = NONE;

    /**
     * Gibt an, ob dieser Flipperarm der rechte oder linke ist.
     */
    private boolean isLeft;

    /**
     * Die Maximale Rotation des Flipperarmes.
     */
    private double minRotation;

    /**
     * Die Minimale Rotation des Flipperarmes.
     */
    private double maxRotation;

    /**
     * Erstellt ein neues FlipperPhysicsElement.
     *
     * @param physicsHandler     Der PhysicsHandler des PhysicsElements.
     * @param gameElement        Das zugehörige GameElement, welches von diesem FlipperPhysicsElement beeinflusst werden soll.
     * @param position           Die Position des PhysicsElements.
     * @param basePhysicsElement Das korrespondierende BasePhysicsElement.
     * @param isLeft             Gibt an , ob er rechte oder linke Flipperarm gemeint ist.
     */
    public FlipperPhysicsElement(PhysicsHandler<GameElementT> physicsHandler, GameElementT gameElement, Vector2 position, double strengthMultiplier, BasePhysicsElement basePhysicsElement, boolean isLeft)
    {
        super(physicsHandler, gameElement, position, isLeft ? FLIPPER_MIN_ROTATION : -FLIPPER_MIN_ROTATION, strengthMultiplier, basePhysicsElement);
        this.isLeft = isLeft;

        if (isLeft)
        {
            minRotation = FLIPPER_MAX_ROTATION;
            maxRotation = FLIPPER_MIN_ROTATION;
        }
        else
        {
            // Bei dem Rechten Flipperarm werden die Werte vertauscht, damit beide Arme gleich weit nach oben bzw. unten gedreht werden können.
            minRotation = -FLIPPER_MIN_ROTATION;
            maxRotation = -FLIPPER_MAX_ROTATION;
        }
    }

    /**
     * Gibt an, ob sich der Flipperarm nach oben bewegt.
     *
     * @return Ob sich der Flipperarm nach oben bewegt.
     */
    public boolean isRotatingUp()
    {
        return angularDirection == UP;
    }

    /**
     * Gibt an, ob sich der Flipperarm nach unten bewegt.
     *
     * @return Ob sich der Flipperarm nach unten bewegt.
     */
    public boolean isRotatingDown()
    {
        return angularDirection == DOWN;
    }

    /**
     * Lässt den Flipperarm drehen, falls möglich.
     *
     * @param newAngularDirection Die neue Drehrichtung
     */
    private void rotate(AngularDirection newAngularDirection)
    {
        double newAngularVelocity = getAngularVelocity(newAngularDirection);
        if (newAngularVelocity > 0 && getRotation() < maxRotation) angularDirection = newAngularDirection;
        if (newAngularVelocity < 0 && getRotation() > minRotation) angularDirection = newAngularDirection;
    }

    @Override
    public void update(double deltaTime)
    {
        // Rotate flipper
        double newRotation = getRotation() + getAngularVelocity() * deltaTime;

        if (newRotation <= minRotation)
        {
            setRotation(minRotation);
            angularDirection = NONE;
        }
        else if (newRotation >= maxRotation)
        {
            setRotation(maxRotation);
            angularDirection = NONE;
        }
        else
        {
            setRotation(newRotation);
        }
    }

    /**
     * Gibt die aktuelle Winkelgeschwindigkeit zurück.
     *
     * @return die aktuelle Winkelgeschwindigkeit.
     */
    public double getAngularVelocity()
    {
        return getAngularVelocity(angularDirection);
    }

    /**
     * Gibt die Winkelgeschwindigkeit abhängig vom der übergebenen AngularDirection zurück.
     *
     * @param angularDirection Die Bewegungsrichtung des Flippers.
     * @return Die Winkelgeschwindigkeit.
     */
    private double getAngularVelocity(AngularDirection angularDirection)
    {
        if (isLeft && angularDirection == DOWN) return FLIPPER_ANGULAR_VELOCITY;
        if (isLeft && angularDirection == UP) return -FLIPPER_ANGULAR_VELOCITY;
        if (!isLeft && angularDirection == DOWN) return -FLIPPER_ANGULAR_VELOCITY;
        if (!isLeft && angularDirection == UP) return FLIPPER_ANGULAR_VELOCITY;
        return 0;
    }

    @Override
    public void applyModify(FlipperModify modify)
    {
        rotate(modify.newAngulatDirection());
    }
}
