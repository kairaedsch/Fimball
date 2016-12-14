package sep.fimball.viewmodel.window.pinballmachine.editor;

import javafx.beans.property.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sep.fimball.general.data.RectangleDouble;
import sep.fimball.general.data.Vector2;
import sep.fimball.model.blueprint.base.BaseElement;
import sep.fimball.model.blueprint.pinballmachine.PinballMachine;
import sep.fimball.model.blueprint.pinballmachine.PlacedElement;

import java.util.*;


public class PinballMachineEditor
{
    private ListProperty<PlacedElement> selection;

    private Map<PlacedElement, Vector2> detailedPositions;

    private PinballMachine pinballMachine;

    public PinballMachineEditor(PinballMachine pinballMachine)
    {
        this.pinballMachine = pinballMachine;
        selection = new SimpleListProperty<>(FXCollections.observableArrayList());
        detailedPositions = new HashMap<>();
    }

    public void placeSelection()
    {
        for (PlacedElement placedElement : selection)
        {
            pinballMachine.addElement(placedElement);
        }
    }

    private Vector2 getPosition(PlacedElement placedElement)
    {
        if(!detailedPositions.containsKey(placedElement)) detailedPositions.put(placedElement, placedElement.positionProperty().get());
        return detailedPositions.get(placedElement);
    }

    private void setPosition(PlacedElement placedElement, Vector2 newPos)
    {
        detailedPositions.put(placedElement, newPos);
        placedElement.setPosition(newPos.round());
    }

    public void moveSelectionBy(Vector2 by)
    {
        for (PlacedElement placedElement : selection)
        {
            setPosition(placedElement, getPosition(placedElement).plus(by));
        }
    }

    public void moveSelectionTo(Vector2 to)
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

    public void rotateSelection()
    {
        //TODO
    }

    public void removeSelection()
    {
        for (PlacedElement placedElement : selection)
        {
            pinballMachine.removeElement(placedElement);
        }
    }

    public void addToSelection(PlacedElement placedElement)
    {
        if(!selection.contains(placedElement)) selection.add(placedElement);
    }

    public void addToSelection(ListProperty<PlacedElement> placedElementList)
    {
        if (placedElementList != null)
        {
            for (PlacedElement placedElement : placedElementList)
            {
                addToSelection(placedElement);
            }
        }
    }

    public void addToSelection(BaseElement baseElement)
    {
        // TODO correct points and multiplier in placedElement
        selection.clear();
        addToSelection(new PlacedElement(baseElement, new Vector2(0, 0), 0, 0, 0));
    }

    public void removeFromSelection(PlacedElement placedElement)
    {
        selection.remove(placedElement);
    }

    public void clearSelection()
    {
        selection.clear();
    }

    public ReadOnlyListProperty<PlacedElement> getElementsAt(Vector2 pos)
    {
        ListProperty<PlacedElement> elements = new SimpleListProperty<>(FXCollections.observableArrayList());
        Optional<PlacedElement> element = pinballMachine.getElementAt(pos);
        if (element.isPresent())
        {
            elements.add(element.get());
        }
        return elements;
    }

    public ReadOnlyListProperty<PlacedElement> getElementsAt(RectangleDouble rect)
    {
        return pinballMachine.getElementsAt(rect);
    }

    public ReadOnlyListProperty<PlacedElement> getSelection()
    {
        return selection;
    }
}