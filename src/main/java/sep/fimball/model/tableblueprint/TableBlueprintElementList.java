package sep.fimball.model.tableblueprint;

import javafx.beans.property.SimpleMapProperty;
import javafx.collections.FXCollections;

/**
 * Created by kaira on 01.11.2016.
 */
public class TableBlueprintElementList
{
    private SimpleMapProperty<Integer, TableBlueprintElement> elements;

    public TableBlueprintElementList()
    {
        this.elements = new SimpleMapProperty<>(FXCollections.observableHashMap());
    }

    public TableBlueprintElement getElementByBlueprintElementId(int blueprintElemnentId)
    {
        return elements.get(blueprintElemnentId);
    }

    public void addElement(TableBlueprintElement tableBlueprintElement)
    {
        elements.put(tableBlueprintElement.blueprintElementId.get(), tableBlueprintElement);
    }

    public boolean removeElement(int blueprintElementId)
    {
        return elements.remove(blueprintElementId) != null;
    }
}
