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
        Config.config();
        // Erstellt einen leeren Automaten
        PinballMachine pinballMachine = PinballMachineManager.getInstance().createNewMachine();
        ReadOnlyListProperty<PlacedElement> pinballMachineElements = pinballMachine.elementsProperty();

        // Lädt alle im Spiel verfügbaren BaseElements und fügt sie in den Auotmaten ein
        int i = 0;
        for (BaseElement b : BaseElementManager.getInstance().elementsProperty().values())
        {
            PlacedElement p = new PlacedElement(BaseElementManager.getInstance().getElement(b.getId()), new Vector2(0, MAX_ELEMENT_SIZE * i), 0, 1, 0);
            pinballMachine.addElement(p);
            pinballMachineElements.add(p);
            i++;
        }

        // Speichert den erstellten Automaten
        pinballMachine.saveToDisk();

        // Leert den aktuellen Automaten
        pinballMachine.unloadElements();
        ReadOnlyListProperty<PlacedElement> loadedElements = pinballMachine.elementsProperty();

        //Vergleichen des erstellten und des geladenen Automaten
        boolean difference = false;
        for (PlacedElement original : pinballMachineElements)
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
