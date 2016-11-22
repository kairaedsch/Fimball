package sep.fimball.model.physics.collision;

import sep.fimball.general.data.Vector2;
import sep.fimball.model.physics.element.BallElement;

/**
 * Created by alexcekay on 22.11.16.
 */
public class EmptyCollision implements CollisionType
{
    @Override
    public void applyCollision(BallElement ball, Vector2 shortestIntersect, double rotation)
    {

    }
}
