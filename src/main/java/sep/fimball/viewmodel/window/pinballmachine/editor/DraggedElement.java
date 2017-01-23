package sep.fimball.viewmodel.window.pinballmachine.editor;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import sep.fimball.general.data.Vector2;
import sep.fimball.model.blueprint.pinballmachine.PlacedElement;

/**
 * Created by TheAsuro on 22.01.2017.
 */
public class DraggedElement
{
    private ObjectProperty<Vector2> accuratePosition;

    private PlacedElement placedElement;

    public DraggedElement(PlacedElement placedElement)
    {
        this.accuratePosition = new SimpleObjectProperty<>(placedElement.positionProperty().get());
        this.placedElement = placedElement;
    }

    public DraggedElement(PlacedElement placedElement, Vector2 accuratePos)
    {
        this.accuratePosition = new SimpleObjectProperty<>(accuratePos);
        this.placedElement = placedElement;
    }

    public Vector2 getAccuratePosition()
    {
        return accuratePosition.get();
    }

    public ObjectProperty<Vector2> accuratePositionProperty()
    {
        return accuratePosition;
    }

    public void setAccuratePosition(Vector2 accuratePosition)
    {
        this.accuratePosition.set(accuratePosition);
        placedElement.setPosition(accuratePosition.round());

    }

    public PlacedElement getPlacedElement()
    {
        return placedElement;
    }

    public void setPlacedElement(PlacedElement placedElement)
    {
        this.placedElement = placedElement;
    }

    @Override
    public int hashCode()
    {
        return placedElement.hashCode();
    }

    @Override
    public boolean equals(Object object)
    {
        if (object instanceof DraggedElement)
        {
            return this.getPlacedElement().equals(((DraggedElement) object).getPlacedElement());
        }
        return object.equals(this);
    }
}
