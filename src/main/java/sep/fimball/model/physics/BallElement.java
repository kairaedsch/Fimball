package sep.fimball.model.physics;

import sep.fimball.general.data.Vector2;
import sep.fimball.model.element.GameElement;

/**
 * Stellt einen Ball, der in der Berechnung der Physik genutzt wird, dar.
 */
public class BallElement
{
    /**
     * Darstellung des Balls als PhysicsElement.
     */
    private PhysicsElement subElement;

    /**
     * Die Geschwindigkeit des Balls.
     */
    private Vector2 velocity;

    /**
     *  Die Geschwindigkeit der Rotation des Balls um die Achse, die aus dem Spielfeld "herausragt".
     */
    private double angularVelocity;

    /**
     * Die Form des Colliders des Balls.
     */
    private CircleColliderShape collider;

    /**
     * Die Ebene, auf der sich der Ball befindet.
     */
    private WorldLayer layer;

    /**
     * Erzeugt einen neuen Ball.
     * @param gameElement Das GameElement, dessen Eigenschaften kopiert werden.
     * @param collider Die Form des Colliders, die der Ball haben soll.
     * @param layer Die Ebene, auf der sich der Ball befinden soll.
     */
    public BallElement(GameElement gameElement, CircleColliderShape collider, WorldLayer layer)
    {
        subElement = new PhysicsElement(gameElement);

        this.collider = collider;
        this.velocity = new Vector2();
        this.angularVelocity = 0.0;
        this.layer = layer;
    }

    /**
     * TODO
     * @return
     */
    public PhysicsElement getSubElement()
    {
        return subElement;
    }

    /**
     * Gibt die Position des Balls zurück.
     * @return Die Position des Balls.
     */
    public Vector2 getPosition()
    {
        return subElement.getPosition();
    }

    /**
     * Setzt die Position des Balls auf den gegebenen Wert.
     * @param position Die neue Position des Balls.
     */
    public void setPosition(Vector2 position)
    {
        subElement.setPosition(position);
    }

    /**
     * TODO
     * @param rotation
     */
    public void setRotation(double rotation)
    {
        subElement.setRotation(rotation);
    }

    /**
     * Gibt die Geschwindigkeit ders des Balls zurück.
     * @return Die Geschwindigkeit des Balls.
     */
    public Vector2 getVelocity()
    {
        return velocity;
    }

    /**
     * Setzt die Geschwindigkeit des Balls auf den übergebenen Wert.
     * @param velocity Die neue Geschwindigkeit des Balls.
     */
    public void setVelocity(Vector2 velocity)
    {
        this.velocity = velocity;
    }

    /**
     * Gibt die Geschwindigkeit der Rotation des Balls um die Achse, die aus dem Spielfeld "herausragt", zurück.
     * @return Die Geschwindigkeit der Rotation des Balls um die Achse, die aus dem Spielfeld "herausragt".
     */
    public double getAngularVelocity()
    {
        return angularVelocity;
    }

    /**
     * Setzt die Geschwindigkeit der Rotation des Balls um die Achse, die aus dem Spielfeld "herausragt" auf den übergebenen Wert.
     * @param angularVelocity Die neue Geschwindigkeit der Rotation des Balls um die Achse, die aus dem Spielfeld "herausragt".
     */
    public void setAngularVelocity(double angularVelocity)
    {
        this.angularVelocity = angularVelocity;
    }

    /**
     * Gibt die Form des Colliders des Balls zurück.
     * @return Die Form des Colliders des Balls.
     */
    public CircleColliderShape getCollider()
    {
        return collider;
    }

    /**
     * Gibt die Ebene, auf der sich der Ball befindet, zurück.
     * @return Die Ebene, auf der sich der Ball befindet.
     */
    public WorldLayer getLayer()
    {
        return layer;
    }

    /**
     * Setzt die Ebene, auf der sich der Ball befindet, auf den übergebenen Wert.
     * @param layer Die neue Ebene, auf der sich der Ball befindem soll.
     */
    public void setLayer(WorldLayer layer)
    {
        this.layer = layer;
    }
}
