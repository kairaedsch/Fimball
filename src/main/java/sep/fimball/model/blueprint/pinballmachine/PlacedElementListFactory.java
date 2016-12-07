package sep.fimball.model.blueprint.pinballmachine;

import sep.fimball.model.blueprint.base.BaseElement;
import sep.fimball.model.blueprint.base.BaseElementManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static sep.fimball.model.blueprint.json.JsonUtil.nullCheck;

/**
 * Die PlacedElementListFactory kümmert sich um die Umwandlung zwischen Listen von PlacedElements und PlacedElementListJson.
 */
class PlacedElementListFactory
{
    /**
     * Wandelt eine PlacedElementListJson in eine Liste von PlacedElements um.
     *
     * @param placedElementListOptional Die PlacedElementListJson aus der die Liste von PlacedElements erstellt werden soll.
     * @return Eine Liste von PlacedElements aus der {@code placedElementListOptional}.
     */
    static Optional<List<PlacedElement>> createPlacedElementList(Optional<PlacedElementListJson> placedElementListOptional)
    {
        try
        {
            if (!placedElementListOptional.isPresent()) throw new IllegalArgumentException();
            PlacedElementListJson placedElementListJson = placedElementListOptional.get();

            // Versuche alle Elemente des Automaten einzulesen. Dabei werden nicht einlesbare Elemente ignoriert
            nullCheck(placedElementListJson.elements);
            List<PlacedElement> placedElements = new ArrayList<>();
            for (PlacedElementListJson.PlacedElementJson element : placedElementListJson.elements)
            {
                boolean success = false;
                if (element != null)
                {
                    BaseElement baseElement = BaseElementManager.getInstance().getElement(element.baseElementId);
                    if (baseElement != null)
                    {
                        placedElements.add(new PlacedElement(baseElement, element.position, element.points, element.multiplier, element.rotation));
                        success = true;
                    }
                }
                if (!success) System.err.println("Machine elem not loaded: baseElementId \"" + element.baseElementId + "\" does not exist");
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

    /**
     * Wandelt eine Liste von PlacedElements in ein PlacedElementListJson um.
     *
     * @param placedElements Die List von PlacedElements aus der die PlacedElementListJson erstellt werden soll.
     * @return Eine PlacedElementListJson aus der {@code placedElementListOptional}.
     */
    static PlacedElementListJson createPlacedElementListJson(List<PlacedElement> placedElements)
    {
        PlacedElementListJson placedElementListJson = new PlacedElementListJson();

        // Wandelt alle Elemente um
        placedElementListJson.elements = new PlacedElementListJson.PlacedElementJson[placedElements.size()];
        int pos = 0;
        for (PlacedElement placedElement : placedElements)
        {
            PlacedElementListJson.PlacedElementJson placedElementJson = new PlacedElementListJson.PlacedElementJson();
            placedElementJson.baseElementId = placedElement.getBaseElement().getId();
            placedElementJson.position = placedElement.positionProperty().getValue();
            placedElementJson.rotation = placedElement.rotationProperty().getValue();
            placedElementJson.points = placedElement.pointsProperty().getValue();
            placedElementJson.multiplier = placedElement.multiplierProperty().getValue();
            placedElementListJson.elements[pos] = placedElementJson;
            pos++;
        }

        return placedElementListJson;
    }
}
