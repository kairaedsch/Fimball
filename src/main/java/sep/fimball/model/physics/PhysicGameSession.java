package sep.fimball.model.physics;

import java.util.List;

public interface PhysicGameSession
{
    void setBallLost(boolean b);

    void addEventArgses(List<CollisionEventArgs> collisionEventArgses, List<ElementEventArgs> elementEventArgses);
}
