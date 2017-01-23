package sep.fimball.viewmodel.window.pinballmachine.editor;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import sep.fimball.general.data.RectangleDoubleByPoints;
import sep.fimball.general.data.Vector2;
import sep.fimball.model.blueprint.base.BaseElement;
import sep.fimball.model.blueprint.pinballmachine.PinballMachine;
import sep.fimball.model.blueprint.pinballmachine.PinballMachineUtil;
import sep.fimball.model.blueprint.pinballmachine.PlacedElement;

import java.util.*;

import static javafx.collections.FXCollections.observableArrayList;
import static sep.fimball.general.data.Config.MAX_EDITOR_SELECTION_AMOUNT;

/**
 * Das Model des Editors.
 */
public class PinballMachineEditor
{
    /**
     * Die aktuelle Auswahl.
     */
    private ListProperty<DraggedElement> selection;

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
    }

    void selectElement(Optional<DraggedElement> element, boolean additive)
    {
        if (additive)
        {
            if (element.isPresent())
            {
                if (selection.contains(element.get()))
                    removeFromSelection(element.get());
                else
                    addToSelection(element.get());
            }
        }
        else
        {
            if (!element.isPresent() || !selection.contains(element.get()))
            {
                clearSelection();
            }
            element.ifPresent(this::addToSelection);
        }
    }

    /**
     * Dupliziert die aktuelle Auswahl.
     */
    public void duplicateSelection()
    {
        List<DraggedElement> newSelection = new ArrayList<>();
        for (DraggedElement placedElement : selection)
        {
            PlacedElement placedElementCopy = placedElement.getPlacedElement().duplicate();
            placedElementCopy.setPosition(placedElementCopy.positionProperty().get().plus(new Vector2(2, -2)));
            pinballMachine.addElement(placedElementCopy);
            newSelection.add(new DraggedElement(placedElementCopy));
        }
        clearSelection();
        newSelection.forEach(this::addToSelection);
    }

    /**
     * Fügt die Auswahl dem Automaten hinzu und platziert diese.
     */
    void placeSelection()
    {
        for (DraggedElement placedElement : selection)
        {
            pinballMachine.addElement(placedElement.getPlacedElement());
        }
    }

    /**
     * Verschiebt die Elemente in der Auswahl um den gegebenen Vector.
     *
     * @param by Der Vektor, um den verschoben werden soll.
     */
    void moveSelectionBy(Vector2 by)
    {
        for (DraggedElement placedElement : selection)
        {
            placedElement.setAccuratePosition(placedElement.getAccuratePosition().plus(by));
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
            DraggedElement aPlacedElement = selection.iterator().next();

            Map<DraggedElement, Vector2> relativePos = new HashMap<>();
            relativePos.put(aPlacedElement, new Vector2(0, 0));

            if (selection.size() > 1)
            {
                for (DraggedElement draggedElement : selection)
                {
                    draggedElement.setAccuratePosition(draggedElement.getAccuratePosition().minus(draggedElement.getAccuratePosition()));
                    relativePos.put(draggedElement, draggedElement.getAccuratePosition());
                }
            }

            for (DraggedElement draggedElement : selection)
            {
                draggedElement.setAccuratePosition(to.plus(relativePos.get(draggedElement)));
            }
        }
    }

    /**
     * Dreht die Elemente in der Auswahl.
     */
    void rotateSelection()
    {
        if (!selection.isEmpty() && selection.stream().allMatch(p -> p.getPlacedElement().getBaseElement().getMedia().canRotate()))
        {
            double rotation = selection.stream()
                    .map(p -> p.getPlacedElement().getBaseElement().getMedia().getRotationAccuracy())
                    .reduce(0, (rA1, rA2) -> rA1 > rA2 ? rA1 : rA2);
            if (rotation == 0)
            {
                rotation = 90;
            }

            Vector2 mainPivotPoint = PinballMachineUtil.getBoundingBox(selection, t -> t.getPlacedElement()).getMiddle().round();
            for (DraggedElement placedElement : selection)
            {
                Vector2 pivotPoint = placedElement.getPlacedElement().getBaseElement().getPhysics().getPivotPoint();
                Vector2 newPos = placedElement.getPlacedElement().positionProperty().get().plus(pivotPoint).rotate(Math.toRadians(rotation), mainPivotPoint).minus(pivotPoint);
                placedElement.setAccuratePosition(newPos);
                placedElement.getPlacedElement().rotateClockwise();
            }
        }
    }

    /**
     * Löscht die Elemente in der Auswahl.
     */
    void removeSelection()
    {
        for (DraggedElement placedElement : selection)
        {
            pinballMachine.removeElement(placedElement.getPlacedElement());
        }
    }

    /**
     * Fügt ein Element zur Auswahl hinzu.
     *
     * @param placedElement Das Element, das hinzugefügt werden soll.
     */
    void addToSelection(PlacedElement placedElement)
    {
        addToSelection(new DraggedElement(placedElement));
    }

    /**
     * Fügt ein Element zur Auswahl hinzu.
     *
     * @param placedElement Das Element, das hinzugefügt werden soll.
     */
    void addToSelection(DraggedElement placedElement)
    {
        if (!selection.contains(placedElement) && selection.size() < MAX_EDITOR_SELECTION_AMOUNT)
        {
            selection.add(placedElement);
        }
    }

    /**
     * Fügt die Elemente zur Auswahl hinzu.
     *
     * @param placedElementList Die Elemente, die hinzugefügt werden sollen.
     */
    void addToSelection(List<PlacedElement> placedElementList)
    {
        if (placedElementList != null)
        {
            for (int i = 0; i < placedElementList.size() && selection.size() < MAX_EDITOR_SELECTION_AMOUNT; i++)
            {
                PlacedElement placedElement = placedElementList.get(i);
                selection.add(new DraggedElement(placedElement));
            }
        }
    }

    /**
     * Fügt ein zum Platzieren verfügbares Element der Auswahl hinzu.
     *
     * @param baseElement Das zum Platzieren verfügbares Element, das hinzugefügt werden soll.
     */
    void addToSelection(BaseElement baseElement)
    {
        selection.clear();
        addToSelection(new DraggedElement(new PlacedElement(baseElement, new Vector2(0, 0), 1, 1, 0)));
    }

    /**
     * Entfernt das Element aus der Auswahl.
     *
     * @param placedElement Das Element, das entfernt werden soll.
     */
    private void removeFromSelection(DraggedElement placedElement)
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
        ListProperty<PlacedElement> elements = new SimpleListProperty<>(observableArrayList());
        Optional<PlacedElement> element = pinballMachine.getElementAt(pos);
        element.ifPresent(elements::add);
        return elements;
    }

    /**
     * Gibt die Elemente, die in dem Rechteck liegen, zurück.
     *
     * @param rect Das Rechteck.
     * @return Die Elemente, die in dem Rechteck liegen.
     */
    List<PlacedElement> getElementsAt(RectangleDoubleByPoints rect)
    {
        return pinballMachine.getElementsAt(rect);
    }

    /**
     * Gibt die aktuelle Auswahl zurück.
     *
     * @return Die aktuelle Auswahl.
     */
    ReadOnlyListProperty<DraggedElement> getSelection()
    {
        return selection;
    }
}