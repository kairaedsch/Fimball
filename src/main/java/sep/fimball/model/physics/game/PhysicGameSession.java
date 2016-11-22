package sep.fimball.model.physics.game;

import java.util.List;

public interface PhysicGameSession<GameElementT>
{
    void setBallLost(boolean b);

    void addEventArgses(List<CollisionEventArgs<GameElementT>> collisionEventArgses, List<ElementEventArgs<GameElementT>> elementEventArgses);
}
