package sep.fimball.model.blueprint.elementtype;

import javafx.beans.property.StringProperty;

import java.util.Map;

/**
 * Created by kaira on 15.11.2016.
 */
public class MediaElementType
{
    private StringProperty name;
    private StringProperty description;

    private Map<Integer, MediaElementEvent> eventMap;

    public MediaElementType(StringProperty name, StringProperty description, Map<Integer, MediaElementEvent> eventMap)
    {

        this.name = name;
        this.description = description;
        this.eventMap = eventMap;
    }

    public String getName()
    {
        return name.get();
    }

    public StringProperty nameProperty()
    {
        return name;
    }

    public String getDescription()
    {
        return description.get();
    }

    public StringProperty descriptionProperty()
    {
        return description;
    }

    public Map<Integer, MediaElementEvent> getEventMap()
    {
        return eventMap;
    }
}
