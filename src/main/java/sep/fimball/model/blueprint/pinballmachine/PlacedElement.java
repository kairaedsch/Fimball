package sep.fimball.model.blueprint.pinballmachine;

import javafx.beans.property.*;
import sep.fimball.general.data.Vector2;
import sep.fimball.model.blueprint.base.BaseElement;

/**
 * Ein PlacedElement stellt ein auf einem Automaten platziertes Element mit den zugehörigen Eigenschaften dar. Wie auch BaseElementJson wird diese Klasse zur Serialisierung genutzt.
 */
public class PlacedElement
{
    /**
     * Verweis auf den Typ des Elements.
     */
    private BaseElement baseElement;

    /**
     * Position des Elementes auf dem Automaten.
     */
    private ObjectProperty<Vector2> position;

    /**
     * Punkte, die das Element vergibt.
     */
    private IntegerProperty points;

    /**
     * Der Multiplikator, der die Stärke der physikalischen Interaktion des Flipperautomat-Elements mit dem Ball verstärkt oder reduziert.
     */
    private DoubleProperty multiplier;

    /**
     * Die Rotation in Grad, um die das PlacedElementJson gedreht ist.
     */
    private DoubleProperty rotation;

    /**
     * Erzeugt ein neues PlacedElement.
     *
     * @param baseElement Das korrespondierende BaseElement.
     * @param position    Die Position des PlacedElement.
     */
    public PlacedElement(BaseElement baseElement, Vector2 position, int points, double multiplier, double rotation)
    {
        this.baseElement = baseElement;
        this.position = new SimpleObjectProperty<>(position);
        this.points = new SimpleIntegerProperty(points);
        this.multiplier = new SimpleDoubleProperty(multiplier);
        this.rotation = new SimpleDoubleProperty(rotation);
    }

    public ReadOnlyObjectProperty<Vector2> positionProperty()
    {
        return position;
    }

    public IntegerProperty pointsProperty()
    {
        return points;
    }

    public DoubleProperty multiplierProperty()
    {
        return multiplier;
    }

    public ReadOnlyDoubleProperty rotationProperty()
    {
        return rotation;
    }

    public BaseElement getBaseElement()
    {
        return baseElement;
    }

    public void setPosition(Vector2 position)
    {
        this.position.set(position);
    }

    public void setPoints(int points)
    {
        this.points.set(points);
    }

    public void rotateClockwise()
    {
        if (baseElement.getMedia().canRotate())
            rotation.setValue(rotation.get() + baseElement.getMedia().getRotationAccuracy());
    }

    public void rotateCounterclockwise()
    {
        if (baseElement.getMedia().canRotate())
            rotation.setValue(360 + rotation.get() - baseElement.getMedia().getRotationAccuracy());
    }

    @Override
    public boolean equals(Object other)
    {
        if (other != null && other instanceof PlacedElement)
        {
            PlacedElement p = (PlacedElement) other;
            return this.baseElement == p.getBaseElement() && this.position.equals(p.positionProperty()) && this.rotation.equals(p.rotationProperty()) && this.multiplier.equals(p.multiplierProperty()) && this.points.equals(p.pointsProperty());
        }
        else
        {
            return false;
        }
    }


}
