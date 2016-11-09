package sep.fimball.model.blueprint;

import javafx.beans.property.MapProperty;
import javafx.beans.property.ReadOnlyMapProperty;
import javafx.beans.property.SimpleMapProperty;
import javafx.collections.FXCollections;

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
     * @return
     */
    public static ElementTypeManager getInstance()
    {
        if(singletonInstance == null) singletonInstance = new ElementTypeManager();
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
    }

    public ReadOnlyMapProperty<String, ElementType> elementsProperty()
    {
        return elements;
    }
}
