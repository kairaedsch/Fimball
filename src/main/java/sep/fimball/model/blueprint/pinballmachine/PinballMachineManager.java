package sep.fimball.model.blueprint.pinballmachine;

import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
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

    private ListProperty<PinballMachine> tableBlueprints;

    /**
     * Erzeugt eine neue Instanz von PinballMachineManager.
     */
    private PinballMachineManager()
    {
        tableBlueprints = new SimpleListProperty<>(FXCollections.observableArrayList());

        //TODO load real Blueprints
        tableBlueprints.add(new PinballMachine("Testautomat 1", 0));
        tableBlueprints.add(new PinballMachine("Testautomat 2", 1));
        tableBlueprints.add(new PinballMachine("Testautomat 3", 2));
    }

    public ListProperty<PinballMachine> tableBlueprintsProperty()
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
