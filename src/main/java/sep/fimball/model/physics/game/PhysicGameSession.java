package sep.fimball.model.physics.game;

import java.util.List;

/**
 * TODO
 * @param <GameElementT>
 */
public interface PhysicGameSession<GameElementT>
{
    /**
     * TODO
     * @param b
     */
    void setBallLost(boolean b);

    /**
     * TODO
     * @param collisionEventArgs
     * @param elementEventArgs
     */
    void addEventArgs(List<CollisionEventArgs<GameElementT>> collisionEventArgs, List<ElementEventArgs<GameElementT>> elementEventArgs);
}
