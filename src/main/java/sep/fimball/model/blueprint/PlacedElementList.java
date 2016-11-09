package sep.fimball.model.blueprint;

import javafx.beans.property.MapProperty;
import javafx.beans.property.ReadOnlyMapProperty;
import javafx.beans.property.SimpleMapProperty;
import javafx.collections.FXCollections;

/**
 * Die PlacedElementList fasst platzierte Elemente in einer Liste zusammen.
 */
public class PlacedElementList
{
    /**
     * Liste von platzierten Elementen.
     */
    private MapProperty<Integer, PlacedElement> elements;

    /**
     * Erzeugt einen neue Instanz von PlacedElementList.
     */
    public PlacedElementList()
    {
        this.elements = new SimpleMapProperty<>(FXCollections.observableHashMap());
    }

    /**
     * Gibt das Element mit der gegebenen ID zurück.
     * @param blueprintElemnentId
     * @return
     */
    public PlacedElement getElementByBlueprintElementId(int blueprintElemnentId)
    {
        return elements.get(blueprintElemnentId);
    }

    /**
     * Fügt ein Element der Liste hinzu.
     * @param placedElement
     */
    public void addElement(PlacedElement placedElement)
    {
        elements.put(placedElement.blueprintElementId.get(), placedElement);
    }

    /**
     * Entfernt das Element mit der gegebenen ID aus der Liste.
     * @param blueprintElementId
     * @return
     */
    public boolean removeElement(int blueprintElementId)
    {
        return elements.remove(blueprintElementId) != null;
    }

    public ReadOnlyMapProperty<Integer, PlacedElement> elementsProperty()
    {
        return elements;
    }
}
