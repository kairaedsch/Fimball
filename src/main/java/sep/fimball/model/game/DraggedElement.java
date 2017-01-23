package sep.fimball.model.game;

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
    }

    public PlacedElement getPlacedElement()
    {
        return placedElement;
    }

    public void setPlacedElement(PlacedElement placedElement)
    {
        this.placedElement = placedElement;
    }
}
