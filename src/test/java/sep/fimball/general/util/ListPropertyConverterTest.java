package sep.fimball.general.util;

import javafx.beans.property.ListProperty;
import javafx.beans.property.MapProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleMapProperty;
import javafx.collections.FXCollections;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * Created by alexcekay on 11/28/16.
 */
public class ListPropertyConverterTest {

    private ListProperty<DummyOne> originalList;
    private MapProperty<Integer, DummyOne> originalMap;
    private ListProperty<DummyTwo> convertedList;
    private final int originalSize = 100;

    @Before
    public void initialize()
    {
        originalList = new SimpleListProperty<>(FXCollections.observableArrayList());
        convertedList = new SimpleListProperty<>(FXCollections.observableArrayList());
        originalMap = new SimpleMapProperty<>(FXCollections.observableHashMap());

        for (int i = 0; i < originalSize; i++)
        {
            originalList.add(new DummyOne(i));
            originalMap.put(i, new DummyOne(i));
        }
    }

    @Test
    public void convertAndBindListTest()
    {
        ListPropertyConverter.bindAndConvertList(convertedList, originalList, (DummyTwo::new));
        assertTrue(convertedList.size() == originalSize);

        for (int i = 0; i < originalSize; i++)
        {
            assertTrue(originalList.get(i).getData() == (int)convertedList.get(i).getData());
        }
        //Ein Element hinzufügen, überprüfen ob Größe der Converted um eins höher und Elemente gleich
        originalList.add(new DummyOne(100));
        assertTrue(convertedList.size() == originalSize + 1);
        assertTrue(originalList.get(originalSize).getData() == (int)convertedList.get(originalSize).getData());

        //Ein Element entfernen, überprüfen ob Größe der Converted um eins geringer
        originalList.remove(originalSize);
        assertTrue(convertedList.size() == originalSize);

        //Zwei Elemente vertauschen, überprüfen ob Größe der Converted gleich
        DummyOne temp = originalList.get(0);
        originalList.set(0, originalList.get(originalSize - 1));
        originalList.set(originalSize - 1, temp);
        assertTrue(convertedList.size() == originalSize);
        assertTrue(originalList.get(0).getData() == (int)convertedList.get(0).getData());
        assertTrue(originalList.get(originalSize - 1).getData() == (int)convertedList.get(originalSize -1).getData());
    }

    @Test
    public void convertAndBindMapTest()
    {
        ListPropertyConverter.bindAndConvertMap(convertedList, originalMap, ((originalKey, dummyOne) -> new DummyTwo(dummyOne)));
        assertTrue(convertedList.size() == originalMap.size());
        originalMap.put(100, new DummyOne(100));
        assertTrue(convertedList.size() == originalSize + 1);
        assertTrue(originalMap.get(100).getData() == (int)convertedList.get(originalSize).getData());

        //Selben Key erneut setzen um zu überprüfen ob nicht unnötigerweise in die Liste eingefügt wird
        originalMap.put(100, new DummyOne(100));
        assertTrue(convertedList.size() == originalSize + 1);
        assertTrue(originalMap.get(100).getData() == (int)convertedList.get(originalSize).getData());

        //Den erstellten Map Eintrag entfernen und überprüfen ob die Größen noch immer gleich sind
        originalMap.remove(100);
        assertTrue(convertedList.size() == originalMap.size());
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
