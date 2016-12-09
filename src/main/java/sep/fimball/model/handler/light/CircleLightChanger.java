package sep.fimball.model.handler.light;

import javafx.beans.property.ReadOnlyObjectProperty;
import sep.fimball.general.data.Vector2;

public class CircleLightChanger extends LightChanger
{
    private final ReadOnlyObjectProperty<Vector2> center;
    private final boolean round;

    public CircleLightChanger(boolean direction, ReadOnlyObjectProperty<Vector2> center, boolean round)
    {
        super(direction);
        this.center = center;
        this.round = round;
    }

    @Override
    public boolean determineStatusIntern(Vector2 position, long delta)
    {
        // units per second
        double speed = 40;
        double space = 16;
        double width;

        double radius = (delta / 1000.0) * speed;
        Vector2 relativePosition = position.minus(center.get());

        double distance;
        if(round)
        {
            width = 8;
            distance = Math.abs(radius - relativePosition.magnitude());
        }
        else
        {
            width = 6;
            double d1 =  Math.abs(radius - relativePosition.getX());
            double d2 =  Math.abs(radius - relativePosition.getY());
            double d3 =  Math.abs(-radius - relativePosition.getX());
            double d4 =  Math.abs(-radius - relativePosition.getY());

            distance = Math.min(Math.min(d1, d2), Math.min(d3, d4));
        }
        return distance % (space + width) <= width;
    }
}
