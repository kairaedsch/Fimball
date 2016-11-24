package sep.fimball.model.physics.game;

import java.util.List;

/**
 * Die PhysicGameSession stellt die GameSession aus der Sichtweise der Physic da.
 *
 * @param <GameElementT> TODO
 */
public interface PhysicGameSession<GameElementT>
{
    /**
     * Setzt den aktuellen Ball Zustand.
     *
     * @param isBallLost Gibt an, ob der Ball verloren ist.
     */
    void setBallLost(boolean isBallLost);

    /**
     * Überreicht die verschiedenen Events, die in der Physik passiert sind, an die GameSession weiter.
     *
     * @param collisionEventArgs Liste aller CollisionsEvents.
     * @param elementEventArgs   Liste aller ElementEvents.
     */
    void addEventArgs(List<CollisionEventArgs<GameElementT>> collisionEventArgs, List<ElementEventArgs<GameElementT>> elementEventArgs);
}
