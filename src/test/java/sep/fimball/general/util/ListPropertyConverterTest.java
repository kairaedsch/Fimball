package sep.fimball.general.util;

import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * Created by alexcekay on 11/28/16.
 */
public class ListPropertyConverterTest {

    private ListProperty<DummyOne> originalList;
    private ListProperty<DummyTwo> convertedList;
    private final int originalListSize = 100;

    @Before
    public void initialize()
    {
        originalList = new SimpleListProperty<>(FXCollections.observableArrayList());
        convertedList = new SimpleListProperty<>(FXCollections.observableArrayList());

        for (int i = 0; i < originalListSize; i++)
        {
            originalList.add(new DummyOne(i));
        }
    }

    @Test
    public void convertAndBindListTest()
    {
        ListPropertyConverter.bindAndConvertList(convertedList, originalList, (DummyTwo::new));
        assertTrue(convertedList.size() == originalListSize);

        for (int i = 0; i < originalListSize; i++)
        {
            assertTrue(originalList.get(i).getData() == (int)convertedList.get(i).getData());
        }
        //Ein Element hinzufügen, überprüfen ob Größe der Converted um eins höher und Elemente gleich
        originalList.add(new DummyOne(100));
        assertTrue(convertedList.size() == originalListSize + 1);
        assertTrue(originalList.get(originalListSize).getData() == (int)convertedList.get(originalListSize).getData());

        //Ein Element entfernen, überprüfen ob Größe der Converted um eins geringer
        originalList.remove(originalListSize);
        assertTrue(convertedList.size() == originalListSize);

        //Zwei Elemente vertauschen, überprüfen ob Größe der Converted gleich
        DummyOne temp = originalList.get(0);
        originalList.set(0, originalList.get(originalListSize - 1));
        originalList.set(originalListSize - 1, temp);
        assertTrue(convertedList.size() == originalListSize);
        assertTrue(originalList.get(0).getData() == (int)convertedList.get(0).getData());
        assertTrue(originalList.get(originalListSize - 1).getData() == (int)convertedList.get(originalListSize -1).getData());
    }

    public static class DummyOne
    {
        private int data;

        public DummyOne(int data)
        {
            this.data = data;
        }

        public int getData()
        {
            return data;
        }
    }

    public static class DummyTwo
    {
        private double data;

        public DummyTwo(DummyOne dummy)
        {
            this.data = dummy.getData();
        }

        public double getData()
        {
            return data;
        }
    }
}
