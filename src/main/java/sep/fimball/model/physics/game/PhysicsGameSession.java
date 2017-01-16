package sep.fimball.model.physics.game;

import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

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
    void ballLost();

    /**
     * Überreicht die verschiedenen Events, die in der Physik passiert sind, an die GameSession weiter.
     *
     * @param collisionEventArgs Liste aller CollisionsEvents.
     * @param elementEventArgs   Liste aller ElementEvents.
     */
    void addEventArgs(ConcurrentLinkedQueue<CollisionEventArgs<GameElementT>> collisionEventArgs, List<ElementEventArgs<GameElementT>> elementEventArgs);
}
