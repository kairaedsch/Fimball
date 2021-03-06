package sep.fimball;

import org.junit.Ignore;
import org.junit.Test;
import sep.fimball.general.data.Vector2;
import sep.fimball.model.blueprint.base.BaseElementManager;
import sep.fimball.model.blueprint.pinballmachine.PinballMachine;
import sep.fimball.model.blueprint.pinballmachine.PinballMachineManager;
import sep.fimball.model.blueprint.pinballmachine.PlacedElement;

import java.util.ArrayList;

/**
 * Diese Klasse enthält Methoden zur Generierung von Automaten für Skalierungs- und Belastungstests.
 */
@Ignore
public class TestMachineGenerator
{
    /**
     * Generiert eine Maschine mit einer variablen Anzahl von Bahnelementen um die Skalierbarkeit der Bahnelemente zu testen.
     */
    @Test
    public void generateScalabilityTestMachine()
    {
        int[] sizes = {16, 32, 64, 128, 256, 512};
        final int distance = 20;

        for (int size : sizes)
        {
            PinballMachine pinballMachine = PinballMachineManager.getInstance().createNewMachine();

            pinballMachine.nameProperty().setValue("Test - Scalability " + size + "*" + size);

            for (int x = 0; x < size; x++)
            {
                for (int y = 0; y < size; y++)
                {
                    String[] keys = new String[BaseElementManager.getInstance().elementsProperty().getSize()];
                    BaseElementManager.getInstance().elementsProperty().get().keySet().toArray(keys);
                    int index = (int) Math.floor(keys.length * Math.random());
                    String key = keys[index];

                    if (!key.equals("ball"))
                    {
                        pinballMachine.addElement(BaseElementManager.getInstance().getElement(key), new Vector2(x * distance - (size * distance / 2), y * distance));
                    }
                }
            }

            pinballMachine.saveToDisk(true, true);
        }
    }

    /**
     * Generiert eine Maschine mit einer großen Anzahl von Lichtern um die Skalierbarkeit der Lichter zu testen.
     */
    @Test
    public void generateScalabilityLightMachine()
    {
        final int size = 100;
        final int distance = 5;

        PinballMachine pinballMachine = PinballMachineManager.getInstance().createNewMachine();
        pinballMachine.nameProperty().set("Test - Light Show " + size);
        pinballMachine.addElement(new PlacedElement(BaseElementManager.getInstance().getElement("hinderniss_linie_2"), new Vector2(1, 30), 0, 0, 0));

        ArrayList<PlacedElement> list = new ArrayList<>();

        for (int x = 0; x < size; x++)
        {
            for (int y = 0; y < size; y++)
            {
                pinballMachine.addElement(new PlacedElement(BaseElementManager.getInstance().getElement("light_green"), new Vector2(x * distance - (size * distance / 2), y * distance), 0, 0, 0));
            }
        }
        pinballMachine.saveToDisk(true, true);
    }

    /**
     * Generiert eine Maschine mit einer variablen Anzahl von gleichen Elementen um die Skalierbarkeit der Kollisionsüberprüfung zu testen.
     */
    @Test
    public void generateScalabilityCollisionTestMachine()
    {
        int[] sizes = {32, 64, 128, 256, 512, 1024};

        for (int size : sizes)
        {
            PinballMachine pinballMachine = PinballMachineManager.getInstance().createNewMachine();

            pinballMachine.nameProperty().setValue("Test - Scalability Collision " + size);

            pinballMachine.addElement(new PlacedElement(BaseElementManager.getInstance().getElement("hinderniss_linie_2"), new Vector2(1, 8), 0, 0, 0));

            for (int i = 0; i < size; i++)
            {
                PlacedElement element = new PlacedElement(BaseElementManager.getInstance().getElement("speed"), new Vector2(0, 6), 1, 0, 0);
                pinballMachine.addElement(element);
            }

            pinballMachine.saveToDisk(true, true);
        }
    }
}
