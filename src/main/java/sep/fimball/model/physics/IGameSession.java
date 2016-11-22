package sep.fimball.model.physics;

import java.util.List;

public interface IGameSession
{
    void setBallLost(boolean b);

    void addCollisionEventArgs(List<CollisionEventArgs> collisionEventArgses);
}
