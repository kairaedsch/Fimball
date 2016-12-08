package sep.fimball.model.handler.light;

import sep.fimball.general.data.Vector2;

public abstract class LightChanger
{
    public abstract boolean determineStatus(Vector2 position, long time);

    public abstract long getDuration();
}
