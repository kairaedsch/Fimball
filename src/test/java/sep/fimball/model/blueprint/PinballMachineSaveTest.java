package sep.fimball.model.blueprint;

import org.junit.Ignore;

/**
 *
 */
@Ignore
public class PinballMachineSaveTest
{
    private static final int MAX_ELEMENT_SIZE = 20;
/*
    @Test
    public void pinballMachineShouldSave()
    {
        Integer pinballMachineId = PinballMachineManager.getInstance().createNew();
        PinballMachine pinballMachine = PinballMachineManager.getInstance().tableBlueprintsProperty().valueAt(pinballMachineId).get();
        String[] elementTypeIds;
        BaseElementManager.getInstance().elementsProperty().keySet().toArray(elementTypeIds);
        PlacedElementList tableElementList = pinballMachine.getTableElementList();

        for (int i = 0; i < elementTypeIds.length; i++)
        {
            tableElementList.addElement(i, new PlacedElement(elementTypeIds[i], new Vector2(0, MAX_ELEMENT_SIZE * i));
        }

        PinballMachineManager.getInstance().save(pinballMachineId);

        //TODO load

        PinballMachine loadedPinballMachine;

        ReadOnlyMapProperty<Integer, PlacedElement> elementsPropertyOriginal = pinballMachine.getTableElementList().elementsProperty();
        ReadOnlyMapProperty<Integer, PlacedElement> elementsPropertyLoaded = pinballMachine.getTableElementList().elementsProperty();

        boolean difference = false;
        for (Integer key : pinballMachine.getTableElementList().elementsProperty().keySet())
        {
            PlacedElement original = elementsPropertyOriginal.valueAt(key).get();
            PlacedElement loaded = elementsPropertyLoaded.valueAt(key).get();

            assert original != null;

            if (!original.equals(loaded))
            {
                difference = true;
            }
        }

    }
*/
}
