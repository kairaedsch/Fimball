package sep.fimball.model.blueprint;

import org.junit.Test;
import sep.fimball.general.data.Vector2;
import sep.fimball.model.blueprint.base.BaseElement;
import sep.fimball.model.blueprint.base.BaseElementManager;
import sep.fimball.model.blueprint.pinballmachine.PinballMachine;
import sep.fimball.model.blueprint.pinballmachine.PinballMachineManager;
import sep.fimball.model.blueprint.pinballmachine.PlacedElement;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;

/**
 * Diese Klasse repräsentiert einen JUnit-Test, der einen neuen Automaten erstellt, in diesen Elemente einfügt, ihn speichert und anschließend den gespeicherten Automaten lädt, um diesen abschließend mit dem erstellten Automaten zu vergleichen und sicherzustellen, dass die beiden identisch sind.
 */
public class PinballMachineSaveTest
{
    /**
     * Gibt an um wie viel GridEinheiten verschoben die Elemente auf der Y-Achse platziert werden sollen.
     */
    private static final int ELEMENT_POSITION_OFFSET = 20;

    /**
     * Erstellt einen neuen Automaten, platziert jedes verfügbare Spielelement darin, speichert den erstellten Automaten und lädt ihn von der Festplatte. Anschließend wird sichergestellt, dass der geladene Automat mit dem erstellten Automat übereinstimmt.
     *
     * @throws IOException Exception die beim I/O der Serialisierung auftreten können.
     */
    @Test
    public void pinballMachineShouldSave() throws IOException
    {
        // Erstellt einen leeren Automaten, TODO dependency injection?
        PinballMachine pinballMachine = PinballMachineManager.getInstance().createNewMachine();
        List<PlacedElement> pinballMachineElements = new ArrayList<>();

        // Lädt alle im Spiel verfügbaren BaseElements und fügt sie in den Automaten ein, TODO test custom element?
        int i = 0;
        for (BaseElement b : BaseElementManager.getInstance().elementsProperty().values())
        {
            PlacedElement p = new PlacedElement(BaseElementManager.getInstance().getElement(b.getId()), new Vector2(0, ELEMENT_POSITION_OFFSET * i), 0, 1, 0);
            pinballMachine.addElement(p);
            pinballMachineElements.add(p);
            i++;
        }

        //Speichert den Automaten auf der Festplatte, wodurch die Element-Liste des Automaten geleert wird, und lädt die Elemente neu von der Festplatte durch Zugriff auf elementsProperty.
        pinballMachine.saveToDisk(true);
        List<PlacedElement> loadedElements = pinballMachine.elementsProperty().get();

        // Überprüft, ob beide Listen die gleichen Elemente enthalten
        assertTrue(pinballMachineElements.stream().allMatch((PlacedElement original) -> loadedElements.stream().anyMatch(original::identicalTo)));

        pinballMachine.deleteFromDisk();
    }
}
