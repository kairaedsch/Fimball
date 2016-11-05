package sep.fimball.model.tableblueprint;

import javafx.beans.property.SimpleMapProperty;
import javafx.collections.FXCollections;

/**
 * Created by kaira on 01.11.2016.
 */
public class MachineElementList
{
    private SimpleMapProperty<Integer, MachineElement> elements;

    public MachineElementList()
    {
        this.elements = new SimpleMapProperty<>(FXCollections.observableHashMap());
    }

    public MachineElement getElementByBlueprintElementId(int blueprintElemnentId)
    {
        return elements.get(blueprintElemnentId);
    }

    public void addElement(MachineElement machineElement)
    {
        elements.put(machineElement.blueprintElementId.get(), machineElement);
    }

    public boolean removeElement(int blueprintElementId)
    {
        return elements.remove(blueprintElementId) != null;
    }
}
