package sep.fimball.model.physics.element;

import sep.fimball.model.physics.game.ElementEventArgs;

/**
 * Interface, welches es PhysicsElements erlaubt, bei jedem Durchlauf der Physics-Loop aufgerufen zu werden.
 */
public interface PhysicsUpdatable<GameElementT>
{
    /**
     * Wird beim durchlaufen der Physics-Loop aufgerufen.
     *
     * @param deltaTime Zeit, die seit dem letzten Aufruf der Loop vergangen ist.
     */
    void update(double deltaTime);

    /**
     * Gibt zurück ob sich Position oder Rotation des physikalischen Elements geändert haben.
     *
     * @return Ob sich Position oder Rotation des physikalischen Elements geändert haben.
     */
    boolean hasChanged();

    /**
     * Setzt das Feld welches angibt ob sich Position oder Rotation des physikalischen Elements geändert haben auf {@code false} zurück.
     */
    void resetChanged();

    /**
     * Gibt den aktuellen Zustand des PhysicsElements zurück.
     * @return
     */
    ElementEventArgs<GameElementT> getChange();
}
