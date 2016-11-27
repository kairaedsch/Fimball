package sep.fimball.model.physics.collision;

/**
 * Die EmptyCollision ist für Kollisionen da, die keine Wirkung auf den Ball haben.
 */
public class EmptyCollision implements CollisionType
{
    @Override
    public void applyCollision(CollisionInfo info)
    {

    }
}
