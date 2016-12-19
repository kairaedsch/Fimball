package sep.fimball.model.physics.element;

/**
 * Interface, welches es PhysicsElements erlaubt, bei jedem Durchlauf der Physics-Loop aufgerufen zu werden.
 */
public interface PhysicsUpdatable
{
    /**
     * Wird beim durchlaufen der Physics-Loop aufgerufen.
     *
     * @param deltaTime Zeit, die seit dem letzten Aufruf der Loop vergangen ist.
     */
    void update(double deltaTime);
}
