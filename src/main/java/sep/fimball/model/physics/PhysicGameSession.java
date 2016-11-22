package sep.fimball.model.physics;

import java.util.List;

public interface PhysicGameSession
{
    void setBallLost(boolean b);

    void addCollisionEventArgs(List<CollisionEventArgs> collisionEventArgses);
}
