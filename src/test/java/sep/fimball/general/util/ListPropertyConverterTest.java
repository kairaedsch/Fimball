package sep.fimball.general.util;

import javafx.beans.property.ListProperty;
import javafx.beans.property.MapProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleMapProperty;
import javafx.collections.FXCollections;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

/**
 * Created by alexcekay on 11/28/16.
 */
public class ListPropertyConverterTest {

    private ListProperty<DummyOne> originalList;
    private MapProperty<Integer, DummyOne> originalMap;
    private ListProperty<DummyOne> filteredList;
    private ListProperty<DummyTwo> convertedList;
    private final int originalSize = 100;

    @Before
    public void initialize()
    {
        originalList = new SimpleListProperty<>(FXCollections.observableArrayList());
        originalMap = new SimpleMapProperty<>(FXCollections.observableHashMap());
        filteredList = new SimpleListProperty<>(FXCollections.observableArrayList());
        convertedList = new SimpleListProperty<>(FXCollections.observableArrayList());

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
        assertThat(convertedList.size(), is(originalSize));

        for (int i = 0; i < originalSize; i++)
        {
            assertThat(originalList.get(i).getData() == (int)convertedList.get(i).getData(), is(true));
        }
        //Ein Element hinzufügen, überprüfen ob Größe der Converted um eins höher und Elemente gleich
        originalList.add(new DummyOne(100));
        assertThat(convertedList.size(), is(originalSize + 1));
        assertThat(originalList.get(originalSize).getData() == (int)convertedList.get(originalSize).getData(), is(true));

        //Ein Element entfernen, überprüfen ob Größe der Converted um eins geringer
        originalList.remove(originalSize);
        assertThat(convertedList.size(), is(originalSize));

        //Zwei Elemente vertauschen, überprüfen ob Größe der Converted gleich
        DummyOne temp = originalList.get(0);
        originalList.set(0, originalList.get(originalSize - 1));
        originalList.set(originalSize - 1, temp);
        assertThat(convertedList.size(), is(originalSize));
        assertThat(originalList.get(0).getData() == (int)convertedList.get(0).getData(), is(true));
        assertThat(originalList.get(originalSize - 1).getData() == (int)convertedList.get(originalSize - 1).getData(), is(true));
    }

    @Test
    public void convertAndBindMapTest()
    {
        ListPropertyConverter.bindAndConvertMap(convertedList, originalMap, ((originalKey, dummyOne) -> new DummyTwo(dummyOne)));
        assertThat(convertedList.size(), is(originalMap.size()));
        originalMap.put(100, new DummyOne(100));
        assertThat(convertedList.size(), is(originalSize + 1));
        assertThat(originalMap.get(100).getData() == (int)convertedList.get(originalSize).getData(), is(true));

        //Selben Key erneut setzen um zu überprüfen ob nicht unnötigerweise in die Liste eingefügt wird
        originalMap.put(100, new DummyOne(100));
        assertThat(convertedList.size(), is(originalSize + 1));
        assertThat(originalMap.get(100).getData() == (int)convertedList.get(originalSize).getData(), is(true));

        //Den erstellten Map Eintrag entfernen und überprüfen ob die Größen noch immer gleich sind
        originalMap.remove(100);
        assertThat(convertedList.size(), is(originalMap.size()));
    }

    @Test
    public void filterListTest()
    {
        ListPropertyConverter.bindAndFilterList(filteredList, originalList, (original -> original.getData() >= 50));

        //Überprüfen ob alle Elemente der Liste die Bedingung erfüllen
        filteredList.forEach(dummyOne -> assertThat(dummyOne.getData() >= 50, is(true)));

        int currentSize = filteredList.size();
        //Ein neues Element einfügen welches die Bedingung erfüllt
        originalList.add(new DummyOne(1337));
        //Überprüfen ob die gefilterte Liste um eins größer ist
        assertThat(filteredList.size(), is(currentSize + 1));
        //Überprüfen ob das eingefügte Element die Bedingung erfüllt
        assertThat(filteredList.get(currentSize).getData() >= 50, is(true));

        //Ein neues Element einfügen welches die Bedingung nicht erfüllt
        originalList.add(new DummyOne(10));
        //Überprüfen ob weiterhin alle Elemente die Bedingung erfüllen
        filteredList.forEach(dummyOne -> assertThat(dummyOne.getData() >= 50, is (true)));
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
