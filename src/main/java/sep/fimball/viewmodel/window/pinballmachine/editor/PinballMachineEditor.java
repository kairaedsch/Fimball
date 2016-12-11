package sep.fimball.viewmodel.window.pinballmachine.editor;

import javafx.beans.property.ListProperty;
import javafx.beans.property.ReadOnlyListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import sep.fimball.general.data.RectangleDouble;
import sep.fimball.general.data.Vector2;
import sep.fimball.model.blueprint.pinballmachine.PinballMachine;
import sep.fimball.model.blueprint.pinballmachine.PlacedElement;


public class PinballMachineEditor
{
    ListProperty<PlacedElement> selection;
    PinballMachine pinballMachine;

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

    public void moveSelection(Vector2 by)
    {
        for (PlacedElement placedElement : selection)
        {
            placedElement.positionProperty().get().plus(by);
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
        return null;
    }

    public ReadOnlyListProperty<PlacedElement> getElementsAt(RectangleDouble rect)
    {
        // TODO
        return null;
    }

}
