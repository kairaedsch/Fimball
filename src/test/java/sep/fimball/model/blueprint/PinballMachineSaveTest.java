package sep.fimball.model.blueprint;

import javafx.beans.property.ReadOnlyMapProperty;
import org.junit.Ignore;
import org.junit.Test;
import sep.fimball.general.data.Vector2;
import sep.fimball.model.blueprint.base.BaseElementManager;
import sep.fimball.model.blueprint.pinballmachine.PinballMachine;
import sep.fimball.model.blueprint.pinballmachine.PinballMachineManager;
import sep.fimball.model.blueprint.pinballmachine.PlacedElement;

import static org.junit.Assert.assertFalse;

/**
 *
 */
@Ignore
public class PinballMachineSaveTest
{
    private static final int MAX_ELEMENT_SIZE = 20;

    @Test
    public void pinballMachineShouldSave()
    {
        //Erstellen eines neuen Automaten, der alle verschiedene Elemente enthaelt
        PinballMachine pinballMachine = PinballMachineManager.getInstance().createNewMachine();
        String[] elementTypeIds = null;
        BaseElementManager.getInstance().elementsProperty().keySet().toArray(elementTypeIds);

        for (int i = 0; i < elementTypeIds.length; i++)
        {
            //pinballMachine.addElement(i, new PlacedElement(elementTypeIds[i], new Vector2(0, MAX_ELEMENT_SIZE * i)));
        }

        //Den gerade erstellten Automaten serialisieren und speichern
        PinballMachineManager.getInstance().savePinballMachine(pinballMachine);

        //Den vorher gespeicherten Automaten neu laden
        PinballMachine loadedPinballMachine = null;
        /*for (PinballMachine machine : TestPinballMachineManager.getInstance().pinballMachinesProperty().get())
        {
            if (pinballMachine.getID().equals(machine.getID()))
            {
                loadedPinballMachine = machine;
            }
        }*/

        assert loadedPinballMachine ==null;

        /*//Vergleichen des erstellten und des geladenen Automaten
        //TODO vllt pinballmachine.equals(loadedPinballMachine), da name etc noch nicht verglichen werden
        ReadOnlyMapProperty<Integer, PlacedElement> elementsPropertyOriginal = pinballMachine.getElements().elementsProperty();
        ReadOnlyMapProperty<Integer, PlacedElement> elementsPropertyLoaded = pinballMachine.getElements().elementsProperty();

        boolean difference = false;
        for (Integer key : pinballMachine.getElements().elementsProperty().keySet())
        {
            PlacedElement original = elementsPropertyOriginal.valueAt(key).get();
            PlacedElement loaded = elementsPropertyLoaded.valueAt(key).get();

            assert original !=null;

            if (!original.equals(loaded))
            {
                difference = true;
            }
        }

        assertFalse(difference);*/

        //Loeschen des vorher gespeicherten Automaten
        PinballMachineManager.getInstance().deleteMachine(pinballMachine);
    }

    class TestPinballMachineManager
    {
    }

}
