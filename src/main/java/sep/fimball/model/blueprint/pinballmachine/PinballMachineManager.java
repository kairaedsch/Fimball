package sep.fimball.model.blueprint.pinballmachine;

import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import sep.fimball.general.data.Config;
import sep.fimball.general.data.Highscore;
import sep.fimball.model.blueprint.JsonFileManager;
import sep.fimball.model.blueprint.base.BaseElement;
import sep.fimball.model.blueprint.base.BaseElementManager;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
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
     * @return Instanz des PinballMachineManager
     */
    public static PinballMachineManager getInstance()
    {
        if(singletonInstance == null) singletonInstance = new PinballMachineManager();
        return singletonInstance;
    }

    /**
     * Die Liste der gespeicherten Pinballautomaten.
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
            Files.list(Paths.get(Config.pathToMachines())).filter((e) -> e.toFile().isDirectory()).forEach(this::loadMachine);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    private void loadMachine(Path path)
    {
        String pinballMachineId = path.getFileName().toString();
        
        Path jsonPath = Paths.get(Config.pathToPinballMachineGeneralJson(pinballMachineId));

        Optional<PinballMachineJson> pinballMachineOptional = JsonFileManager.loadFromJson(jsonPath, PinballMachineJson.class);

        if (pinballMachineOptional.isPresent())
        {
            PinballMachineJson pinballMachineJson = pinballMachineOptional.get();

            // TODO NullPointerException not very good
            try
            {
                ArrayList<Highscore> highscores = new ArrayList<>();
                for (PinballMachineJson.HighscoreJson highscoreJson : pinballMachineJson.highscores)
                {
                    highscores.add(new Highscore(highscoreJson.score, highscoreJson.playerName));
                }

                PinballMachine pinballMachine = new PinballMachine(pinballMachineJson.name, pinballMachineId, highscores);
                pinballMachines.add(pinballMachine);
                System.out.println("Machine      \"" + pinballMachineId + "\" loaded");

                loadMachineElements(pinballMachine, pinballMachineId);
            }
            catch (NullPointerException e)
            {
                System.err.println("Machine      \"" + pinballMachineId + "\" not loaded");
                e.printStackTrace();
            }

        }
        else
        {
            System.err.println("Machine      \"" + pinballMachineId + "\" not loaded");
        }
    }

    private void loadMachineElements(PinballMachine pinballMachine, String pinballMachineId)
    {
        Path jsonPath = Paths.get(Config.pathToPinballMachinePlacedElementsJson(pinballMachineId));

        Optional<PlacedElementListJson> placedElementListOptional = JsonFileManager.loadFromJson(jsonPath, PlacedElementListJson.class);

        if (placedElementListOptional.isPresent())
        {
            PlacedElementListJson placedElementListJson = placedElementListOptional.get();

            // TODO NullPointerException not very good

            if(placedElementListJson.elements != null)
            {
                int loaded = 0;
                for (PlacedElementListJson.PlacedElementJson element : placedElementListJson.elements)
                {
                    BaseElement baseElement = BaseElementManager.getInstance().getElement(element.baseElementId);
                    if (baseElement != null)
                    {
                        pinballMachine.addElement(new PlacedElement(baseElement, element.position, element.points, element.multiplier, element.rotation));
                        loaded++;
                    }
                    else System.err.println("Machine elem \"" + pinballMachineId + "\" not loaded: baseElementId \"" + element.baseElementId + "\" does not exist");
                }
                System.out.println("Machine elem \"" + pinballMachineId + "\" loaded: (" + loaded + "/" + placedElementListJson.elements.length + ")");
            }
            else
            {
                System.err.println("Machine elem \"" + pinballMachineId + "\" not loaded: Element List null");
            }
        }
        else
        {
            System.err.println("Machine elem \"" + pinballMachineId + "\" not loaded: All");
        }
    }

    public ListProperty<PinballMachine> pinballMachinesProperty()
    {
        return pinballMachines;
    }

    public PinballMachine createNewMachine()
    {
        PinballMachine pinballMachine = new PinballMachine("New Pinball Machine", Config.uniqueId() + "", null);
        pinballMachines.add(pinballMachine);
        return pinballMachine;
    }
}
