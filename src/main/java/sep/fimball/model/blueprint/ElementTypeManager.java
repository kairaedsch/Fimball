package sep.fimball.model.blueprint;

import javafx.beans.property.MapProperty;
import javafx.beans.property.ReadOnlyMapProperty;
import javafx.beans.property.SimpleMapProperty;
import javafx.collections.FXCollections;

/**
 * Created by kaira on 03.11.2016.
 */
public class ElementTypeManager
{
    private static ElementTypeManager singletonInstance;

    public static ElementTypeManager getInstance()
    {
        if(singletonInstance == null) singletonInstance = new ElementTypeManager();
        return singletonInstance;
    }

    private MapProperty<String, ElementType> elements;

    private ElementTypeManager()
    {
        elements = new SimpleMapProperty<>(FXCollections.observableHashMap());
    }

    public ReadOnlyMapProperty<String, ElementType> elementsProperty()
    {
        return elements;
    }
}
