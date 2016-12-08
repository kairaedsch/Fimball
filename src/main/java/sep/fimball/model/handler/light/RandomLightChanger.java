package sep.fimball.model.handler.light;

import sep.fimball.general.data.Vector2;

public class RandomLightChanger extends LightChanger
{
    @Override
    public boolean determineStatus(Vector2 position, long delta)
    {
        return Math.random() > 0.5;
    }
}
