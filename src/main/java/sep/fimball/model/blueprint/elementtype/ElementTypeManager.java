package sep.fimball.model.blueprint.elementtype;

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
    private MapProperty<String, ElementType> elements;

    /**
     * Gibt die vom ElementManager verwalteten Elemente zurück.
     */
    private ElementTypeManager()
    {
        elements = new SimpleMapProperty<>(FXCollections.observableHashMap());

        try
        {
            Files.list(Paths.get(Config.pathToData + Config.pathDataToElements)).filter((e) -> e.toFile().isDirectory()).forEach(this::loadElement);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    private void loadElement(Path path)
    {
        Path jsonPath = Paths.get(path.toString() + Config.pathElementsToDataJson);

        Optional<ElementTypeJson> elementTypeOptional = JsonLoader.loadFromJson(jsonPath, ElementTypeJson.class);

        if (elementTypeOptional.isPresent())
        {
            ElementTypeJson elementTypeJson = elementTypeOptional.get();

            // TODO NullPointerException not very good
            try
            {
                ElementType elementType = new ElementType(elementTypeJson);
                elements.put(path.getFileName().toString(), elementType);
                System.out.println("Element Type \"" + path.getFileName() + "\" loaded");
            }
            catch (NullPointerException e)
            {
                System.err.println("Element Type \"" + path.getFileName() + "\" not loaded");
                e.printStackTrace();
            }

        }
        else
        {
            System.err.println("Element Type \"" + path.getFileName() + "\" not loaded");
        }
    }

    public ReadOnlyMapProperty<String, ElementType> elementsProperty()
    {
        return elements;
    }
}
