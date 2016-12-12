package sep.fimball.model.physics.collision;

/**
 * Beschreibt eine Interaktion eines Colliders mit einem Ball.
 */
public interface CollisionType
{
    /**
     * Wendet eine Interaktion einer Fläche, die den Ball berührt, auf diesen Ball an.
     *
     * @param info Die CollisionInfo welche Auskunft über eine eingetretene Kollision gibt.
     */
    void applyCollision(CollisionInfo info);
}
