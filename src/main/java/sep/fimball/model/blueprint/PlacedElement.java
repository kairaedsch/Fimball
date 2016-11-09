package sep.fimball.model.blueprint;

import javafx.beans.property.*;

import java.awt.*;

/**
 * Ein PlacedElement stellt ein auf einem Automaten platziertes Element mit den zugehörigen Eigenschaften dar.
 */
public class PlacedElement
{
    /**
     * ID zur Identifizierung des Elements.
     */
    IntegerProperty blueprintElementId;

    /**
     * Verweis auf den Typ des Elements.
     */
    StringProperty greenprintElementId;

    /**
     * Position des Elementes auf dem Automaten.
     */
    ObjectProperty<Point> position;

    /**
     * Punkte, die das Element vergibt.
     */
    IntegerProperty points;

    DoubleProperty multiplier;

    /**
     * Legt ein neues Element  mit gegebener ID, Typ und Position an.
     * @param blueprintElementId
     * @param greenprintElementId
     * @param position
     */
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
