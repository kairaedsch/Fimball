package sep.fimball.model.blueprint;

import org.junit.Ignore;

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
        String[] elementTypeIds;
        BaseElementManager.getInstance().elementsProperty().keySet().toArray(elementTypeIds);

        for (int i = 0; i < elementTypeIds.length; i++)
        {
            pinballMachine.addElement(i, new PlacedElement(elementTypeIds[i], new Vector2(0, MAX_ELEMENT_SIZE * i));
        }

        //Den gerade erstellten Automaten serialisieren und speichern
        PinballMachineManager.getInstance().savePinballMachine(pinballMachine);

        //Den vorher gespeicherten Automaten neu laden
        PinballMachine loadedPinballMachine;
        for (PinballMachine machine : TestPinballMachineManager.getInstance().pinballMachinesProperty().get())
        {
            if (pinballMachine.getId().equals(machine.getId()))
            {
                loadedPinballMachine = machine;
            }
        }

        assert loadedPinballMachine ==null;

        //Vergleichen des erstellten und des geladenen Automaten
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

        assertFalse(difference);

        //Loeschen des vorher gespeicherten Automaten
        PinballMachineManager.getInstance().deleteMachine(pinballMachine);
    }

    class TestPinballMachineManager extends PinballMachineManager
    {
    }

}
