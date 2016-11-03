package sep.fimball.model.tableblueprint;

import javafx.beans.property.ReadOnlyMapProperty;
import javafx.beans.property.SimpleMapProperty;
import javafx.collections.FXCollections;

/**
 * Created by kaira on 01.11.2016.
 */
public class TableBlueprintManager
{
    private static TableBlueprintManager singletonInstance;

    public static TableBlueprintManager getInstance()
    {
        if(singletonInstance == null) singletonInstance = new TableBlueprintManager();
        return singletonInstance;
    }

    private SimpleMapProperty<Integer, TableBlueprint> tableBlueprints;

    private TableBlueprintManager()
    {
        tableBlueprints = new SimpleMapProperty<>(FXCollections.observableHashMap());

        //TODO load real Blueprints
        tableBlueprints.put(0, new TableBlueprint("Testautomat 1", 0));
        tableBlueprints.put(1, new TableBlueprint("Testautomat 2", 1));
        tableBlueprints.put(2, new TableBlueprint("Testautomat 3", 2));
    }

    public ReadOnlyMapProperty<Integer, TableBlueprint> tableBlueprintsProperty()
    {
        return tableBlueprints;
    }

    public void save(int blueprintId)
    {

    }
}
