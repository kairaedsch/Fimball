package sep.fimball.model.physics.game;

import java.util.List;

/**
 * Die PhysicsGameSession stellt die GameSession aus der Sichtweise der Physic da.
 *
 * @param <GameElementT> Generisches GameElement.
 */
public interface PhysicsGameSession<GameElementT>
{
    /**
     * Lässt den Ball als verloren gelten.
     */
    void setBallLost();

    /**
     * Überreicht die verschiedenen Events, die in der Physik passiert sind, an die GameSession weiter.
     *
     * @param collisionEventArgs Liste aller CollisionsEvents.
     * @param elementEventArgs   Liste aller ElementEvents.
     */
    void addEventArgs(List<CollisionEventArgs<GameElementT>> collisionEventArgs, List<ElementEventArgs<GameElementT>> elementEventArgs);
}
