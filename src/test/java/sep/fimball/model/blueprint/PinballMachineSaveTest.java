package sep.fimball.model.blueprint;

import javafx.beans.property.ReadOnlyListProperty;
import org.junit.Test;
import sep.fimball.general.data.Vector2;
import sep.fimball.model.blueprint.base.BaseElement;
import sep.fimball.model.blueprint.base.BaseElementManager;
import sep.fimball.model.blueprint.pinballmachine.PinballMachine;
import sep.fimball.model.blueprint.pinballmachine.PinballMachineManager;
import sep.fimball.model.blueprint.pinballmachine.PlacedElement;

import static org.junit.Assert.assertTrue;

/**
 *
 */
public class PinballMachineSaveTest
{
    private static final int MAX_ELEMENT_SIZE = 20;

    @Test
    public void pinballMachineShouldSave()
    {
        // Erstellt einen leeren Automaten
        PinballMachine pinballMachine = PinballMachineManager.getInstance().createNewMachine();
        ReadOnlyListProperty<PlacedElement> pinballMachineElements = pinballMachine.elementsProperty();

        // Lädt alle im Spiel verfügbaren BaseElements und fügt sie in den Automaten ein
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

        // Bei erneutem Zugriff auf die Property werden die Elemente neu geladen
        ReadOnlyListProperty<PlacedElement> loadedElements = pinballMachine.elementsProperty();

        // Überprüft, ob beide Listen die gleichen Elemente enthalten
        assertTrue(pinballMachineElements.stream().allMatch((PlacedElement original)->loadedElements.stream().anyMatch(original::equals)));

        // Löscht den Automaten
        pinballMachine.deleteFromDisk();
    }
}
