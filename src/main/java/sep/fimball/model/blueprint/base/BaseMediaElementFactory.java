package sep.fimball.model.blueprint.base;

import sep.fimball.general.data.Language;
import sep.fimball.general.data.Vector2;
import sep.fimball.model.media.Animation;
import sep.fimball.model.media.BaseMediaElement;
import sep.fimball.model.media.BaseMediaElementEvent;
import sep.fimball.model.media.ElementImage;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static sep.fimball.model.blueprint.json.JsonUtil.nullCheck;

/**
 * Diese Klasse ist für die Erstellung des BaseMediaElement eines BaseElement zuständig.
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
        nullCheck(baseElementId);
        nullCheck(mediaElement.descriptions);

        Map<Language, String> names = new HashMap<>();
        Map<Language, String> descriptions = new HashMap<>();

        for (BaseElementJson.MediaElementJson.MediaElementDescriptionJson descriptionJson : mediaElement.descriptions)
        {
            names.put(descriptionJson.language, descriptionJson.editorName);
            descriptions.put(descriptionJson.language, descriptionJson.editorDescription);
        }

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

                eventMap.put(event.colliderId, new BaseMediaElementEvent(java.util.Optional.ofNullable(animation), Optional.ofNullable(event.soundName)));
            }
        }

        Map<Integer, Vector2> localCoords = new HashMap<>();

        if (mediaElement.localCoordinates != null)
        {
            for (BaseElementJson.MediaElementJson.MediaElementLocalCoordinateJson localCoord : mediaElement.localCoordinates)
            {
                nullCheck(localCoord.localCoord);
                localCoords.put(localCoord.rotation, localCoord.localCoord);
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

        return new BaseMediaElement(names, descriptions, mediaElement.general.elementHeight, canRotate, rotationAccuracy, elementImage, eventMap, localCoords);
    }
}
