package sep.fimball.model.handler.light;

import sep.fimball.general.data.Vector2;

public class CircleLightChanger extends LightChanger
{
    @Override
    public boolean determineStatus(Vector2 position, long delta)
    {
        return Math.random() > 0.5;
    }

    @Override
    public long getDuration()
    {
        return 10000;
    }
}
