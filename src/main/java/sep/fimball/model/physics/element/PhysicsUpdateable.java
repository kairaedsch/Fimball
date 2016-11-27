package sep.fimball.model.physics.element;

/**
 * Interface, das es PhysicsElements erlaubt, bei jedem Durchlauf der Physics-Loop aufgerufen zu werden.
 */
public interface PhysicsUpdateable
{
    /**
     * Wird beim durchlaufen der Physics-Loop aufgerufen.
     * @param deltaTime Zeit, die seit dem letzten Aufruf der Loop vergangen ist.
     */
    void update(double deltaTime);
}
