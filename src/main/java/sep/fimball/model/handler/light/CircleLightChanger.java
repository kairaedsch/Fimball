package sep.fimball.model.handler.light;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import sep.fimball.general.data.Vector2;

public class CircleLightChanger extends LightChanger
{
    private final ReadOnlyObjectProperty<Vector2> center;

    public CircleLightChanger(boolean direction, ReadOnlyObjectProperty<Vector2> center)
    {
        super(direction);
        this.center = center;
    }

    @Override
    public boolean determineStatusIntern(Vector2 position, long delta)
    {
        // units per second
        double speed = 15;
        double width = 4;
        double space = 8;

        double radius = (delta / 1000.0) * speed;

        return Math.abs(radius - position.minus(center.get()).magnitude()) % (space + width) <= width;
    }
}
