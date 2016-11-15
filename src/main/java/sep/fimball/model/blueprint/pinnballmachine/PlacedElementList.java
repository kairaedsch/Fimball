package sep.fimball.model.blueprint.pinnballmachine;

import javafx.beans.property.ListProperty;
import javafx.beans.property.ReadOnlyListProperty;
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
     * Fügt ein Element der Liste hinzu.
     * @param placedElement
     */
    public void addElement(PlacedElement placedElement)
    {
        elements.add(placedElement);
    }

    public void rempoveElement(PlacedElement placedElement)
    {
        elements.remove(placedElement);
    }

    public ReadOnlyListProperty<PlacedElement> elementsProperty()
    {
        return elements;
    }
}
