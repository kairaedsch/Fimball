package sep.fimball.viewmodel.window.pinballmachine.editor;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import sep.fimball.general.data.Vector2;
import sep.fimball.model.blueprint.pinballmachine.PlacedElement;

/**
 * Wrapper für ein PlacedElement, welches verschoben wird.
 */
public class DraggedElement
{
    /**
     * Die genaue Position in Grid-Einheiten, die nicht gerundet wird.
     */
    private ObjectProperty<Vector2> accuratePosition;

    /**
     * Das Element, welches verschoben wird.
     */
    private PlacedElement placedElement;

    /**
     * Erstellt ein neues DraggedElement aus einem PlacedElement.
     * @param placedElement Das PlacedElement, welches verschoben wird.
     */
    public DraggedElement(PlacedElement placedElement)
    {
        this.accuratePosition = new SimpleObjectProperty<>(placedElement.positionProperty().get());
        this.placedElement = placedElement;
    }

    /**
     * Gibt die genaue Position zurück.
     * @return Die genaue Position.
     */
    public Vector2 getAccuratePosition()
    {
        return accuratePosition.get();
    }

    /**
     * Gibt die genaue Position als Property zurück.
     * @return Die genaue Position als Property.
     */
    public ObjectProperty<Vector2> accuratePositionProperty()
    {
        return accuratePosition;
    }

    /**
     * Setzt die genaue Position.
     * @param accuratePosition Die genaue Position.
     */
    public void setAccuratePosition(Vector2 accuratePosition)
    {
        this.accuratePosition.set(accuratePosition);
        placedElement.setPosition(accuratePosition.round());

    }

    /**
     * Gibt das verschobene PlacedElement zurück.
     * @return Das verschobene PlacedElement
     */
    public PlacedElement getPlacedElement()
    {
        return placedElement;
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
