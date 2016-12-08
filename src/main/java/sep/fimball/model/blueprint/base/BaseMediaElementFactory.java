package sep.fimball.model.blueprint.base;

import sep.fimball.general.data.Vector2;
import sep.fimball.model.media.Animation;
import sep.fimball.model.media.BaseMediaElement;
import sep.fimball.model.media.BaseMediaElementEvent;
import sep.fimball.model.media.ElementImage;

import java.util.HashMap;
import java.util.Map;

import static sep.fimball.model.blueprint.json.JsonUtil.nullCheck;

/**
 * Diese Klasse enthält alle Informationen zu den Physik-Eigenschaften eines BaseElements.
 */
public class BaseMediaElementFactory
{
    /**
     * Generiert ein BaseMediaElement aus dem gegebenen MediaElementJson.
     *
     * @param mediaElement  Die Vorlage, aus der das BaseMediaElement erstellt wird.
     * @param baseElementId Die ID des zugehörigen BaseElements.
     * @return Das generierte BaseMediaElement.
     */
    static BaseMediaElement create(BaseElementJson.MediaElementJson mediaElement, String baseElementId)
    {
        nullCheck(mediaElement);
        nullCheck(mediaElement.general);
        nullCheck(mediaElement.general.editorName);
        nullCheck(mediaElement.general.editorDescription);
        nullCheck(baseElementId);

        String name = mediaElement.general.editorName;
        String description = mediaElement.general.editorDescription;
        boolean canRotate = mediaElement.general.canRotate;
        int rotationAccuracy = mediaElement.general.rotationAccuracy;

        Map<Integer, BaseMediaElementEvent> eventMap = new HashMap<>();
        if (mediaElement.events != null)
        {
            for (BaseElementJson.MediaElementJson.MediaElementEventJson event : mediaElement.events)
            {
                Animation animation = null;
                if (event.animation != null)
                {
                    nullCheck(event.animation.animationName);
                    animation = new Animation(event.animation.duration, event.animation.frameCount, event.animation.animationName);
                }

                nullCheck(event.soundName);
                eventMap.put(event.colliderId, new BaseMediaElementEvent(java.util.Optional.ofNullable(animation), event.soundName));
            }
        }

        Map<Integer, Vector2> localCoords = new HashMap<>();

        if (mediaElement.localCoordinates != null)
        {
            for (BaseElementJson.MediaElementJson.MediaElementLocalCoordinateJson localCoordinate : mediaElement.localCoordinates)
            {
                nullCheck(localCoordinate.localCoord);
                localCoords.put(localCoordinate.rotation, localCoordinate.localCoord);
            }
        }

        ElementImage elementImage;
        if (canRotate)
        {
            elementImage = new ElementImage(baseElementId, rotationAccuracy);
        }
        else
        {
            elementImage = new ElementImage(baseElementId);
        }

        return new BaseMediaElement(name, description, mediaElement.general.elementHeight, canRotate, rotationAccuracy, elementImage, eventMap, localCoords);
    }
}
