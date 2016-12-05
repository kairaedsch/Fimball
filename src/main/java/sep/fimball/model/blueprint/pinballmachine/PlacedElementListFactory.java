package sep.fimball.model.blueprint.pinballmachine;

import sep.fimball.model.blueprint.base.BaseElement;
import sep.fimball.model.blueprint.base.BaseElementManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static sep.fimball.model.blueprint.json.JsonUtil.nullCheck;

/**
 * Created by kaira on 05.12.2016.
 */
class PlacedElementListFactory
{
    /**
     * Lädt die Elemente einer gegebenen PinballMachine.
     */
    static Optional<List<PlacedElement>> createPlacedElementList(Optional<PlacedElementListJson> placedElementListOptional)
    {
        try
        {
            if (!placedElementListOptional.isPresent()) throw new IllegalArgumentException();
            PlacedElementListJson placedElementListJson = placedElementListOptional.get();

            nullCheck(placedElementListJson.elements);
            List<PlacedElement> placedElements = new ArrayList<>();
            for (PlacedElementListJson.PlacedElementJson element : placedElementListJson.elements)
            {
                BaseElement baseElement = BaseElementManager.getInstance().getElement(element.baseElementId);
                if (baseElement != null)
                {
                    placedElements.add(new PlacedElement(baseElement, element.position, element.points, element.multiplier, element.rotation));
                }
                else
                {
                    System.err.println("Machine elem not loaded: baseElementId \"" + element.baseElementId + "\" does not exist");
                }
            }
            System.out.println("Machine elem loaded: (" + placedElements.size() + "/" + placedElementListJson.elements.length + ")");
            return Optional.of(placedElements);
        }
        catch (IllegalArgumentException e)
        {
            System.err.println("Machine elem not loaded");
            e.printStackTrace();
            return Optional.empty();
        }
    }

    static PlacedElementListJson createPlacedElementListJson(List<PlacedElement> placedElements)
    {
        PlacedElementListJson placedElementListJson = new PlacedElementListJson();

        placedElementListJson.elements = new PlacedElementListJson.PlacedElementJson[placedElements.size()];
        int counter = 0;
        for (PlacedElement placedElement : placedElements)
        {
            PlacedElementListJson.PlacedElementJson placedElementJson = new PlacedElementListJson.PlacedElementJson();
            placedElementJson.baseElementId = placedElement.getBaseElement().getId();
            placedElementJson.position = placedElement.positionProperty().getValue();
            placedElementJson.rotation = placedElement.rotationProperty().getValue();
            placedElementJson.points = placedElement.pointsProperty().getValue();
            placedElementJson.multiplier = placedElement.multiplierProperty().getValue();
            placedElementListJson.elements[counter] = placedElementJson;
            counter++;
        }

        return placedElementListJson;
    }
}
