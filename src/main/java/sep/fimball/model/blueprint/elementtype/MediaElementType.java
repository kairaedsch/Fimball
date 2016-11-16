package sep.fimball.model.blueprint.elementtype;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import sep.fimball.model.blueprint.json.ElementTypeJson;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by kaira on 15.11.2016.
 */
public class MediaElementType
{
    private StringProperty name;
    private StringProperty description;

    private Map<Integer, MediaElementEvent> eventMap;

    public MediaElementType(ElementTypeJson.MediaElementTypeJson mediaElement)
    {
        name = new SimpleStringProperty(mediaElement.general.editorName);
        description  = new SimpleStringProperty(mediaElement.general.editorDescription);

        eventMap = new HashMap<>();
        for (ElementTypeJson.MediaElementTypeJson.MediaElementEventJson event : mediaElement.events)
        {
            // TODO hashCode musst not be unique
            eventMap.put(event.colliderId.hashCode(), new MediaElementEvent(event));
        }
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
