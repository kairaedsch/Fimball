package sep.fimball.model.blueprint.pinballmachine;

import javafx.beans.property.*;
import sep.fimball.general.data.Vector2;
import sep.fimball.model.blueprint.elementtype.ElementType;

/**
 * Ein PlacedElement stellt ein auf einem Automaten platziertes Element mit den zugehörigen Eigenschaften dar. Wie auch ElementTypeJson wird diese Klasse zur Serialisierung genutzt.
 */
public class PlacedElement
{
    /**
     * Verweis auf den Typ des Elements.
     */
    private ElementType elementType;

    /**
     * Position des Elementes auf dem Automaten.
     */
    private ObjectProperty<Vector2> position;

    /**
     * Punkte, die das Element vergibt.
     */
    private IntegerProperty points;

    private DoubleProperty multiplier;

    public PlacedElement(ElementType elementType, Vector2 position)
    {
        this.elementType = elementType;
        this.position = new SimpleObjectProperty<>(position);
        this.points = new SimpleIntegerProperty();
        this.multiplier = new SimpleDoubleProperty();
    }

    public ReadOnlyObjectProperty<Vector2> positionProperty()
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

    public void setPosition(Vector2 position)
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
