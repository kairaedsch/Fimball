package sep.fimball.model.physics;

import sep.fimball.model.element.GameElement;

/**
 * Argumente, die an das "Ziel-GameElement" (das GameElement welche diese CollisionEventArgs beim OnCollision Event übergeben bekommt) bei einer Kollision mit einem anderen GameElement übergeben werden.
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

    /**
     * Gibt das GameElement, das mit dem Ziel-GameElement zusammengestoßen ist, zurück.
     * @return Das GameElement, das mit dem Ziel-GameElement zusammengestoßen ist.
     */
    public GameElement getOtherElement()
    {
        return otherElement;
    }

    /**
     * Gibt den Collider des Ziel-GameElements, der mit dem anderen GameElement zusammengestoßen ist, zurück.
     * @return Der Collider des Ziel-GameElements, der mit dem anderen GameElement zusammengestoßen ist.
     */
    public Collider getOwnCollider()
    {
        return ownCollider;
    }

    /**
     * Gibt den Collider des anderen GameElements, der mit dem Ziel-GameElement zusammengestoßen ist, zurück.
     * @return Der Collider des anderen GameElements, der mit dem Ziel-GameElement zusammengestoßen ist.
     */
    public Collider getOtherCollider()
    {
        return otherCollider;
    }
}