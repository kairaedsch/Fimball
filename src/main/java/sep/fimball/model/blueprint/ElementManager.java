package sep.fimball.model.blueprint;

import javafx.beans.property.MapProperty;
import javafx.beans.property.ReadOnlyMapProperty;
import javafx.beans.property.SimpleMapProperty;
import javafx.collections.FXCollections;

/**
 * Created by kaira on 03.11.2016.
 */
public class ElementManager
{
    private static ElementManager singletonInstance;

    public static ElementManager getInstance()
    {
        if(singletonInstance == null) singletonInstance = new ElementManager();
        return singletonInstance;
    }

    private MapProperty<String, Element> elements;

    private ElementManager()
    {
        elements = new SimpleMapProperty<>(FXCollections.observableHashMap());
    }

    public ReadOnlyMapProperty<String, Element> elementsProperty()
    {
        return elements;
    }
}
