package sep.fimball;

import org.junit.Ignore;
import org.junit.Test;
import sep.fimball.general.data.Vector2;
import sep.fimball.model.blueprint.base.BaseElement;
import sep.fimball.model.blueprint.base.BaseElementManager;
import sep.fimball.model.blueprint.pinballmachine.PinballMachine;
import sep.fimball.model.blueprint.pinballmachine.PinballMachineManager;
import sep.fimball.model.blueprint.pinballmachine.PlacedElement;

import java.util.ArrayList;
import java.util.PrimitiveIterator;
import java.util.Random;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;

@Ignore
public class TestMachineGenerator
{
    @Test
    public void generateScalabilityTestMachine()
    {
        int[] sizes = {16, 32, 64, 128, 256, 512, 1024};
        final int distance = 20;

        for (int i = 0; i < sizes.length; i++)
        {
            PinballMachine pinballMachine = PinballMachineManager.getInstance().createNewMachine();

            int size = sizes[i];

            pinballMachine.nameProperty().setValue("Test - Scalability " + size + "*" + size);

            String[] keys = new String[BaseElementManager.getInstance().elementsProperty().getSize()];
            BaseElementManager.getInstance().elementsProperty().get().keySet().toArray(keys);

            BaseElement[] baseElements = new BaseElement[keys.length];
            for (int k = 0; k < keys.length; k++)
            {
                baseElements[k] = BaseElementManager.getInstance().getElement(keys[k]);
            }

            ArrayList<PlacedElement> list = new ArrayList<>();

            PrimitiveIterator.OfInt iterator = new Random().doubles(size * size).mapToInt(d -> (int) (keys.length * d)).iterator();
            int halfSize = size / 2;
            for (int x = 0; x < size; x++)
            {
                int xPos = (x - halfSize) * distance;
                for (int y = 0; y < size; y++)
                {
                    list.add(new PlacedElement(baseElements[iterator.next()], new Vector2(xPos, y * distance), 0, 1, 0));
                }
            }
            System.out.println("created list with: " + size);

            pinballMachine.addElement(list.toArray(new PlacedElement[0]));

            System.out.println("created machine with: " + size);

            pinballMachine.saveToDisk(true);

            System.out.println("saved machine with: " + size);
        }
    }

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
                list.add(new PlacedElement(BaseElementManager.getInstance().getElement("light_green"), new Vector2(x * distance - (size * distance / 2), y * distance), 0, 0, 0));
            }
        }
        pinballMachine.addElement(list.toArray(new PlacedElement[0]));
        pinballMachine.saveToDisk(true);
    }

    @Test
    public void generateScalabilityCollisionTestMachine()
    {
        PinballMachine pinballMachine = PinballMachineManager.getInstance().createNewMachine();

        final int size = 512;

        pinballMachine.nameProperty().setValue("Test - Scalability Collision " + size);

        pinballMachine.addElement(new PlacedElement(BaseElementManager.getInstance().getElement("hinderniss_linie_2"), new Vector2(1, 8), 0, 0, 0));

        for (int i = 0; i < size; i++)
        {
            PlacedElement element = new PlacedElement(BaseElementManager.getInstance().getElement("target_green"), new Vector2(0, 6), 1, 0, 0);
            pinballMachine.addElement(element);
        }

        pinballMachine.saveToDisk(true);
    }
}
