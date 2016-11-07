package sep.fimball.model;

/**
 * Argumente, die an das "Ziel-GameElement" bei einer Kollision mit einem anderen GameElement übergeben werden.
 */
public class CollisionEventArgs
{
    /**
     * Das GameElement, das mit dem Ziel-GameElement zusammengestoßen ist.
     */
    private GameElement otherElement;

    /**
     * Der Collider des Ziel-GameElements, der mit dem anderen GameElement zusammengestoßen ist.
     */
    private Collider ownCollider;

    /**
     * Der Collider des anderen GameElements, der mit dem Ziel-GameElement zusammengestoßen ist.
     */
    private Collider otherCollider;

    /**
	 * Erstellt eine neue Instanz von Kollisionsargumenten
	 * @param otherElement
	 */
	public CollisionEventArgs(GameElement otherElement, Collider ownCollider, Collider otherCollider)
    {
        this.otherElement = otherElement;
        this.ownCollider = ownCollider;
        this.otherCollider = otherCollider;
    }

    public GameElement getOtherElement()
    {
        return otherElement;
    }

    public Collider getOwnCollider()
    {
        return ownCollider;
    }

    public Collider getOtherCollider()
    {
        return otherCollider;
    }
}