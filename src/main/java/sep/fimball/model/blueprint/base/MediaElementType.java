package sep.fimball.model.blueprint.base;

import sep.fimball.model.blueprint.json.BaseElementJson;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by kaira on 15.11.2016.
 */
public class MediaElementType
{
    private String name;
    private String description;

    private Map<Integer, MediaElementEvent> eventMap;

    public MediaElementType(BaseElementJson.MediaElementTypeJson mediaElement)
    {
        name = mediaElement.general.editorName;
        description  = mediaElement.general.editorDescription;

        eventMap = new HashMap<>();
        if (mediaElement.events != null)
        {
            for (BaseElementJson.MediaElementTypeJson.MediaElementEventJson event : mediaElement.events)
            {
                // TODO hashCode musst not be unique
                eventMap.put(event.colliderId.hashCode(), new MediaElementEvent(event));
            }
        }
    }

    public String getName()
    {
        return name;
    }

    public String getDescription()
    {
        return description;
    }

    public Map<Integer, MediaElementEvent> getEventMap()
    {
        return eventMap;
    }
}
