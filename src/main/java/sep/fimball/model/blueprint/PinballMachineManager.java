package sep.fimball.model.blueprint;

import javafx.beans.property.MapProperty;
import javafx.beans.property.ReadOnlyMapProperty;
import javafx.beans.property.SimpleMapProperty;
import javafx.collections.FXCollections;

/**
 * Verwaltet die mitgelieferten und neu erstellten Flipperautomaten.
 */
public class PinballMachineManager
{
    /**
     * Die einzige Instanz des Managers.
     */
    private static PinballMachineManager singletonInstance;

    /**
     * Gibt den bereits existierenden ElementManager oder  einen neu angelegten zurück, falls noch keiner existiert.
     * @return
     */
    public static PinballMachineManager getInstance()
    {
        if(singletonInstance == null) singletonInstance = new PinballMachineManager();
        return singletonInstance;
    }

    private MapProperty<Integer, PinballMachine> tableBlueprints;

    /**
     * Erzeugt eine neue Instanz von PinballMachineManager.
     */
    private PinballMachineManager()
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

    /**
     * Serialisiert und speichert den Flipperautomaten mit der gegebenen Id.
     * @param blueprintId
     */
    public void save(int blueprintId)
    {

    }
}
