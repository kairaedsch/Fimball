package sep.fimball.model.blueprint.pinballmachine;

import javafx.beans.property.*;
import sep.fimball.general.data.Vector2;
import sep.fimball.model.blueprint.base.BaseElement;

/**
 * Ein PlacedElement stellt ein auf einem Automaten platziertes Element mit den zugehörigen Eigenschaften dar.
 */
public class PlacedElement
{
    /**
     * Verweis auf die Vorlage des Elements.
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
     * Die Rotation in Grad, um die das PlacedElement gedreht ist.
     */
    private DoubleProperty rotation;

    /**
     * Erzeugt ein neues PlacedElement.
     *
     * @param baseElement Das korrespondierende BaseElement.
     * @param position    Die Position des PlacedElement.
     * @param points      Die Punkte, die das Element vergibt.
     * @param multiplier  Der Multiplikator, der die Stärke der physikalischen Interaktion des Flipperautomat-Elements mit dem Ball verstärkt oder reduziert.
     * @param rotation    Die Rotation in Grad, um die das PlacedElement gedreht sein soll.
     */
    public PlacedElement(BaseElement baseElement, Vector2 position, int points, double multiplier, double rotation)
    {
        this.baseElement = baseElement;
        this.position = new SimpleObjectProperty<>(position);
        this.points = new SimpleIntegerProperty(points);
        this.multiplier = new SimpleDoubleProperty(multiplier);
        this.rotation = new SimpleDoubleProperty(rotation);
    }

    /**
     * Gibt die Position des Elementes auf dem Automaten zurück.
     * @return Die Position des Elementes auf dem Automaten.
     */
    public ReadOnlyObjectProperty<Vector2> positionProperty()
    {
        return position;
    }

    /**
     * Gibt die Punkte, die das Element vergibt, zurück.
     * @return Die Punkte, die das Element vergibt.
     */
    public IntegerProperty pointsProperty()
    {
        return points;
    }

    /**
     * Gibt den Multiplikator, der die Stärke der physikalischen Interaktion des Flipperautomat-Elements mit dem Ball verstärkt oder reduziert, zurück.
     * @return Der Multiplikator
     */
    public DoubleProperty multiplierProperty()
    {
        return multiplier;
    }

    /**
     * Gibt die Rotation in Grad, um die das PlacedElementJson gedreht ist, zurück.
     * @return Die Rotation in Grad, um die das PlacedElementJson gedreht ist.
     */
    public ReadOnlyDoubleProperty rotationProperty()
    {
        return rotation;
    }

    /**
     * Gibt den Verweis auf die Vorlage des Elements zurück.
     * @return Der Verweis auf die Vorlage des Elements.
     */
    public BaseElement getBaseElement()
    {
        return baseElement;
    }

    /**
     * Setzt die Position des Elementes auf dem Automaten.
     * @param position Die neue Position des Elementes auf dem Automaten.
     */
    public void setPosition(Vector2 position)
    {
        this.position.set(position);
    }


    /**
     * Setztdie Punkte, die das Element vergibt.
     * @param points Die neuen Punkte, die das Element vergibt.
     */
    public void setPoints(int points)
    {
        this.points.set(points);
    }

    /**
     * Dreht das PlacedElement im Uhrzeigersinn.
     */
    public void rotateClockwise()
    {
        if (baseElement.getMedia().canRotate())
            rotation.setValue(rotation.get() + baseElement.getMedia().getRotationAccuracy());
    }

    /**
     * Dreht das PlacedElement gegen den Uhrzeigersinn.
     */
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
