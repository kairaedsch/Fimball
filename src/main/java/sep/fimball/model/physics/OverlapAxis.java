package sep.fimball.model.physics;

import sep.fimball.general.data.Vector2;

/**
 * Created by alexcekay on 18.11.16.
 */
public class OverlapAxis
{
    private Vector2 axis;
    private double overlap;

    public OverlapAxis(Vector2 axis, double overlap)
    {
        this.axis = axis;
        this.overlap = overlap;
    }

    public Vector2 getAxis()
    {
        return axis;
    }

    public double getOverlap()
    {
        return overlap;
    }
}
