package sep.fimball.model.handler.light;

import sep.fimball.general.data.Vector2;

public class CircleLightChanger extends LightChanger
{
    @Override
    public boolean determineStatus(Vector2 position, long delta)
    {
        double radius = (delta * 1.0) / getDuration();

        return Math.abs(radius * 50 - position.magnitude()) <= 1.5;
    }

    @Override
    public long getDuration()
    {
        return 3000;
    }
}
