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
    private PinballMachine pinballMachine;

    public PinballMachineEditor(PinballMachine pinballMachine)
    {
        this.pinballMachine = pinballMachine;
        selection = new SimpleListProperty<>(FXCollections.observableArrayList());
    }

    public void placeSelection()
    {
        for (PlacedElement placedElement : selection)
        {
            pinballMachine.elementsProperty().get().add(placedElement);
        }
    }

    public void moveSelectionBy(Vector2 by)
    {
        for (PlacedElement placedElement : selection)
        {
            ObjectProperty<Vector2> positionProperty = (ObjectProperty<Vector2>) placedElement.positionProperty();
            positionProperty.set(positionProperty.get().plus(by));
        }
    }

    public void moveSelectionTo(Vector2 to)
    {
        // TODO if selection size > 1 store localPos and apply after movement
        for (PlacedElement placedElement : selection)
        {
            ObjectProperty<Vector2> positionProperty = (ObjectProperty<Vector2>) placedElement.positionProperty();
            positionProperty.setValue(to);
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
            pinballMachine.elementsProperty().get().remove(placedElement);
        }
    }

    public void addToSelection(PlacedElement placedElement)
    {
        selection.add(placedElement);
    }

    public void addToSelection(ListProperty<PlacedElement> placedElementList)
    {
        if (placedElementList != null)
        {
            selection.addAll(placedElementList);
        }
    }

    public void addToSelection(BaseElement baseElement)
    {
        // TODO correct points and multiplier in placedElement
        selection.clear();
        selection.add(new PlacedElement(baseElement, new Vector2(0, 0), 0, 0, 0));
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
        // TODO
        return null;
    }

    public ReadOnlyListProperty<PlacedElement> getSelection()
    {
        return selection;
    }
}