package sep.fimball.model.handler;

import sep.fimball.model.physics.game.CollisionEventType;

/**
 * Fasst alle Argumente eines Aufrufs für ElementHandler zusammen.
 * Die Argumente stellen demnach einen Zusammenstoß zwischen einem Ball und einem Collider da.
 */
public class ElementHandlerArgs
{
    /**
     * Gibt an, wie der Colliders getroffen wurde.
     */
    private CollisionEventType collisionEventType;

    /**
     * Gibt an, wie Tief der Ball eingedrungen ist.
     */
    private double depth;

    /**
     * Die ID des getroffenen Colliders.
     */
    private int colliderId;

    /**
     * Erstellt ein neues ElementHandlerArgs.
     *
     * @param collisionEventType Gibt an, wie der Colliders getroffen wurde.
     * @param depth              Gibt an, wie Tief der Ball eingedrungen ist.
     * @param colliderId         Die ID des getroffenen Colliders.
     */
    public ElementHandlerArgs(CollisionEventType collisionEventType, double depth, int colliderId)
    {
        this.collisionEventType = collisionEventType;
        this.depth = depth;
        this.colliderId = colliderId;
    }

    /**
     * Gibt zurück, wie der Colliders getroffen wurde.
     *
     * @return Gibt an, wie der Colliders getroffen wurde.
     */
    public CollisionEventType getCollisionEventType()
    {
        return collisionEventType;
    }

    /**
     * Gibt zurück, wie Tief der Ball eingedrungen ist.
     *
     * @return Gibt an, wie Tief der Ball eingedrungen ist.
     */
    public double getDepth()
    {
        return depth;
    }

    /**
     * Gibt die ID des getroffenen Colliders zurück.
     *
     * @return Die ID des getroffenen Colliders.
     */
    public int getColliderId()
    {
        return colliderId;
    }
}
