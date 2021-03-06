package sep.fimball.general.util;

import javafx.beans.property.ListProperty;
import javafx.beans.property.MapProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleMapProperty;
import javafx.collections.FXCollections;
import org.junit.Before;
import org.junit.Test;

import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * Tests für die Klasse ListPropertyConverter
 */
public class ListPropertyConverterTest
{

    /**
     * Original Liste auf der die Listenoperationen ausgeführt werden.
     */
    private ListProperty<DummyOne> originalList;

    /**
     * Original Map auf der die Mapoperationen ausgeführt werden.
     */
    private MapProperty<Integer, DummyOne> originalMap;

    /**
     * Liste die mit der originalList/originalMap synchronisiert wird, vorher werden die Werte konvertiert.
     */
    private ListProperty<DummyTwo> convertedList;

    /**
     * Die Anfangsgröße der originalList und originalMap.
     */
    private final int originalSize = 100;

    /**
     * Wird vor jedem Test zur Initialisierung der Listen und Map genutzt.
     */
    @Before
    public void initialize()
    {
        originalList = new SimpleListProperty<>(FXCollections.observableArrayList());
        originalMap = new SimpleMapProperty<>(FXCollections.observableHashMap());
        convertedList = new SimpleListProperty<>(FXCollections.observableArrayList());

        //Die Liste und Map mit werten von 0-99 füllen.
        for (int i = 0; i < originalSize; i++)
        {
            originalList.add(new DummyOne(i));
            originalMap.put(i, new DummyOne(i));
        }
    }

    /**
     * Testet ob das Binden einer Liste an eine andere Liste mit Konvertierung funktioniert.
     */
    @Test
    public void convertAndBindListTest()
    {
        ListPropertyConverter.bindAndConvertList(convertedList, originalList, (DummyTwo::new), Optional.empty());
        //Überprüfen ob beide Listen nach der Bindung gleich groß sind.
        assertThat("Die Größe der convertedList ist 100", convertedList.size(), is(originalSize));

        //Überprüfen ob alle Daten in beiden Listen gleich sind.
        for (int i = 0; i < originalSize; i++)
        {
            assertThat("Element " + i + " ist in originalList und convertedList gleich", originalList.get(i).getData() == (int) convertedList.get(i).getData(), is(true));
        }
        //Ein Element hinzufügen, überprüfen ob Größe der convertedList ebenfalls um eins gestiegen ist und das eingefügte Element in beiden Listen gleich ist.
        originalList.add(new DummyOne(100));
        assertThat("Die Größe der convertedList ist 101", convertedList.size(), is(originalSize + 1));
        assertThat("Element 100 ist in originalList und convertedList gleich", originalList.get(originalSize).getData() == (int) convertedList.get(originalSize).getData(), is(true));

        //Ein Element entfernen, überprüfen ob Größe der convertedList ebenfalls um eins gesunken ist.
        originalList.remove(originalSize);
        assertThat("Die Größe der convertedList ist 100", convertedList.size(), is(originalSize));

        //Zwei Elemente vertauschen, überprüfen ob Größe gleich geblieben ist.
        DummyOne temp = originalList.get(0);
        originalList.set(0, originalList.get(originalSize - 1));
        originalList.set(originalSize - 1, temp);
        assertThat("Die Größe der convertedList ist 100", convertedList.size(), is(originalSize));
        //Überprüfen ob die Permutation auch in der convertedList durchgeführt worden ist.
        assertThat("Element 0 ist in der originalList und convertedList gleich", originalList.get(0).getData() == (int) convertedList.get(0).getData(), is(true));
        assertThat("Element 99 ist in der originalList und convertedList gleich", originalList.get(originalSize - 1).getData() == (int) convertedList.get(originalSize - 1).getData(), is(true));
    }

    /**
     * Testet ob das Binden einer Liste an eine Map mit Konvertierung funktioniert.
     */
    @Test
    public void convertAndBindMapTest()
    {
        ListPropertyConverter.bindAndConvertMap(convertedList, originalMap, ((originalKey, dummyOne) -> new DummyTwo(dummyOne)), Optional.empty());
        //Überprüfen ob die Größe der Map und Liste nach der Bindung gleich groß ist.
        assertThat("Die Größe der convertedList ist gleich der Größe der originalMap", convertedList.size(), is(originalMap.size()));
        originalMap.put(100, new DummyOne(100));
        //Überprüfen ob nach einfügen eines Elements in die Map die Größe der Liste ebenfalls um eins gestiegen ist.
        assertThat("Die Größe der convertedList ist 101", convertedList.size(), is(originalSize + 1));
        //Überprüfen ob das eingefügte Element in beiden gleich ist.
        assertThat("Element 100 der convertedList und originalMap sind gleich", originalMap.get(100).getData() == (int) convertedList.get(originalSize).getData(), is(true));

        //Selben Key erneut setzen um zu überprüfen ob nicht unnötigerweise in die Liste eingefügt wird.
        originalMap.put(100, new DummyOne(100));
        assertThat("Die Größe der convertedList ist 101", convertedList.size(), is(originalSize + 1));
        assertThat("Element 100 der convertedList und originalMap sind gleich", originalMap.get(100).getData() == (int) convertedList.get(originalSize).getData(), is(true));

        //Den erstellten Map Eintrag entfernen und überprüfen ob danach die Größe der beiden gleich ist.
        originalMap.remove(100);
        assertThat("Die Größe der convertedList ist gleich der Größe der originalMap", convertedList.size(), is(originalMap.size()));
    }

    /**
     * Dummy Datentyp welcher Integer Werte nutzt.
     */
    private static class DummyOne
    {
        /**
         * Dummy Feld vom Typ int.
         */
        private int data;

        /**
         * Erzeugt eine Dummy Klasse welche Integer Werte nutzt.
         *
         * @param data Der Dummy Wert welcher gespeichert werden soll.
         */
        public DummyOne(int data)
        {
            this.data = data;
        }

        /**
         * Gibt den Dummy Wert vom Typ Integer zurück.
         *
         * @return Der Dummy Wert.
         */
        public int getData()
        {
            return data;
        }
    }

    /**
     * Dummy Datentyp welcher Double Werte nutzt.
     */
    private static class DummyTwo
    {
        /**
         * Dummy Feld vom Typ data.
         */
        private double data;

        /**
         * Erzeugt eine Dummy Klasse welche Double Werte nutzt.
         *
         * @param dummy Der Dummy Wert welcher gespeichert werden soll.
         */
        public DummyTwo(DummyOne dummy)
        {
            this.data = dummy.getData();
        }

        /**
         * Gibt den Dummy Wert vom Typ Double zurück.
         *
         * @return Der Dummy Wert.
         */
        public double getData()
        {
            return data;
        }
    }
}
