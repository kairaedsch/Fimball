package sep.fimball.model.blueprint;

import javafx.beans.property.ReadOnlyListProperty;
import org.junit.Ignore;
import org.junit.Test;
import sep.fimball.general.data.Config;
import sep.fimball.general.data.Vector2;
import sep.fimball.model.blueprint.base.BaseElement;
import sep.fimball.model.blueprint.base.BaseElementManager;
import sep.fimball.model.blueprint.pinballmachine.PinballMachine;
import sep.fimball.model.blueprint.pinballmachine.PinballMachineManager;
import sep.fimball.model.blueprint.pinballmachine.PlacedElement;

import static junit.framework.TestCase.assertFalse;

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
        Config.config();
        PinballMachine pinballMachine = PinballMachineManager.getInstance().createNewMachine();
        ReadOnlyListProperty<PlacedElement> originalElements = pinballMachine.getElements();
        int i = 0;
        for (BaseElement b : BaseElementManager.getInstance().elementsProperty().values())
        {
            PlacedElement p = new PlacedElement(BaseElementManager.getInstance().getElement(b.getId()), new Vector2(0, MAX_ELEMENT_SIZE * i), 0, 1, 0);
            pinballMachine.addElement(p);
            originalElements.add(p);
            i++;
        }

        //Den gerade erstellten Automaten serialisieren und speichern
        pinballMachine.saveToDisk();

        //Den gespeicherten Automaten neu laden
        pinballMachine.checkUnloadElements();
        ReadOnlyListProperty<PlacedElement> loadedElements = pinballMachine.getElements();

        //Vergleichen des erstellten und des geladenen Automaten
        boolean difference = false;
        for (PlacedElement original : originalElements)
        {
            boolean match = false;
            for (PlacedElement loaded : loadedElements)
            {
                if (original.equals(loaded))
                {
                    match = true;
                }
            }

            if (!match)
            {
                difference = true;
            }
        }

        assertFalse(difference);

        //Loeschen des vorher gespeicherten Automaten
        pinballMachine.deleteFromDisk();
    }

}
