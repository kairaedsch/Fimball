package sep.fimball.model.physics.game;

/**
 * Argumente, die an das "Ziel-GameElement" (das GameElement welche diese CollisionEventArg beim OnCollision Event übergeben bekommt) bei einer Kollision mit einem anderen GameElement übergeben werden.
 *
 * @param <GameElementT> Die Klasse des GameElements, mit dem das Ziel-GameElement zusammengestoßen ist.
 */
public class CollisionEventArgs<GameElementT>
{
    /**
     * Das GameElement, das mit dem Ziel-GameElement zusammengestoßen ist.
     */
    private GameElementT otherElement;

    /**
     * Der Collider des Ziel-GameElements, der mit dem anderen GameElement zusammengestoßen ist.
     */
    private int colliderId;

    /**
     * Gibt an, wie collided wurde.
     */
    private CollisionEventType collisionEventType;

    /**
     * Gibt an, wie tief der Ball eingedrungen ist.
     */
    private double depth;

    /**
     * Erstellt eine neue Instanz von CollisionEventArgs.
     *
     * @param otherElement       Mit welchem GameElement das Ziel-GameElement zusammengestoßen ist.
     * @param colliderId         Die ID des Colliders von otherElement, an dem die Kollision passiert ist.
     * @param collisionEventType Gibt an, wie collided wurde.
     * @param depth              Gibt an, wie tief der Ball eingedrungen ist.
     */
    public CollisionEventArgs(GameElementT otherElement, int colliderId, CollisionEventType collisionEventType, double depth)
    {
        this.otherElement = otherElement;
        this.colliderId = colliderId;
        this.collisionEventType = collisionEventType;
        this.depth = depth;
        System.out.println(collisionEventType);
    }

    /**
     * Gibt das GameElement, das mit dem Ziel-GameElement zusammengestoßen ist, zurück.
     *
     * @return Das GameElement, das mit dem Ziel-GameElement zusammengestoßen ist.
     */
    public GameElementT getOtherElement()
    {
        return otherElement;
    }

    /**
     * Die ID des Colliders von otherElement, an dem die Kollision passiert ist.
     *
     * @return Die ID des Colliders von otherElement, an dem die Kollision passiert ist.
     */
    public int getColliderId()
    {
        return colliderId;
    }

    /**
     * Gibt zurück, wie collided wurde.
     *
     * @return Wie collided wurde
     */
    public CollisionEventType getCollisionEventType()
    {
        return collisionEventType;
    }


    /**
     * Gibt zurück, wie tief der Ball eingedrungen ist.
     *
     * @return Wie tief der Ball eingedrungen ist.
     */
    public double getDepth()
    {
        return depth;
    }
}