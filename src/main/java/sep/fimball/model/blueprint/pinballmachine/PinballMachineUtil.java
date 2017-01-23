package sep.fimball.model.blueprint.pinballmachine;

import sep.fimball.general.data.RectangleDouble;
import sep.fimball.general.data.RectangleDoubleByPoints;
import sep.fimball.general.data.Vector2;

import java.util.*;
import java.util.function.Function;

/**
 * Die PinballMachineUtil ermöglicht das manipulieren von PlacedElements oder deren Auswertung.
 */
public class PinballMachineUtil
{
    /**
     * Gibt eine Box, die die übergebenen Elemente umschließt, zurück.
     *
     * @param elements Die übergebenen Elemente.
     * @return Eine Box, die die übergebenen Elemente umschließt.
     */
    public static <T>RectangleDouble getBoundingBox(Collection<T> elements, Function<T, PlacedElement> converter)
    {
        Vector2 max = elements.stream().map(element -> converter.apply(element).positionProperty().get().plus(converter.apply(element).getBaseElement().getPhysics().getExtremePos(converter.apply(element).rotationProperty().get(), true))).reduce(Vector2.NEGATIVE_INFINITY, Vector2::maxComponents);
        Vector2 origin = elements.stream().map(element -> converter.apply(element).positionProperty().get().plus(converter.apply(element).getBaseElement().getPhysics().getExtremePos(converter.apply(element).rotationProperty().get(), false))).reduce(Vector2.POSITIVE_INFINITY, Vector2::minComponents);

        double width = Math.abs(max.getX() - origin.getX());
        double height = Math.abs(max.getY() - origin.getY());
        return new RectangleDouble(origin, width, height);
    }

    /**
     * Gibt die Elemente zurück, die sich innerhalb der Vierecks befinden.
     *
     * @param rect Das Viereck.
     * @param elements Die übergebenen Elemente.
     * @return Die Elemente.
     */
    public static List<PlacedElement> getElementsAt(RectangleDoubleByPoints rect, Collection<PlacedElement> elements)
    {
        List<PlacedElement> matchingElements = new ArrayList<>();
        for (PlacedElement element : elements)
        {
            Vector2 elemPos = element.positionProperty().get();

            Vector2 relToOrigin = elemPos.minus(rect.getOrigin());
            Vector2 relToEnd = elemPos.minus(rect.getEnd());

            Vector2 maxExtremePos = element.getBaseElement().getPhysics().getExtremePos(element.rotationProperty().get(), true);
            Vector2 minExtremePos = element.getBaseElement().getPhysics().getExtremePos(element.rotationProperty().get(), false);

            if (relToOrigin.plus(maxExtremePos).getX() > 0 && relToOrigin.plus(maxExtremePos).getY() > 0 && relToEnd.plus(minExtremePos).getX() < 0 && relToEnd.plus(minExtremePos).getY() < 0)
            {
                matchingElements.add(element);
            }
        }
        return matchingElements;
    }

    /**
     * Überprüft, ob sich ein element an der gegebenen Position befindet. Falls dies der Fall ist wird es zurückgegeben.
     *
     * @param point Die Position.
     * @param elements Die übergebenen Elemente.
     * @return Das element an der gegebenen Position, fallse gefunden.
     */
    public static Optional<PlacedElement> getElementAt(Vector2 point, List<PlacedElement> elements)
    {
        for (int i = elements.size() - 1; i >= 0; i--)
        {
            PlacedElement element = elements.get(i);
            if (element.getBaseElement().getPhysics().checkIfPointIsInElement(element.rotationProperty().get(), point.minus(element.positionProperty().get())))
            {
                return Optional.of(element);
            }
        }
        return Optional.empty();
    }
}
