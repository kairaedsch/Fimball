package sep.fimball.model.physics;

import sep.fimball.model.element.GameElement;

/**
 * Argumente, die an das "Ziel-GameElement" (das GameElement welche diese CollisionEventArg beim OnCollision Event übergeben bekommt) bei einer Kollision mit einem anderen GameElement übergeben werden.
 */
public class CollisionEventArg
{
    /**
     * Das GameElement, das mit dem Ziel-GameElement zusammengestoßen ist.
     */
    private GameElement otherElement;

    /**
     * Der Collider des Ziel-GameElements, der mit dem anderen GameElement zusammengestoßen ist.
     */
    private int colliderId;

    /**
	 * Erstellt eine neue Instanz von Kollisionsargumenten
	 * @param otherElement
	 */
	public CollisionEventArg(GameElement otherElement, int colliderId)
    {
        this.otherElement = otherElement;
        this.colliderId = colliderId;
    }

    /**
     * Gibt das GameElement, das mit dem Ziel-GameElement zusammengestoßen ist, zurück.
     * @return Das GameElement, das mit dem Ziel-GameElement zusammengestoßen ist.
     */
    public GameElement getOtherElement()
    {
        return otherElement;
    }

    public int getColliderId()
    {
        return colliderId;
    }
}