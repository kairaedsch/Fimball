package sep.fimball.model.physics.collision;

import sep.fimball.model.physics.collider.WorldLayer;

public class RampCollision implements CollisionType
{
    @Override
    public void applyCollision(CollisionInfo info)
    {
        if(info.getBall().getLayer() == WorldLayer.RAMP)
        {
            info.getBall().setHeight(2);
        }
    }
}
