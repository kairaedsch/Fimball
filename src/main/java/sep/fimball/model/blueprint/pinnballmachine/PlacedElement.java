package sep.fimball.model.blueprint.pinnballmachine;

import javafx.beans.property.*;

import java.awt.*;

/**
 * Ein PlacedElement stellt ein auf einem Automaten platziertes Element mit den zugehörigen Eigenschaften dar. Wie auch ElementTypeJson wird diese Klasse zur Serialisierung genutzt.
 */
public class PlacedElement
{
    /**
     * Verweis auf den Typ des Elements.
     */
    StringProperty elementTypeId;

    /**
     * Position des Elementes auf dem Automaten.
     */
    ObjectProperty<Point> position;

    /**
     * Punkte, die das Element vergibt.
     */
    IntegerProperty points;

    /**
     * TODO
     */
    DoubleProperty multiplier;

    /**
     * Legt ein neues Element  mit gegebener ID, Typ und Position an.
     * @param blueprintElementId
     * @param elementTypeId
     * @param position
     */
    public PlacedElement(int blueprintElementId, String elementTypeId, Point position)
    {
        this.elementTypeId = new SimpleStringProperty(elementTypeId);
        this.position = new SimpleObjectProperty<>(position);
        this.points = new SimpleIntegerProperty();
        this.multiplier = new SimpleDoubleProperty();
    }

    public ReadOnlyStringProperty elementTypeIdProperty()
    {
        return elementTypeId;
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
