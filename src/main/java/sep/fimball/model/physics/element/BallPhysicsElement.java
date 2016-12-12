package sep.fimball.model.physics.element;

import sep.fimball.general.data.Vector2;
import sep.fimball.model.physics.PhysicsHandler;
import sep.fimball.model.physics.collider.CircleColliderShape;
import sep.fimball.model.physics.collider.WorldLayer;

/**
 * Stellt einen Ball, der in der Berechnung der Physik genutzt wird, dar.
 *
 * @param <GameElementT> Die Klasse des korrespondierenden GameElements.
 */
public class BallPhysicsElement<GameElementT> extends PhysicsElement<GameElementT> implements PhysicsUpdateable
{
    /**
     * In m/s^2. Gibt an wie stark der Ball auf der y-Achse nach Unten beschleunigt wird. Dabei wurde die Neigung des Tisches schon mit eingerechnet: 9.81 m/s^2 * sin(7°), wobei 9.81 m/s^2 die Schwerkraftkonstante und 7° die angenommene Neigung ist.
     */
    private final double GRAVITY = 1.19554 * 20;

    public static final double GRAVITY_HEIGHT = 5;

    public static final double MAX_HEIGHT = 2;

    public static final double RADIUS = 2;

    /**
     * Die Geschwindigkeit des Balls.
     */
    private Vector2 velocity;

    /**
     * Die Geschwindigkeit der Rotation des Balls um die Achse, die aus dem Spielfeld "herausragt".
     */
    private double angularVelocity;

    /**
     * Die Höhe des Balles.
     */
    private double height;

    /**
     * Erzeugt einen neuen Ball.
     *
     * @param gameElement        Das GameElement, dessen Eigenschaften kopiert werden.
     * @param position           Die Position, auf der sich der Ball befinden soll.
     * @param rotation           Die Rotation, um die der Ball gedreht sein soll.
     * @param basePhysicsElement Das korrespondierende BasePhysicsElement.
     */
    public BallPhysicsElement(GameElementT gameElement, Vector2 position, double rotation, BasePhysicsElement basePhysicsElement)
    {
        super(gameElement, position, rotation, basePhysicsElement);

        this.velocity = new Vector2();
        this.angularVelocity = 0.0;
        this.height = 0;
    }

    @Override
    public void update(double deltaTime)
    {
        // Wende Schwerkraft auf den Ball an
        setVelocity(getVelocity().plus(new Vector2(0.0, GRAVITY * deltaTime)));

        setHeight(Math.max(0, height - GRAVITY_HEIGHT * deltaTime));

        // Bewege den Ball
        setPosition(getPosition().plus(getVelocity().scale(deltaTime)));
    }

    /**
     * Gibt die Geschwindigkeit des Balls zurück.
     *
     * @return Die Geschwindigkeit des Balls.
     */
    public Vector2 getVelocity()
    {
        return velocity;
    }

    /**
     * Setzt die Geschwindigkeit des Balls auf den übergebenen Wert.
     *
     * @param velocity Die neue Geschwindigkeit des Balls.
     */
    public void setVelocity(Vector2 velocity)
    {
        this.velocity = velocity.clamp(1 / PhysicsHandler.TICK_RATE_SEC);
        //System.out.println("speed: " + velocity.magnitude() * (PhysicsHandler.TICK_RATE / 1000D));
    }

    /**
     * Gibt die Geschwindigkeit der Rotation des Balls um die Achse, die aus dem Spielfeld "herausragt", zurück.
     *
     * @return Die Geschwindigkeit der Rotation des Balls um die Achse, die aus dem Spielfeld "herausragt".
     */
    public double getAngularVelocity()
    {
        return angularVelocity;
    }

    /**
     * Setzt die Geschwindigkeit der Rotation des Balls um die Achse, die aus dem Spielfeld "herausragt" auf den übergebenen Wert.
     *
     * @param angularVelocity Die neue Geschwindigkeit der Rotation des Balls um die Achse, die aus dem Spielfeld "herausragt".
     */
    public void setAngularVelocity(double angularVelocity)
    {
        this.angularVelocity = angularVelocity;
    }

    /**
     * Gibt die Form des Colliders des Balls zurück.
     *
     * @return Die Form des Colliders des Balls.
     */
    public CircleColliderShape getCollider()
    {
        return (CircleColliderShape) getColliders().get(0).getShapes().get(0);
    }

    /**
     * Gibt die Ebene, auf der sich der Ball befindet, zurück.
     *
     * @return Die Ebene, auf der sich der Ball befindet.
     */
    public WorldLayer getLayer()
    {
        return height > 1 ? WorldLayer.RAMP : WorldLayer.GROUND;
    }

    public void setHeight(double height)
    {
        this.height = Math.min(2, Math.max(0, height));
    }

    public double getHeight()
    {
        return height;
    }

    public double getScale()
    {
        return 1 + height / 4;
    }
}
