package sep.fimball.viewmodel.window.pinballmachine.editor;

import javafx.beans.property.ListProperty;
import javafx.beans.property.ReadOnlyListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import sep.fimball.general.data.RectangleDoubleOfPoints;
import sep.fimball.general.data.Vector2;
import sep.fimball.model.blueprint.base.BaseElement;
import sep.fimball.model.blueprint.pinballmachine.PinballMachine;
import sep.fimball.model.blueprint.pinballmachine.PlacedElement;

import java.util.*;

/**
 * Das Model des Editors.
 */
public class PinballMachineEditor
{
    /**
     * Die aktuelle Auswahl.
     */
    private ListProperty<PlacedElement> selection;

    /**
     * Die genauen Positionen von Elementen.
     */
    private Map<PlacedElement, Vector2> detailedPositions;

    /**
     * Der zugehörige Flipper-Automat.
     */
    private PinballMachine pinballMachine;

    /**
     * Erstellt einen neuen PinballMachineEditor.
     *
     * @param pinballMachine Der zugehörige Flipper-Automat.
     */
    PinballMachineEditor(PinballMachine pinballMachine)
    {
        this.pinballMachine = pinballMachine;
        selection = new SimpleListProperty<>(FXCollections.observableArrayList());
        detailedPositions = new HashMap<>();
    }

    /**
     * Fügt die Auswahl dem Automaten hinzu und platziert diese.
     */
    void placeSelection()
    {
        for (PlacedElement placedElement : selection)
        {
            pinballMachine.addElement(placedElement);
        }
    }

    /**
     * Gibt die Position des gegebenen Elements zurück.
     *
     * @param placedElement Das Element, dessen Position zurückgegeben werden soll.
     * @return Die Position des gegebenen Elements.
     */
    private Vector2 getPosition(PlacedElement placedElement)
    {
        if (!detailedPositions.containsKey(placedElement))
            detailedPositions.put(placedElement, placedElement.positionProperty().get());
        return detailedPositions.get(placedElement);
    }

    /**
     * Setzt die Position des Elements.
     *
     * @param placedElement Das Element, dessen Position gesetzt werden soll.
     * @param newPos        Die neue Position des Elements.
     */
    private void setPosition(PlacedElement placedElement, Vector2 newPos)
    {
        detailedPositions.put(placedElement, newPos);
        placedElement.setPosition(newPos.round());
    }

    /**
     * Verschiebt die Elemente in der Auswahl um den gegebenen Vector.
     *
     * @param by Der Vektor, um den verschoben werden soll.
     */
    void moveSelectionBy(Vector2 by)
    {
        for (PlacedElement placedElement : selection)
        {
            setPosition(placedElement, getPosition(placedElement).plus(by));
        }
    }

    /**
     * Verschiebt die Elemente in der Auswahl an die gegebene Position.
     *
     * @param to Die Position, an der die Elemente in der Auswahl verschoben werden sollen.
     */
    void moveSelectionTo(Vector2 to)
    {
        if (!selection.isEmpty())
        {

            Map<PlacedElement, Vector2> relativePos = new HashMap<>();
            relativePos.put(selection.get(0), new Vector2(0, 0));

            if (selection.size() > 1)
            {
                for (int i = 1; i < selection.size(); i++)
                {
                    relativePos.put(selection.get(i), getPosition(selection.get(i)).minus(getPosition(selection.get(0))));
                }
            }

            for (PlacedElement placedElement : selection)
            {
                setPosition(placedElement, to.plus(relativePos.get(placedElement)));
            }
        }
    }

    /**
     * Dreht die Elemente in der Auswahl.
     */
    void rotateSelection()
    {
        if (!selection.isEmpty() && selection.stream().allMatch(p -> p.getBaseElement().getMedia().canRotate()))
        {
            double rotation = selection.stream().map(p -> p.getBaseElement().getMedia().getRotationAccuracy()).reduce(0, (rA1, rA2) -> rA1 > rA2 ? rA1 : rA2);
            if (rotation == 0)
                rotation = 90;
            Vector2 mainPivotPoint = selection.get(0).positionProperty().get().plus(selection.get(0).getBaseElement().getPhysics().getPivotPoint());
            for (PlacedElement placedElement : selection)
            {
                Vector2 pivotPoint = placedElement.getBaseElement().getPhysics().getPivotPoint();
                Vector2 newPos = placedElement.positionProperty().get().plus(pivotPoint).rotate(Math.toRadians(rotation), mainPivotPoint).minus(pivotPoint);
                setPosition(placedElement, newPos);
                placedElement.rotateClockwise();
            }
        }
    }

    /**
     * Löscht die Elemente in der Auswahl.
     */
    void removeSelection()
    {
        for (PlacedElement placedElement : selection)
        {
            pinballMachine.removeElement(placedElement);
        }
    }

    /**
     * Fügt ein Element zur Auswahl hinzu.
     *
     * @param placedElement Das Element, das hinzugefügt werden soll.
     */
    void addToSelection(PlacedElement placedElement)
    {
        if (!selection.contains(placedElement))
        {
            setPosition(placedElement, getPosition(placedElement).round());
            selection.add(placedElement);
        }
    }

    /**
     * Fügt die Elemente zur Auswahl hinzu.
     *
     * @param placedElementList Die Elemente, die hinzugefügt werden sollen.
     */
    void addToSelection(ListProperty<PlacedElement> placedElementList)
    {
        if (placedElementList != null)
        {
            placedElementList.forEach(this::addToSelection);
        }
    }

    /**
     * Fügt ein zum Platzieren verfügbares Element der Auswahl hinzu.
     *
     * @param baseElement Das zum Platzieren verfügbares Element, das hinzugefügt werden soll.
     */
    void addToSelection(BaseElement baseElement)
    {
        // TODO correct points and multiplier in placedElement
        selection.clear();
        addToSelection(new PlacedElement(baseElement, new Vector2(0, 0), 0, 0, 0));
    }

    /**
     * Entfernt das Element aus der Auswahl.
     *
     * @param placedElement Das Element, das entfernt werden soll.
     */
    void removeFromSelection(PlacedElement placedElement)
    {
        selection.remove(placedElement);
    }

    /**
     * Löscht die Auswahl.
     */
    void clearSelection()
    {
        selection.clear();
    }

    /**
     * Gibt die Elemente an der gegebenen Position zurück.
     *
     * @param pos Die Position.
     * @return Die Elemente an der gegebenen Position.
     */
    ReadOnlyListProperty<PlacedElement> getElementsAt(Vector2 pos)
    {
        ListProperty<PlacedElement> elements = new SimpleListProperty<>(FXCollections.observableArrayList());
        Optional<PlacedElement> element = pinballMachine.getElementAt(pos);
        if (element.isPresent())
        {
            elements.add(element.get());
        }
        return elements;
    }

    /**
     * Gibt die Elemente, die in dem Rechteck liegen, zurück.
     *
     * @param rect Das Rechteck.
     * @return Die Elemente, die in dem Rechteck liegen.
     */
    ReadOnlyListProperty<PlacedElement> getElementsAt(RectangleDoubleOfPoints rect)
    {
        return pinballMachine.getElementsAt(rect);
    }

    /**
     * Gibt die aktuelle Auswahl zurück.
     *
     * @return Die aktuelle Auswahl.
     */
    ReadOnlyListProperty<PlacedElement> getSelection()
    {
        return selection;
    }

    /**
     * Dupliziert die aktuelle Auswahl.
     */
    public void duplicateSelection()
    {
        List<PlacedElement> newSelection = new ArrayList<>();
        for (PlacedElement placedElement : selection)
        {
            PlacedElement placedElementCopy = placedElement.duplicate();
            placedElementCopy.setPosition(placedElementCopy.positionProperty().get().plus(new Vector2(2, -2)));
            pinballMachine.addElement(placedElementCopy);
            newSelection.add(placedElementCopy);
        }
        clearSelection();
        newSelection.forEach(this::addToSelection);
    }
}