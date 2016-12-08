package sep.fimball.model.handler.light;

import sep.fimball.general.data.Vector2;

public abstract class LightChanger
{
    private boolean direction;

    public LightChanger(boolean direction)
    {
        this.direction = direction;
    }

    public final boolean determineStatus(Vector2 position, long delta)
    {
        return determineStatusIntern(position, (direction ? (getDuration() - delta) : delta));
    }

    protected abstract boolean determineStatusIntern(Vector2 position, long delta);

    public long getDuration()
    {
        return 10000;
    }
}
