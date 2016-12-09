package sep.fimball.model.handler.light;

import sep.fimball.general.data.Vector2;

public class LineLightChanger extends LightChanger
{
    private boolean vertical;

    public LineLightChanger(boolean direction, boolean vertical)
    {
        super(direction);
        this.vertical = vertical;
    }

    @Override
    public boolean determineStatusIntern(Vector2 position, long delta)
    {
        // units per second
        double speed = 15;
        double width = 6;
        double space = 8;

        double radius = (delta / 1000.0) * speed;

        return Math.abs(radius - (vertical ? position.getX() : position.getY())) % (space + width) <= width;
    }
}
