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
    private Vector2 selectionPivot;
    private Map<PlacedElement, Vector2> localPositions;
    private PinballMachine pinballMachine;

    public PinballMachineEditor(PinballMachine pinballMachine)
    {
        this.pinballMachine = pinballMachine;
        selection = new SimpleListProperty<>(FXCollections.observableArrayList());
        selectionPivot = new Vector2(0, 0);
        localPositions = new HashMap<>();

        selection.addListener((observableValue, placedElements, t1) ->
        {
            double sumX = 0;
            double sumY = 0;
            for (PlacedElement placedElement : selection)
            {
                sumX += placedElement.positionProperty().get().getX();
                sumY += placedElement.positionProperty().get().getX();
            }
            selectionPivot = new Vector2(Math.round(sumX / selection.size()), Math.round(sumY / selection.size()));

            for (PlacedElement placedElement : selection)
            {
                Vector2 localPos = placedElement.positionProperty().get().minus(selectionPivot);
                localPositions.put(placedElement, localPos);

                System.out.print(placedElement.getBaseElement().getMedia().getName() + " ");
            }
            System.out.println();
        });
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
        for (PlacedElement placedElement : selection)
        {
            ObjectProperty<Vector2> positionProperty = (ObjectProperty<Vector2>) placedElement.positionProperty();
            positionProperty.setValue(to.plus(localPositions.get(placedElement)));
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
            if (pinballMachine.elementsProperty().get().remove(placedElement)) ;
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
        // TODO
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
