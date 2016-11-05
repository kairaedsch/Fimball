package sep.fimball.model.tableblueprint;

import javafx.beans.property.ReadOnlyMapProperty;
import javafx.beans.property.SimpleMapProperty;
import javafx.collections.FXCollections;

/**
 * Created by kaira on 01.11.2016.
 */
public class Manager
{
    private static Manager singletonInstance;

    public static Manager getInstance()
    {
        if(singletonInstance == null) singletonInstance = new Manager();
        return singletonInstance;
    }

    private SimpleMapProperty<Integer, PinballMachine> tableBlueprints;

    private Manager()
    {
        tableBlueprints = new SimpleMapProperty<>(FXCollections.observableHashMap());

        //TODO load real Blueprints
        tableBlueprints.put(0, new PinballMachine("Testautomat 1", 0));
        tableBlueprints.put(1, new PinballMachine("Testautomat 2", 1));
        tableBlueprints.put(2, new PinballMachine("Testautomat 3", 2));
    }

    public ReadOnlyMapProperty<Integer, PinballMachine> tableBlueprintsProperty()
    {
        return tableBlueprints;
    }

    public void save(int blueprintId)
    {

    }
}
