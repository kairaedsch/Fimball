package sep.fimball.model.blueprint.pinnballmachine;

import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;

/**
 * Die PlacedElementList fasst platzierte Elemente in einer Liste zusammen.
 */
public class PlacedElementList
{
    /**
     * Liste von platzierten Elementen.
     */
    private ListProperty<PlacedElement> elements;

    /**
     * Erzeugt einen neue Instanz von PlacedElementList.
     */
    public PlacedElementList()
    {
        this.elements = new SimpleListProperty<>(FXCollections.observableArrayList());
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
        elements.add(placedElement);
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

    public ListProperty<PlacedElement> elementsProperty()
    {
        return elements;
    }
}
