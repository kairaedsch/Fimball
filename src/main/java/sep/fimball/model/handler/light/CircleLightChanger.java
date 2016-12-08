package sep.fimball.model.handler.light;

import sep.fimball.general.data.Vector2;

public class CircleLightChanger extends LightChanger
{
    public CircleLightChanger(boolean direction)
    {
        super(direction);
    }

    @Override
    public boolean determineStatusIntern(Vector2 position, long delta)
    {
        // units per second
        double speed = 15;
        double width = 3;
        double space = 5;

        double radius = (delta / 1000.0) * speed;

        return Math.abs(radius - position.magnitude()) % (space + width) <= width;
    }
}
