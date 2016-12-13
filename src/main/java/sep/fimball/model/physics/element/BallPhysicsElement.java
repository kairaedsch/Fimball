package sep.fimball.model.physics.element;

import sep.fimball.general.data.PhysicsConfig;
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
     * Die Geschwindigkeit des Balls.
     */
    private Vector2 velocity;

    /**
     * Die Höhe des Balls.
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
        this.height = 0;
    }

    @Override
    public void update(double deltaTime)
    {
        // Wende Schwerkraft auf den Ball an
        setVelocity(getVelocity().plus(new Vector2(0.0, PhysicsConfig.GRAVITY * deltaTime)));
        setHeight(height - PhysicsConfig.GRAVITY_HEIGHT * deltaTime);

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
        return height > (PhysicsConfig.MAX_BALL_HEIGHT / 2.0) ? WorldLayer.RAMP : WorldLayer.GROUND;
    }

    /**
     * Setzt die Höhe des Balles. Diese muss zwischen PhysicsConfig.MAX_BALL_HEIGHT und 0 liegen und wird bei Bedarf angepasst.
     *
     * @param height Die neue Höhe des Balles.
     */
    public void setHeight(double height)
    {
        this.height = Math.min(PhysicsConfig.MAX_BALL_HEIGHT, Math.max(0, height));
    }

    /**
     * Gibt die aktuelle Höhe des Balles zurück.
     *
     * @return Die aktuelle Höhe des Balles.
     */
    public double getHeight()
    {
        return height;
    }
}
