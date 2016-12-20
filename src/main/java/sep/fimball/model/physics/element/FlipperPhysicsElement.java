package sep.fimball.model.physics.element;

import sep.fimball.general.data.Vector2;
import sep.fimball.model.physics.PhysicsHandler;

import static sep.fimball.general.data.PhysicsConfig.*;

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
    private AngularDirection angularDirection = AngularDirection.NONE;

    /**
     * Gibt an, ob dieser Flipperarm der rechte oder linke ist.
     */
    private boolean isLeft;

    /**
     * Die Maximale Rotation des Flipperarmes.
     */
    private double maxRotation;

    /**
     * Die Minimale Rotation des Flipperarmes.
     */
    private double minRotation;

    /**
     * Erstellt ein neues FlipperPhysicsElement.
     *
     * @param physicsHandler     Der PhysicsHandler des PhysicsElements.
     * @param gameElement        Das zugehörige GameElement, welches von diesem FlipperPhysicsElement beeinflusst werden soll.
     * @param position           Die Position des PhysicsElements.
     * @param basePhysicsElement Das korrespondierende BasePhysicsElement.
     * @param isLeft             Gibt an , ob er rechte oder linke Flipperarm gemeint ist.
     */
    public FlipperPhysicsElement(PhysicsHandler<GameElementT> physicsHandler, GameElementT gameElement, Vector2 position, BasePhysicsElement basePhysicsElement, boolean isLeft)
    {
        super(physicsHandler, gameElement, position, isLeft ? FLIPPER_MAX_ROTATION : FLIPPER_MIN_ROTATION, basePhysicsElement);
        this.isLeft = isLeft;

        if(isLeft)
        {
            maxRotation = FLIPPER_MAX_ROTATION;
            minRotation = FLIPPER_MIN_ROTATION;
        }
        else
        {
            // Bei dem Rechten Flipperarm werden die Werte vertauscht, damit beide Arme gleich weit nach oben gedreht werden können.
            maxRotation = -FLIPPER_MIN_ROTATION;
            minRotation = -FLIPPER_MAX_ROTATION;
        }
    }

    /**
     * Gibt an, ob sich der Flipperarm nach oben bewegt.
     *
     * @return Ob sich der Flipperarm nach oben bewegt.
     */
    public boolean isRotatingUp()
    {
        return angularDirection == AngularDirection.UP;
    }

    /**
     * Gibt an, ob sich der Flipperarm nach unten bewegt.
     *
     * @return Ob sich der Flipperarm nach unten bewegt.
     */
    public boolean isRotatingDown()
    {
        return angularDirection == AngularDirection.DOWN;
    }

    /**
     * Lässt den Flipperarm nach oben drehen.
     */
    private void rotateUp()
    {
        if (isLeft && getRotation() < maxRotation) angularDirection = AngularDirection.UP;
        if (!isLeft && getRotation() > minRotation) angularDirection = AngularDirection.UP;

    }

    /**
     * Lässt den Flipperarm nach unten drehen.
     */
    private void rotateDown()
    {
        if (isLeft && getRotation() > minRotation) angularDirection = AngularDirection.DOWN;
        if (!isLeft && getRotation() < maxRotation) angularDirection = AngularDirection.DOWN;
    }

    @Override
    public void update(double deltaTime)
    {
        // Rotate flipper
        double newRotation = getRotation() + getAngularVelocity() * deltaTime;

        if (newRotation >= maxRotation)
        {
            setRotation(maxRotation);
            angularDirection = AngularDirection.NONE;
        }
        else if (newRotation <= minRotation)
        {
            setRotation(minRotation);
            angularDirection = AngularDirection.NONE;
        }
        else
        {
            setRotation(newRotation);
        }
    }

    /**
     * Gibt die aktuelle Winkelheschwindigkeit zurück.
     *
     * @return die aktuelle Winkelheschwindigkeit zurück.
     */
    public double getAngularVelocity()
    {
        if (isLeft && angularDirection == AngularDirection.UP) return FLIPPER_ANGULAR_VELOCITY;
        if (isLeft && angularDirection == AngularDirection.DOWN) return -FLIPPER_ANGULAR_VELOCITY;
        if (!isLeft && angularDirection == AngularDirection.UP) return -FLIPPER_ANGULAR_VELOCITY;
        if (!isLeft && angularDirection == AngularDirection.DOWN) return FLIPPER_ANGULAR_VELOCITY;
        return 0;
    }

    @Override
    public void applyModify(FlipperModify modify)
    {
        if (modify.isUp())
            rotateUp();
        else
            rotateDown();
    }

    /**
     * Die Drehrichtung eines Flipperarmes.
     */
    public enum AngularDirection
    {
        /**
         * Bewegung nach Hoch.
         */
        UP,

        /**
         * Bewegung nach Runter.
         */
        DOWN,

        /**
         * Keine Bewegung.
         */
        NONE
    }
}
