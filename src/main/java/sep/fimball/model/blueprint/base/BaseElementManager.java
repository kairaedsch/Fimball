package sep.fimball.model.blueprint.base;

import javafx.beans.property.MapProperty;
import javafx.beans.property.ReadOnlyMapProperty;
import javafx.beans.property.SimpleMapProperty;
import javafx.collections.FXCollections;
import sep.fimball.general.data.Config;
import sep.fimball.model.blueprint.json.BaseElementJson;
import sep.fimball.model.blueprint.json.JsonFileManager;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

/**
 * Der ElementManager verwaltet alle Elementtypen zum Serialisieren.
 */
public class BaseElementManager
{
    /**
     * Stellt sicher, dass es nur einen ElementManager gibt, der alle Elemente verwalten soll um Duplikate zu vermeiden.
     */
    private static BaseElementManager singletonInstance;

    /**
     * Gibt den bereits existierenden ElementManager oder  einen neu angelegten zurück, falls noch keiner existiert.
     *
     * @return
     */
    public static BaseElementManager getInstance()
    {
        if (singletonInstance == null) singletonInstance = new BaseElementManager();
        return singletonInstance;
    }

    /**
     * Liste von Elementtypen.
     */
    private MapProperty<String, BaseElement> elements;

    /**
     * Gibt die vom ElementManager verwalteten Elemente zurück.
     */
    private BaseElementManager()
    {
        elements = new SimpleMapProperty<>(FXCollections.observableHashMap());

        try
        {
            Files.list(Paths.get(Config.pathToElements())).filter((e) -> e.toFile().isDirectory()).forEach(this::loadElement);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    private void loadElement(Path path)
    {
        String elementTypeId = path.getFileName().toString();
        Path jsonPath = Paths.get(Config.pathToElementDataJson(elementTypeId));

        Optional<BaseElementJson> elementTypeOptional = JsonFileManager.loadFromJson(jsonPath, BaseElementJson.class);

        if (elementTypeOptional.isPresent())
        {
            BaseElementJson baseElementJson = elementTypeOptional.get();

            // TODO NullPointerException not very good
            try
            {
                BaseElement baseElement = new BaseElement(elementTypeId, baseElementJson);
                elements.put(path.getFileName().toString(), baseElement);
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

    public ReadOnlyMapProperty<String, BaseElement> elementsProperty()
    {
        return elements;
    }

    public BaseElement getElement(String baseElementId)
    {
        return elements.get(baseElementId);
    }
}
