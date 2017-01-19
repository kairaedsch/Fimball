package sep.fimball.model.blueprint.pinballmachine;

import javafx.beans.property.ListProperty;
import javafx.beans.property.ReadOnlyListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.WritableImage;
import sep.fimball.general.data.Config;
import sep.fimball.general.data.DataPath;
import sep.fimball.model.blueprint.json.JsonFileManager;

import javax.imageio.ImageIO;
import java.awt.image.RenderedImage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static sep.fimball.model.blueprint.json.JsonFileManager.saveToJson;

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
     * Erstellt eine neue PinballMachine, speichert diese und fügt sie zur Liste der Flipperautomaten hinzu.
     *
     * @return Die neu erstellte PinballMachine.
     */
    public PinballMachine createNewMachine()
    {
        PinballMachine pinballMachine = new PinballMachine("New Pinball Machine", Config.uniqueId(), Optional.empty(), Collections.emptyList(), this, true);
        savePinballMachine(pinballMachine, false);
        pinballMachines.add(pinballMachine);
        pinballMachine.unloadElements();
        return pinballMachine;
    }

    /**
     * Gibt die Liste der gespeicherten Flipperautomaten zurück.
     *
     * @return Die Liste der gespeicherten Flipperautomaten.
     */
    public ReadOnlyListProperty<PinballMachine> pinballMachinesProperty()
    {
        return pinballMachines;
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

        pinballMachine.ifPresent(machine -> pinballMachines.add(machine));
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
     * @param autoSave Gibt an, ob der AutoSave-Automat gespeichert werden soll.
     * @return Ob die PinballMachine gespeichert werden konnte.
     */
    boolean savePinballMachine(PinballMachine pinballMachine, boolean autoSave)
    {
        Path pathToMachine;
        if(autoSave) {
            pathToMachine = Paths.get(DataPath.pathToAutoSave());
        } else
        {
            pathToMachine = Paths.get(DataPath.pathToPinballMachine(pinballMachine.getID()));
        }
        if (!pathToMachine.toFile().exists())
        {
            boolean couldCreateFolder = pathToMachine.toFile().mkdir();
            if (!couldCreateFolder)
            {
                if(autoSave) {
                    System.err.println("Could not create folder: \"" + pathToMachine + "\". Auto save machine was not saved.");
                } else {
                    System.err.println("Could not create folder: \"" + pathToMachine + "\". Machine \"" + pinballMachine.getID() + "\" was not saved.");
                }
                return false;
            }
        }

        PinballMachineJson pinballMachineJson = PinballMachineFactory.createPinballMachineJson(pinballMachine);
        boolean successMachine = JsonFileManager.saveToJson(DataPath.pathToPinballMachineGeneralJson(pinballMachine.getID()), pinballMachineJson);

        PlacedElementListJson placedElementListJson = PlacedElementListFactory.createPlacedElementListJson(pinballMachine.elementsProperty());
        boolean successElements = saveToJson(DataPath.pathToPinballMachinePlacedElementsJson(pinballMachine.getID()), placedElementListJson);

        return successMachine && successElements;
    }

    /**
     * Speichert das gegebene Vorschaubild zu dem gegebenen Automaten.
     *
     * @param pinballMachine      Der Automat, dessen Vorschaubild gespeichert werden soll.
     * @param image               Das Vorschaubild, das gespeichert werden soll.
     * @param newPreviewImagePath Der Pfad an dem das neue Vorschaubild gespeichert werden soll.
     */
    void savePreviewImage(PinballMachine pinballMachine, WritableImage image, String newPreviewImagePath)
    {
        Path newPath = Paths.get(DataPath.generatePathToNewImagePreview(pinballMachine.getID(), newPreviewImagePath));
        RenderedImage renderedImage = SwingFXUtils.fromFXImage(image, null);
        try
        {
            // Lösche das alte Vorschaubild
            deletePreviewImage(pinballMachine);
        }
        catch (IOException e)
        {
            System.err.println("Could not delete old preview image: " + pinballMachine.absolutePreviewImagePathProperty().get());
        }

        try
        {
            // Speichere das neue
            ImageIO.write(renderedImage, "png", newPath.toFile());
        }
        catch (IOException | IllegalArgumentException e)
        {
            System.err.println("Could not write preview image to file: " + newPath.toString());
        }
    }

    /**
     * Löscht die gegebene PinballMachine.
     *
     * @param pinballMachine Die PinballMachine, die gelöscht werden soll.
     * @param autoSave Gibt an, ob der AutoSave-Automat gelöscht werden soll.
     * @return Gibt zurück ob diese erfolgreich gelöscht wurde.
     */
    boolean deleteMachine(PinballMachine pinballMachine, boolean autoSave)
    {
        String pathToGerneralJson;
        String pathToElementsJson;
        String pathToPinballMachine;
        if(autoSave) {
            pathToGerneralJson = DataPath.pathToAutoSaveGeneralJson();
            pathToElementsJson = DataPath.pathToAutoSavePlacedElementsJson();
            pathToPinballMachine = DataPath.pathToAutoSave();
        } else {
            pathToGerneralJson = DataPath.pathToPinballMachineGeneralJson(pinballMachine.getID());
            pathToElementsJson = DataPath.pathToPinballMachinePlacedElementsJson(pinballMachine.getID());
            pathToPinballMachine = DataPath.pathToPinballMachine(pinballMachine.getID());
        }

        try
        {
            // Lösche Dateien
            Files.deleteIfExists(Paths.get(pathToGerneralJson));
            Files.deleteIfExists(Paths.get(pathToElementsJson));
            if(!autoSave) deletePreviewImage(pinballMachine);

            // Lösche Ordner
            Files.deleteIfExists(Paths.get(pathToPinballMachine));

            // Entferne aus der List
            return pinballMachines.remove(pinballMachine);
        }
        catch (IOException e)
        {
            if(autoSave) {
                System.err.println("Auto saved machine not deleted");
            } else {
                System.err.println("Machine elem \"" + pinballMachine.getID() + "\" not deleted");
            }
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Löscht das PreviewImage das übergebenen Automaten.
     *
     * @param pinballMachine Der Automat, dessen PreviewImage gelöscht werden soll.
     * @throws IOException Wenn das PreviewImage nicht gelöscht werden konnte.
     */
    private void deletePreviewImage(PinballMachine pinballMachine) throws IOException
    {
        if (pinballMachine.relativePreviewImagePathProperty().get().isPresent())
        {
            Files.deleteIfExists(Paths.get(pinballMachine.absolutePreviewImagePathProperty().get()));
        }
    }

    /**
     * Speichert die gegebene PinballMachine und ihre Elemente als AutoSave-Automaten.
     *
     * @param pinballMachine Die zu speichernde PinballMachine.
     * @return Ob die PinballMachine gespeichert werden konnte.
     */
    public boolean saveAutoSaveMachine(PinballMachine pinballMachine) {
        return savePinballMachine(pinballMachine,true);
    }

    /**
     * Löscht den AutoSave-Automaten.
     *
     * @return Gibt zurück, ob das Löschen erfolgreich war.
     */
    public boolean deleteAutoSaveMachine() {
        return deleteMachine(null, true);
    }

    /**
     * Lädt den AutoSave-Automaten.
     * @return Der geladene Automat.
     */
    public Optional<PinballMachine> loadAutoSavedMachine()
    {
        Path jsonPath = Paths.get(DataPath.pathToAutoSaveGeneralJson());

        Optional<PinballMachineJson> pinballMachineJson = JsonFileManager.loadFromJson(jsonPath, PinballMachineJson.class);

        if(pinballMachineJson.isPresent()) {
            for(PinballMachine pinballMachine : pinballMachines) {
                if(pinballMachine.getID().equals(pinballMachineJson.get().id)) {
                    loadAutoSaveElements(pinballMachine);
                    return Optional.of(pinballMachine);
                }
            }
        }
        return Optional.empty();
    }


    /**
     * Lädt die Elemente des AutoSave-Automaten in den gegebenen Automaten.
     * @param pinballMachine Der Automat, der die Elemente des AutoSave-Automaten erhalten soll.
     */
    private void loadAutoSaveElements(PinballMachine pinballMachine) {
        Path jsonPath = Paths.get(DataPath.pathToAutoSavePlacedElementsJson());

        Optional<PlacedElementListJson> placedElementListJson = JsonFileManager.loadFromJson(jsonPath, PlacedElementListJson.class);
        Optional<List<PlacedElement>> PlacedElementList = PlacedElementListFactory.createPlacedElementList(placedElementListJson);

        if (PlacedElementList.isPresent())
        {
            List<PlacedElement> placedElements = new ArrayList<>();
            placedElements.addAll(pinballMachine.elementsProperty());
            placedElements.forEach(pinballMachine::removeElement);
            PlacedElementList.get().forEach(pinballMachine::addElement);
        }
    }
}
