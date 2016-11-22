package sep.fimball.model.blueprint.base;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import sep.fimball.model.media.ElementImage;

import java.util.HashMap;
import java.util.Map;

/**
 * Diese Klasse enthält alle Informationen zu den Media-Eigenschaften eines BaseElements.
 */
public class BaseMediaElement
{
    /**
     * Der Name des BaseElements im Editor.
     */
    private String name;

    /**
     * Die Beschreibung des BaseElements im Editor.
     */
    private String description;

    /**
     * Gibt an, ob Elemente, die das zu diesem BaseMediaElement zugehörigen BaseElement haben, im Editor gedreht werden können.
     */
    private boolean canRotate;

    /**
     * Gibt an, um wie viel Grad Elemente, die das zu diesem BaseMediaElement zugehörigen BaseElement haben, im Editor pro Schritt gedreht werden können.
     */
    private int rotationAccuracy;

    /**
     * Das Bild des BaseElements.
     */
    private ObjectProperty<ElementImage> elementImage;

    /**
     * Enthält die MediaElementEvents. TODO
     */
    private Map<Integer, BaseMediaElementEvent> eventMap;

    /**
     * Estellt ein neues BaseMediaElement.
     *
     * @param mediaElement Das MediaElementJson, dessen Eigenschaften übernommen werden sollen.
     * @param baseElementId Die ID des zugehörigen BaseElements.
     */
    public BaseMediaElement(BaseElementJson.MediaElementJson mediaElement, String baseElementId)
    {
        name = mediaElement.general.editorName;
        description = mediaElement.general.editorDescription;
        canRotate = mediaElement.general.canRotate;
        rotationAccuracy = mediaElement.general.rotationAccuracy;

        eventMap = new HashMap<>();
        if (mediaElement.events != null)
        {
            for (BaseElementJson.MediaElementJson.MediaElementEventJson event : mediaElement.events)
            {
                // TODO hashCode must not be unique
                eventMap.put(event.colliderId.hashCode(), new BaseMediaElementEvent(event));
            }
        }
        if (canRotate)
        {
            elementImage = new SimpleObjectProperty<>(new ElementImage(baseElementId, rotationAccuracy));
        }
        else
        {
            elementImage = new SimpleObjectProperty<>(new ElementImage(baseElementId));
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

    public int getRotationAccuracy()
    {
        return rotationAccuracy;
    }

    public Map<Integer, BaseMediaElementEvent> getEventMap()
    {
        return eventMap;
    }

    public ReadOnlyObjectProperty<ElementImage> elementImageProperty()
    {
        return elementImage;
    }
}
