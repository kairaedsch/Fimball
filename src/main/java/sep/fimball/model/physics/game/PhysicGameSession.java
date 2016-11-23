package sep.fimball.model.physics.game;

import java.util.List;

public interface PhysicGameSession<GameElementT>
{
    void setBallLost(boolean b);

    void addEventArgs(List<CollisionEventArgs<GameElementT>> collisionEventArgs, List<ElementEventArgs<GameElementT>> elementEventArgs);
}
