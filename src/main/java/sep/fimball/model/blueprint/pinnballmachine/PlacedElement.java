package sep.fimball.model.blueprint.pinnballmachine;

import javafx.beans.property.*;

import java.awt.*;
import java.lang.annotation.ElementType;

/**
 * Ein PlacedElement stellt ein auf einem Automaten platziertes Element mit den zugehörigen Eigenschaften dar. Wie auch ElementTypeJson wird diese Klasse zur Serialisierung genutzt.
 */
public class PlacedElement
{
    /**
     * Verweis auf den Typ des Elements.
     */
    ElementType elementType;

    /**
     * Position des Elementes auf dem Automaten.
     */
    ObjectProperty<Point> position;

    /**
     * Punkte, die das Element vergibt.
     */
    IntegerProperty points;

    DoubleProperty multiplier;

    public PlacedElement(ElementType elementType, Point position)
    {
        this.elementType = elementType;
        this.position = new SimpleObjectProperty<>(position);
        this.points = new SimpleIntegerProperty();
        this.multiplier = new SimpleDoubleProperty();
    }

    public ReadOnlyObjectProperty<Point> positionProperty()
    {
        return position;
    }

    public ReadOnlyIntegerProperty pointsProperty()
    {
        return points;
    }

    public ReadOnlyDoubleProperty multiplierProperty()
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

    public ElementType getElementType()
    {
        return elementType;
    }
}
