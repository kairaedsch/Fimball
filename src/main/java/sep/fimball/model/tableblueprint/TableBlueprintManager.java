package sep.fimball.model.tableblueprint;

import javafx.beans.property.ReadOnlyListProperty;
import javafx.beans.property.SimpleListProperty;
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

    private SimpleListProperty<TableBlueprint> tableBlueprints;

    private TableBlueprintManager()
    {
        tableBlueprints = new SimpleListProperty<>(FXCollections.observableArrayList());

        //TODO load real
        tableBlueprints.add(new TableBlueprint("Testautomat 1", 0));
        tableBlueprints.add(new TableBlueprint("Testautomat 2", 1));
        tableBlueprints.add(new TableBlueprint("Testautomat 3", 2));
    }

    public ReadOnlyListProperty<TableBlueprint> tableBlueprintsProperty()
    {
        return tableBlueprints;
    }

    public void save(int blueprintId)
    {

    }
}
