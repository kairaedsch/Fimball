package sep.fimball.model.blueprint.base;

import javafx.beans.property.MapProperty;
import javafx.beans.property.ReadOnlyMapProperty;
import javafx.beans.property.SimpleMapProperty;
import javafx.collections.FXCollections;
import sep.fimball.general.data.Config;
import sep.fimball.model.blueprint.json.JsonFileManager;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

/**
 * Der ElementManager verwaltet die Serialisierung aller Element-Typen.
 */
public class BaseElementManager
{
    /**
     * Stellt sicher, dass es nur einen ElementManager gibt, der alle Elemente verwalten soll, um Duplikate zu vermeiden.
     */
    private static BaseElementManager singletonInstance;

    /**
     * Gibt den bereits existierenden ElementManager oder einen neu angelegten zurück, falls noch keiner existiert.
     *
     * @return Der BaseElementManager.
     */
    public static BaseElementManager getInstance()
    {
        if (singletonInstance == null)
            singletonInstance = new BaseElementManager();
        return singletonInstance;
    }

    /**
     * Eine Map von IDs auf BaseElemente.
     */
    private MapProperty<String, BaseElement> elements;

    /**
     * Konstruiert einen BaseElementManager, dabei werden die Baupläne aller Spielelemente geladen.
     */
    private BaseElementManager()
    {
        elements = new SimpleMapProperty<>(FXCollections.observableHashMap());

        try
        {
            // Loads all directories
            Files.list(Paths.get(Config.pathToElements())).filter((e) -> e.toFile().isDirectory()).forEach(this::loadElement);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Liest ein BaseElement aus einer BaseElementJson aus und fügt es {@code elements} hinzu.
     *
     * @param path Der Pfad zu der gespeicherten BaseElementJson.
     */
    private void loadElement(Path path)
    {
        String elementTypeId = path.getFileName().toString();
        Path jsonPath = Paths.get(Config.pathToElementDataJson(elementTypeId));

        Optional<BaseElementJson> baseElementOptional = JsonFileManager.loadFromJson(jsonPath, BaseElementJson.class);

        if (baseElementOptional.isPresent())
        {
            BaseElementJson baseElementJson = baseElementOptional.get();

            try
            {
                BaseElement baseElement = new BaseElement(elementTypeId, baseElementJson);
                elements.put(path.getFileName().toString(), baseElement);
                System.out.println("Element Type \"" + elementTypeId + "\" loaded");
            }
            catch (IllegalArgumentException e)
            {
                System.err.println("Element Type \"" + elementTypeId + "\" not loaded");
                e.printStackTrace();
            }
        }
        else
        {
            System.err.println("Element Type \"" + elementTypeId + "\" not loaded");
        }
    }

    /**
     * Gibt die Map, die IDs auf die durch sie identifizierten Elementbaupläne abbildet, zurück.
     *
     * @return Die Map, die Elementbaupläne und deren ID enthält.
     */
    public ReadOnlyMapProperty<String, BaseElement> elementsProperty()
    {
        return elements;
    }

    /**
     * Gibt den Bauplan für ein Element zurück, der durch baseElementId identifiziert wird oder null, falls kein solcher Bauplan existiert.
     *
     * @param baseElementId Die ID des gesuchten Bauplans.
     * @return Der Bauplan mit der gegebenen ID.
     */
    public BaseElement getElement(String baseElementId)
    {
        return elements.get(baseElementId);
    }
}
