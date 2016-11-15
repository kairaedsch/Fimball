package sep.fimball.model.blueprint;

import javafx.beans.property.MapProperty;
import javafx.beans.property.ReadOnlyMapProperty;
import javafx.beans.property.SimpleMapProperty;
import javafx.collections.FXCollections;
import sep.fimball.general.data.Config;
import sep.fimball.model.blueprint.json.ElementTypeJson;
import sep.fimball.model.blueprint.json.JsonLoader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

/**
 * Der ElementManager verwaltet alle Elementtypen zum Serialisieren.
 */
public class ElementTypeManager
{
    /**
     * Stellt sicher, dass es nur einen ElementManager gibt, der alle Elemente verwalten soll um Duplikate zu vermeiden.
     */
    private static ElementTypeManager singletonInstance;

    /**
     * Gibt den bereits existierenden ElementManager oder  einen neu angelegten zurück, falls noch keiner existiert.
     *
     * @return
     */
    public static ElementTypeManager getInstance()
    {
        if (singletonInstance == null) singletonInstance = new ElementTypeManager();
        return singletonInstance;
    }

    /**
     * Liste von Elementtypen.
     */
    private MapProperty<String, ElementTypeJson> elements;

    /**
     * Gibt die vom ElementManager verwalteten Elemente zurück.
     */
    private ElementTypeManager()
    {
        elements = new SimpleMapProperty<>(FXCollections.observableHashMap());

        try
        {
            Files.list(Paths.get(Config.pathToData + Config.pathDataToElements)).filter((e) -> e.toFile().isDirectory()).forEach(this::loadElement);
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    private void loadElement(Path path)
    {
        Path jsonPath = Paths.get(path.toString() + Config.pathElementsToDataJson);

        Optional<ElementTypeJson> elementTypeOptional = JsonLoader.loadFromJson(jsonPath, ElementTypeJson.class);

        if(elementTypeOptional.isPresent())
        {
            System.out.println("Element Type \"" + path.getFileName() + "\" loaded");
            elements.put(path.getFileName().toString(), elementTypeOptional.get());
        }
        else
        {
            System.err.println("Element Type \"" + path.getFileName() + "\" not loaded");
        }
    }

    public ReadOnlyMapProperty<String, ElementTypeJson> elementsProperty()
    {
        return elements;
    }
}
