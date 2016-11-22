package sep.fimball.model.physics;

/**
 * Argumente, die an das "Ziel-GameElement" (das GameElement welche diese CollisionEventArg beim OnCollision Event übergeben bekommt) bei einer Kollision mit einem anderen GameElement übergeben werden.
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
     * Erstellt eine neue Instanz von Kollisionsargumenten
     *
     * @param otherElement Mit welchem GameElement das Ziel-GameElement zusammengestoßen ist.
     * @param colliderId   Die ID des Colliders von otherElement, an dem die Kollision passiert ist.
     */
    public CollisionEventArgs(GameElementT otherElement, int colliderId)
    {
        this.otherElement = otherElement;
        this.colliderId = colliderId;
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
}