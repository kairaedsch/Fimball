package sep.fimball.model.blueprint.pinballmachine;

import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import sep.fimball.general.data.Config;
import sep.fimball.general.data.Vector2;
import sep.fimball.model.blueprint.base.BaseElementManager;
import sep.fimball.model.blueprint.json.JsonFileManager;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

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
     *
     * @return Instanz des PinballMachineManager
     */
    public static PinballMachineManager getInstance()
    {
        if (singletonInstance == null)
            singletonInstance = new PinballMachineManager();
        return singletonInstance;
    }

    /**
     * Die Liste der gespeicherten Flipperautomaten.
     */
    private ListProperty<PinballMachine> pinballMachines;

    /**
     * Erzeugt eine neue Instanz von PinballMachineManager.
     */
    private PinballMachineManager()
    {
        pinballMachines = new SimpleListProperty<>(FXCollections.observableArrayList());

        try
        {
            Files.list(Paths.get(Config.pathToPinballMachines())).filter((e) -> e.toFile().isDirectory()).forEach(this::loadMachine);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Erstellt eine neue PinballMachine und fügt sie zur Liste hinzu.
     *
     * @return Die neu erstellte PinballMachine.
     */
    public PinballMachine createNewMachine()
    {
        PinballMachine pinballMachine = new PinballMachine("New Pinball Machine", Config.uniqueId() + "", null);
        pinballMachine.addElement(BaseElementManager.getInstance().getElement("ball"), new Vector2());
        savePinballMachine(pinballMachine);
        pinballMachines.add(pinballMachine);
        return pinballMachine;
    }

    /**
     * Liest eine PinballMachineJson aus und erstellt daraus eine neue PinballMachine.
     *
     * @param path Der Pfad zur gespeicherten PinballMachineJson.
     */
    private void loadMachine(Path path)
    {
        String pinballMachineId = path.getFileName().toString();

        Path jsonPath = Paths.get(Config.pathToPinballMachineGeneralJson(pinballMachineId));

        Optional<PinballMachineJson> pinballMachineOptional = JsonFileManager.loadFromJson(jsonPath, PinballMachineJson.class);
        Optional<PinballMachine> pinballMachine = PinballMachineFactory.createPinballMachine(pinballMachineOptional, pinballMachineId);

        if(pinballMachine.isPresent())
        {
            pinballMachines.add(pinballMachine.get());
        }
    }

    /**
     * Lädt die Elemente einer gegebenen PinballMachine.
     *
     * @param pinballMachine Die PinballMachine deren Elemente geladen werden sollen.
     */
    void loadMachineElements(PinballMachine pinballMachine)
    {
        Path jsonPath = Paths.get(Config.pathToPinballMachinePlacedElementsJson(pinballMachine.getID()));

        Optional<PlacedElementListJson> placedElementListOptional = JsonFileManager.loadFromJson(jsonPath, PlacedElementListJson.class);
        Optional<List<PlacedElement>> PlacedElementList = PlacedElementListFactory.createPlacedElementList(placedElementListOptional);

        if (placedElementListOptional.isPresent())
        {
            for (PlacedElement placedElement : PlacedElementList.get())
            {
                pinballMachine.addElement(placedElement);
            }
        }
    }

    /**
     * Speichert die gegebene PinballMachine und ihre Bahnelemente.
     *
     * @param pinballMachine Die zu speichernde PinballMachine.
     */
    void savePinballMachine(PinballMachine pinballMachine)
    {
        Path pathToMachine = Paths.get(Config.pathToPinballMachine(pinballMachine.getID()));
        if (!pathToMachine.toFile().exists())
        {
            boolean couldCreateFolder = pathToMachine.toFile().mkdir();
            if (!couldCreateFolder)
            {
                System.err.println("Could not create folder: " + pathToMachine);
                return;
            }
        }

        PinballMachineJson pinballMachineJson = PinballMachineFactory.createPlacedElementListJson(pinballMachine);
        JsonFileManager.saveToJson(Config.pathToPinballMachineGeneralJson(pinballMachine.getID()), pinballMachineJson);

        PlacedElementListJson placedElementListJson = PlacedElementListFactory.createPlacedElementListJson(pinballMachine.elementsProperty());
        JsonFileManager.saveToJson(Config.pathToPinballMachinePlacedElementsJson(pinballMachine.getID()), placedElementListJson);

        // TODO save image of pinballmachine
    }

    /**
     * Löscht die gegebene PinballMachine.
     *
     * @param pinballMachine Die PinballMachine, die gelöscht werden soll.
     * @return Gibt zurück ob die erfolgreich gelöscht wurde.
     */
    boolean deleteMachine(PinballMachine pinballMachine)
    {
        try
        {
            Files.deleteIfExists(Paths.get(Config.pathToPinballMachineGeneralJson(pinballMachine.getID())));
            Files.deleteIfExists(Paths.get(Config.pathToPinballMachinePlacedElementsJson(pinballMachine.getID())));
            Files.deleteIfExists(Paths.get(Config.pathToPinballMachineImagePreview(pinballMachine.getID())));
            Files.deleteIfExists(Paths.get(Config.pathToPinballMachine(pinballMachine.getID())));

            return pinballMachines.remove(pinballMachine);
        }
        catch (IOException e)
        {
            System.err.println("Machine elem \"" + pinballMachine.getID() + "\" not deleted");
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Gibt die Liste der gespeicherten Flipperautomaten zurück.
     * @return  Die Liste der gespeicherten Flipperautomaten.
     */
    public ListProperty<PinballMachine> pinballMachinesProperty()
    {
        return pinballMachines;
    }
}
