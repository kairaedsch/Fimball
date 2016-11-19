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
     *
     * @return Instanz des PinballMachineManager
     */
    public static PinballMachineManager getInstance()
    {
        if (singletonInstance == null) singletonInstance = new PinballMachineManager();
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
            Files.list(Paths.get(Config.pathToPinballMachines())).filter((e) -> e.toFile().isDirectory()).forEach(this::loadMachine);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public PinballMachine createNewMachine()
    {
        PinballMachine pinballMachine = new PinballMachine("New Pinball Machine", Config.uniqueId() + "", null);
        pinballMachines.add(pinballMachine);
        return pinballMachine;
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

            if (placedElementListJson.elements != null)
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
                    else
                    {
                        System.err.println("Machine elem \"" + pinballMachineId + "\" not loaded: baseElementId \"" + element.baseElementId + "\" does not exist");
                    }
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

    void savePinballMachine(PinballMachine pinballMachine)
    {
        PinballMachineJson pinballMachineJson = new PinballMachineJson();
        pinballMachineJson.name = pinballMachine.nameProperty().getName();

        pinballMachineJson.highscores = new PinballMachineJson.HighscoreJson[pinballMachine.highscoreListProperty().size()];
        int counter = 0;
        for (Highscore highscore : pinballMachine.highscoreListProperty())
        {
            PinballMachineJson.HighscoreJson highscoreJson = new PinballMachineJson.HighscoreJson();
            highscoreJson.score = highscore.scoreProperty().getValue();
            highscoreJson.playerName = highscore.playerNameProperty().getValue();
            pinballMachineJson.highscores[counter] = highscoreJson;
            counter++;
        }

        JsonFileManager.saveToJson(Config.pathToPinballMachineGeneralJson(pinballMachine.getID()), pinballMachineJson);

        PlacedElementListJson placedElementListJson = new PlacedElementListJson();
        placedElementListJson.elements = new PlacedElementListJson.PlacedElementJson[pinballMachine.getElements().size()];
        counter = 0;
        for (PlacedElement placedElement : pinballMachine.getElements())
        {
            PlacedElementListJson.PlacedElementJson placedElementJson = new PlacedElementListJson.PlacedElementJson();
            placedElementJson.baseElementId = placedElement.getBaseElement().getId();
            placedElementJson.position = placedElement.positionProperty().getValue();
            placedElementJson.rotation = placedElement.rotationProperty().getValue();
            placedElementJson.points = placedElement.pointsProperty().getValue();
            placedElementJson.multiplier = placedElement.multiplierProperty().getValue();
            placedElementListJson.elements[counter] = placedElementJson;
            counter++;
        }

        JsonFileManager.saveToJson(Config.pathToPinballMachinePlacedElementsJson(pinballMachine.getID()), placedElementListJson);

        // TODO save image of pinballmachine
    }

    boolean deleteMachine(PinballMachine pinballMachine)
    {
        try
        {
            Files.deleteIfExists(Paths.get(Config.pathToPinballMachineGeneralJson(pinballMachine.getID())));
            Files.deleteIfExists(Paths.get(Config.pathToPinballMachinePlacedElementsJson(pinballMachine.getID())));
            Files.deleteIfExists(Paths.get(Config.pathToPinballMachine(pinballMachine.getID())));

            return true;
        }
        catch (IOException e)
        {
            System.err.println("Machine elem \"" + pinballMachine.getID() + "\" not saved");
            e.printStackTrace();
            return false;
        }
    }

    public ListProperty<PinballMachine> pinballMachinesProperty()
    {
        return pinballMachines;
    }
}
