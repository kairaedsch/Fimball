package sep.fimball.model.blueprint;

import javafx.beans.property.*;

import java.awt.*;

/**
 * Created by kaira on 01.11.2016.
 */
public class PlacedElement
{
    IntegerProperty blueprintElementId;
    StringProperty greenprintElementId;
    ObjectProperty<Point> position;
    IntegerProperty points;
    DoubleProperty multiplier;

    public PlacedElement(int blueprintElementId, String greenprintElementId, Point position)
    {
        this.blueprintElementId = new SimpleIntegerProperty(blueprintElementId);
        this.greenprintElementId = new SimpleStringProperty(greenprintElementId);
        this.position = new SimpleObjectProperty<>(position);
        this.points = new SimpleIntegerProperty();
        this.multiplier = new SimpleDoubleProperty();
    }

    public ReadOnlyIntegerProperty blueprintElementIdProperty()
    {
        return blueprintElementId;
    }

    public ReadOnlyStringProperty greenprintElementIdProperty()
    {
        return greenprintElementId;
    }

    public ReadOnlyObjectProperty<Point> positionProperty()
    {
        return position;
    }

    public ReadOnlyIntegerProperty pointsProperty()
    {
        return points;
    }

    public DoubleProperty multiplierProperty()
    {
        return multiplier;
    }

    public void setPosition(Point position)
    {
        this.position.set(position);
    }

    public void setPoints(int points)
    {
        this.points.set(points);
    }

    public void setMultiplier(double multiplier)
    {
        this.multiplier.set(multiplier);
    }
}
