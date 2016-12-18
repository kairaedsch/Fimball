package sep.fimball.model.blueprint.pinballmachine;

import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.WritableImage;
import sep.fimball.general.data.Config;
import sep.fimball.general.data.DataPath;
import sep.fimball.model.blueprint.json.JsonFileManager;

import javax.imageio.ImageIO;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
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
     * Gibt den bereits existierenden PinballMachineManager oder einen neu angelegten zurück, falls noch keiner existiert.
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
     * Die Liste der Flipperautomaten.
     */
    private ListProperty<PinballMachine> pinballMachines;

    /**
     * Erzeugt eine neue Instanz von PinballMachineManager.
     */
    private PinballMachineManager()
    {
        pinballMachines = new SimpleListProperty<>(FXCollections.observableArrayList());

        // Lädt alle Pinball-Maschinen aus dem PinballMachine Ordner
        try
        {
            Files.list(Paths.get(DataPath.pathToPinballMachines())).filter((e) -> e.toFile().isDirectory()).forEach(this::loadMachine);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Erstellt eine neue PinballMachine, speichert diese und fügt sie zur Liste der Flipperautomaten hinzu.
     *
     * @return Die neu erstellte PinballMachine.
     */
    public PinballMachine createNewMachine()
    {
        PinballMachine pinballMachine = new PinballMachine("New Pinball Machine", Config.uniqueId(), Collections.emptyList(), this);
        savePinballMachine(pinballMachine);
        pinballMachines.add(pinballMachine);
        return pinballMachine;
    }

    /**
     * Liest eine PinballMachineJson aus und erstellt daraus eine neue PinballMachine welche zu der Liste der Flipperautomaten hinzugefügt wird.
     *
     * @param path Der Pfad zur gespeicherten PinballMachineJson.
     */
    private void loadMachine(Path path)
    {
        String pinballMachineId = path.getFileName().toString();

        Path jsonPath = Paths.get(DataPath.pathToPinballMachineGeneralJson(pinballMachineId));

        Optional<PinballMachineJson> pinballMachineJson = JsonFileManager.loadFromJson(jsonPath, PinballMachineJson.class);
        Optional<PinballMachine> pinballMachine = PinballMachineFactory.createPinballMachine(pinballMachineJson, pinballMachineId, this);

        if (pinballMachine.isPresent())
        {
            pinballMachines.add(pinballMachine.get());
        }
    }

    /**
     * Lädt die Elemente einer gegebenen PinballMachine und fügt die Elemente zu diese hinzu.
     *
     * @param pinballMachine Die PinballMachine deren Elemente geladen werden sollen.
     */
    void loadMachineElements(PinballMachine pinballMachine)
    {
        Path jsonPath = Paths.get(DataPath.pathToPinballMachinePlacedElementsJson(pinballMachine.getID()));

        Optional<PlacedElementListJson> placedElementListJson = JsonFileManager.loadFromJson(jsonPath, PlacedElementListJson.class);
        Optional<List<PlacedElement>> PlacedElementList = PlacedElementListFactory.createPlacedElementList(placedElementListJson);

        if (PlacedElementList.isPresent())
        {
            PlacedElementList.get().forEach(pinballMachine::addElement);
        }
    }

    /**
     * Speichert die gegebene PinballMachine und ihre Elemente.
     *
     * @param pinballMachine Die zu speichernde PinballMachine.
     */
    void savePinballMachine(PinballMachine pinballMachine)
    {
        Path pathToMachine = Paths.get(DataPath.pathToPinballMachine(pinballMachine.getID()));
        if (!pathToMachine.toFile().exists())
        {
            boolean couldCreateFolder = pathToMachine.toFile().mkdir();
            if (!couldCreateFolder)
            {
                System.err.println("Could not create folder: \"" + pathToMachine + "\". Machine \"" + pinballMachine.getID() + "\" was not saved.");
                return;
            }
        }

        PinballMachineJson pinballMachineJson = PinballMachineFactory.createPinballMachineJson(pinballMachine);
        JsonFileManager.saveToJson(DataPath.pathToPinballMachineGeneralJson(pinballMachine.getID()), pinballMachineJson);

        PlacedElementListJson placedElementListJson = PlacedElementListFactory.createPlacedElementListJson(pinballMachine.elementsProperty());
        JsonFileManager.saveToJson(DataPath.pathToPinballMachinePlacedElementsJson(pinballMachine.getID()), placedElementListJson);
    }

    /**
     * Lädt das Vorschaubild des gegebenen Automaten.
     *
     * @param pinballMachine Der Automat, dessen Vorschaubild geladen werden soll.
     * @return Der Pfad zum Vorschaubild des Automaten.
     */
    String loadPreviewImage(PinballMachine pinballMachine)
    {
        File content[] = new File(DataPath.pathToPinballMachine(pinballMachine.getID())).listFiles();

        if (content == null || content.length < 1)
        {
            System.err.println("No preview image found for automat: " + pinballMachine.getID());
            return DataPath.pathToDefaultPreview();
        }
        else
        {
            for (File entry : content)
            {
                if (entry.getName().contains(DataPath.machinePreviewImageFile.replace("/", "")))
                {
                    return entry.getAbsolutePath().replace('\\', '/');
                }
            }
            System.err.println("No preview image found for automat: " + pinballMachine.getID());
            return DataPath.pathToDefaultPreview();
        }
    }

    /**
     * Speichert das gegebene Vorschaubild zu dem gegebenen Automaten.
     *
     * @param pinballMachine Der Automat, dessen Vorschaubild gespeichert werden soll.
     * @param image          Das Vorschaubild, das gespeichert werden soll.
     */
    void savePreviewImage(PinballMachine pinballMachine, WritableImage image)
    {
        Path newPath = Paths.get(DataPath.generatePathToNewImagePreview(pinballMachine.getID(), System.currentTimeMillis()));
        Path pathToMachine = Paths.get(DataPath.pathToPinballMachine(pinballMachine.getID()));
        File[] directoryContent = pathToMachine.toFile().listFiles();

        if (directoryContent != null)
        {
            for (File entry : directoryContent)
            {
                if (entry.getName().contains(DataPath.machinePreviewImageFile.replace("/", "")))
                {
                    if (!entry.delete())
                    {
                        System.err.println("Could not delete file: " + entry.toString());
                    }
                }
            }
        }
        RenderedImage renderedImage = SwingFXUtils.fromFXImage(image, null);
        try
        {
            ImageIO.write(renderedImage, "png", newPath.toFile());
        }
        catch (IOException e)
        {
            System.err.println("Could not write preview image to file: " + newPath.toString());
        }
    }

    /**
     * Löscht die gegebene PinballMachine.
     *
     * @param pinballMachine Die PinballMachine, die gelöscht werden soll.
     * @return Gibt zurück ob diese erfolgreich gelöscht wurde.
     */
    boolean deleteMachine(PinballMachine pinballMachine)
    {
        try
        {
            // Lösche Dateien
            Files.deleteIfExists(Paths.get(DataPath.pathToPinballMachineGeneralJson(pinballMachine.getID())));
            Files.deleteIfExists(Paths.get(DataPath.pathToPinballMachinePlacedElementsJson(pinballMachine.getID())));
            Files.deleteIfExists(Paths.get(loadPreviewImage(pinballMachine)));

            // Lösche Ordner
            Files.deleteIfExists(Paths.get(DataPath.pathToPinballMachine(pinballMachine.getID())));

            // Entferne aus der List
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
     *
     * @return Die Liste der gespeicherten Flipperautomaten.
     */
    public ListProperty<PinballMachine> pinballMachinesProperty()
    {
        return pinballMachines;
    }
}
