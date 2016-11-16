package sep.fimball.model.blueprint.base;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import sep.fimball.model.ElementImage;
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
    private boolean canRotate;
    private double rotationAccuracy;

    private ObjectProperty<ElementImage> elementImage;

    private Map<Integer, MediaElementEvent> eventMap;

    public MediaElementType(BaseElementJson.MediaElementTypeJson mediaElement, String elementId)
    {
        name = mediaElement.general.editorName;
        description  = mediaElement.general.editorDescription;
        canRotate = mediaElement.general.canRotate;
        rotationAccuracy = mediaElement.general.rotationAccuracy;

        eventMap = new HashMap<>();
        if (mediaElement.events != null)
        {
            for (BaseElementJson.MediaElementTypeJson.MediaElementEventJson event : mediaElement.events)
            {
                // TODO hashCode musst not be unique
                eventMap.put(event.colliderId.hashCode(), new MediaElementEvent(event));
            }
        }
        if (canRotate)
        {
            elementImage = new SimpleObjectProperty<>(new ElementImage(elementId, (int)rotationAccuracy));
        }
        else
        {
            elementImage = new SimpleObjectProperty<>(new ElementImage(elementId));
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

    public boolean canRotate()
    {
        return canRotate;
    }

    public double getRotationAccuracy()
    {
        return rotationAccuracy;
    }

    public Map<Integer, MediaElementEvent> getEventMap()
    {
        return eventMap;
    }

    public ReadOnlyObjectProperty<ElementImage> elementImageProperty()
    {
        return elementImage;
    }
}
