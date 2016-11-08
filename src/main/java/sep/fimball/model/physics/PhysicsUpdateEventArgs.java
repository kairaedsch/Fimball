package sep.fimball.model.physics;

import sep.fimball.general.data.Vector2;

/**
 * Created by theasuro on 08.11.16.
 */
public class PhysicsUpdateEventArgs
{
    private Vector2 position;
    private double rotation;

    public PhysicsUpdateEventArgs(Vector2 position, double rotation)
    {
        this.position = position;
        this.rotation = rotation;
    }

    public Vector2 getPosition()
    {
        return position;
    }

    public double getRotation()
    {
        return rotation;
    }
}
