package sep.fimball.model.elements;

import javafx.beans.property.MapProperty;
import javafx.beans.property.SimpleMapProperty;
import javafx.collections.FXCollections;

/**
 * Created by kaira on 01.11.2016.
 */
public class PlacedElementList
{
    private MapProperty<Integer, PlacedElement> elements;

    public PlacedElementList()
    {
        this.elements = new SimpleMapProperty<>(FXCollections.observableHashMap());
    }

    public PlacedElement getElementByBlueprintElementId(int blueprintElemnentId)
    {
        return elements.get(blueprintElemnentId);
    }

    public void addElement(PlacedElement placedElement)
    {
        elements.put(placedElement.blueprintElementId.get(), placedElement);
    }

    public boolean removeElement(int blueprintElementId)
    {
        return elements.remove(blueprintElementId) != null;
    }
}
