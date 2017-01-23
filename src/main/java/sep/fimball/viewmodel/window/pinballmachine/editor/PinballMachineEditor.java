package sep.fimball.viewmodel.window.pinballmachine.editor;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sep.fimball.general.data.RectangleDoubleByPoints;
import sep.fimball.general.data.Vector2;
import sep.fimball.model.blueprint.base.BaseElement;
import sep.fimball.model.blueprint.pinballmachine.PinballMachine;
import sep.fimball.model.blueprint.pinballmachine.PinballMachineUtil;
import sep.fimball.model.blueprint.pinballmachine.PlacedElement;
import sep.fimball.model.game.DraggedElement;

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
    private Set<PlacedElement> selection;

    /**
     * Die aktuelle Auswahl.
     */
    private IntegerProperty selectionSize;

    /**
     * Die genauen Positionen von Elementen.
     */
    private ObservableList<DraggedElement> detailedPositions;

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
        selection = new HashSet<>();
        selectionSize = new SimpleIntegerProperty(0);
        detailedPositions = FXCollections.observableArrayList();
    }

    void selectElement(Optional<PlacedElement> element, boolean additive)
    {
        if (additive && element.isPresent())
        {
            if (selection.contains(element.get()))
                removeFromSelection(element.get());
            else
                addToSelection(element.get());
        }
        else
        {
            if (!additive && (!element.isPresent() || !selection.contains(element.get())))
                clearSelection();
            element.ifPresent(this::addToSelection);
        }
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
            PlacedElement aPlacedElement = selection.iterator().next();

            Map<PlacedElement, Vector2> relativePos = new HashMap<>();
            relativePos.put(aPlacedElement, new Vector2(0, 0));

            if (selection.size() > 1)
            {
                for (PlacedElement placedElement : selection)
                {
                    relativePos.put(placedElement, getPosition(placedElement).minus(getPosition(aPlacedElement)));
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
            double rotation = selection.stream()
                    .map(p -> p.getBaseElement().getMedia().getRotationAccuracy())
                    .reduce(0, (rA1, rA2) -> rA1 > rA2 ? rA1 : rA2);
            if (rotation == 0)
            {
                rotation = 90;
            }

            Vector2 mainPivotPoint = PinballMachineUtil.getBoundingBox(selection).getMiddle().round();
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
        if (!selection.contains(placedElement) && selection.size() < MAX_EDITOR_SELECTION_AMOUNT)
        {
            setPosition(placedElement, getPosition(placedElement).round());
            selection.add(placedElement);
            selectionSize.set(selection.size());
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
                setPosition(placedElement, getPosition(placedElement).round());
                selection.add(placedElement);
            }
            selectionSize.set(selection.size());
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
        selectionSize.set(selection.size());
        addToSelection(new PlacedElement(baseElement, new Vector2(0, 0), 1, 1, 0));
    }

    /**
     * Entfernt das Element aus der Auswahl.
     *
     * @param placedElement Das Element, das entfernt werden soll.
     */
    private void removeFromSelection(PlacedElement placedElement)
    {
        selection.remove(placedElement);
        selectionSize.set(selection.size());
    }

    /**
     * Löscht die Auswahl.
     */
    void clearSelection()
    {
        selection.clear();
        selectionSize.set(selection.size());
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
    Set<PlacedElement> getSelection()
    {
        return selection;
    }

    /**
     * Gibt die Position des gegebenen Elements zurück.
     *
     * @param placedElement Das Element, dessen Position zurückgegeben werden soll.
     * @return Die Position des gegebenen Elements.
     */
    private Vector2 getPosition(PlacedElement placedElement)
    {
        for (DraggedElement elem : detailedPositions)
        {
            if (elem.getPlacedElement().equals(placedElement))
                return elem.getAccuratePosition();
        }

        detailedPositions.add(new DraggedElement(placedElement));
        return placedElement.positionProperty().get();
    }

    /**
     * Setzt die Position des Elements.
     *
     * @param placedElement Das Element, dessen Position gesetzt werden soll.
     * @param newPos        Die neue Position des Elements.
     */
    private void setPosition(PlacedElement placedElement, Vector2 newPos)
    {
        placedElement.setPosition(newPos.round());
        for (DraggedElement elem : detailedPositions)
        {
            if (elem.getPlacedElement().equals(placedElement))
            {
                elem.setAccuratePosition(newPos);
                return;
            }
        }
        detailedPositions.add(new DraggedElement(placedElement, newPos));
    }

    public IntegerProperty selectionSizeProperty()
    {
        return selectionSize;
    }

    public ObservableList<DraggedElement> getDetailedPositions()
    {
        return detailedPositions;
    }
}