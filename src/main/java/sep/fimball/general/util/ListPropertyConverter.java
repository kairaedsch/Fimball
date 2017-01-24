package sep.fimball.general.util;

import javafx.collections.ListChangeListener;
import javafx.collections.MapChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import sep.fimball.general.data.Action;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;

/**
 * Stellt Funktionen zum Konvertieren einer Liste sowie einer Map in eine andere Liste mit Elementen eines unterschiedlichen Typ bereit.
 */
public class ListPropertyConverter
{
    /**
     * Synchronisiert die Werte der {@code listPropertyConverted} mit den korrespondierenden Werten in der {@code listPropertyOriginal}, wenn sich die Werte in der {@code listPropertyOriginal} ändern.
     *
     * @param listPropertyConverted Die Liste, in welcher die konvertierten Objekte gespeichert werden
     * @param listPropertyOriginal  Die Liste, welche bei Änderung in die Liste listPropertyConverted konvertiert wird
     * @param converter             Ein Converter welcher angibt wie zwischen den Typen OriginalT und ConvertedT konvertiert wird
     * @param <ConvertedT>          Der Typ der Elemente in der listPropertyConverted-Liste
     * @param <OriginalT>           Der Typ der Elemente in der listPropertyOriginal-Liste
     */
    public static <ConvertedT, OriginalT> void bindAndConvertList(ObservableList<ConvertedT> listPropertyConverted, ObservableList<? extends OriginalT> listPropertyOriginal, ListConverter<ConvertedT, OriginalT> converter, Optional<CleanAble> cleanAble)
    {
        bindAndConvertList(listPropertyConverted, listPropertyOriginal, converter, originalT -> {}, originalT -> {}, () -> {}, cleanAble);
    }

    /**
     * Synchronisiert die Werte der {@code listPropertyConverted} mit den korrespondierenden Werten in der {@code listPropertyOriginal}, wenn sich die Werte in der {@code listPropertyOriginal} ändern.
     *
     * @param listPropertyConverted Die Liste, in welcher die konvertierten Objekte gespeichert werden
     * @param listPropertyOriginal  Die Liste, welche bei Änderung in die Liste listPropertyConverted konvertiert wird
     * @param converter             Ein Converter welcher angibt wie zwischen den Typen OriginalT und ConvertedT konvertiert wird
     * @param addListener           Wird aufgerufen, wenn ein Element hinzugefügt wird.
     * @param removeListener        Wird aufgerufen, wenn ein Element entfernt wird.
     * @param clearListener        Wird aufgerufen, wenn alle Elemente entfernt werden.
     * @param <ConvertedT>          Der Typ der Elemente in der listPropertyConverted-Liste
     * @param <OriginalT>           Der Typ der Elemente in der listPropertyOriginal-Liste
     */
    public static <ConvertedT, OriginalT> void bindAndConvertList(ObservableList<ConvertedT> listPropertyConverted, ObservableList<? extends OriginalT> listPropertyOriginal, ListConverter<ConvertedT, OriginalT> converter,
                                                                  Consumer<ConvertedT> addListener, Consumer<ConvertedT> removeListener, Action clearListener, Optional<CleanAble> cleanAble)
    {
        ListChangeListener<OriginalT> listChangeListener = (change) ->
        {
            if (change == null || listPropertyOriginal.isEmpty())
            {
                listPropertyConverted.clear();
                clearListener.perform();
                for (OriginalT original : listPropertyOriginal)
                {
                    ConvertedT converted = converter.convert(original);
                    addListener.accept(converted);
                    listPropertyConverted.add(converted);
                }
            }
            else
            {
                while (change.next())
                {
                    if (change.wasRemoved())
                    {
                        if (change.getFrom() == change.getTo())
                        {
                            removeListener.accept(listPropertyConverted.get(change.getFrom()));
                            listPropertyConverted.remove(change.getFrom());
                        }
                        else
                        {
                            for (int p = change.getFrom(); p < change.getTo(); p++)
                            {
                                removeListener.accept(listPropertyConverted.get(change.getFrom()));
                                listPropertyConverted.remove(change.getFrom());
                            }
                        }
                    }
                    if (change.wasAdded())
                    {
                        for (int p = change.getFrom(); p < change.getTo(); p++)
                        {
                            ConvertedT converted = converter.convert(listPropertyOriginal.get(p));
                            addListener.accept(converted);
                            listPropertyConverted.add(p, converted);
                        }
                    }
                    if (change.wasPermutated())
                    {
                        HashMap<Integer, ConvertedT> map = new HashMap<>();
                        for (int p = change.getFrom(); p < change.getTo(); p++)
                        {
                            int newPos = change.getPermutation(p);
                            if (p != newPos)
                            {
                                map.put(newPos, listPropertyConverted.get(newPos));
                                if (map.containsKey(p))
                                    listPropertyConverted.set(newPos, map.get(p));
                                else
                                    listPropertyConverted.set(newPos, listPropertyConverted.get(p));
                            }
                        }
                    }
                }
            }
        };

        listPropertyOriginal.addListener(listChangeListener);
        listChangeListener.onChanged(null);
        cleanAble.ifPresent(c -> c.addListBindingForCleanUp(listPropertyOriginal, listChangeListener));
    }

    /**
     * Synchronisiert die Werte der {@code listPropertyConverted} mit den korrespondierenden Werten in der {@code mapPropertyOriginal}, wenn sich die Werte in der {@code mapPropertyOriginal} ändern.
     *
     * @param listPropertyConverted Die Liste, die neu befüllt werden soll
     * @param mapPropertyOriginal   Die Map, deren Werte in die {@code listPropertyConverted} eingefügt werden sollen.
     * @param converter             Der Converter, der angibt, wie ein Wert vom Paar OriginalKeyT, OriginalValueT in ein ConvertedT konvertiert werden sollen.
     * @param <ConvertedT>          Der Typ der Elemente in der konvertierten List.
     * @param <OriginalKeyT>        Der Typ der Keys in der Original-Map.
     * @param <OriginalValueT>      Der Typ der Values in der Original-Map.
     */

    public static <ConvertedT, OriginalKeyT, OriginalValueT> void bindAndConvertMap(ObservableList<ConvertedT> listPropertyConverted, ObservableMap<OriginalKeyT, OriginalValueT> mapPropertyOriginal, MapConverter<ConvertedT, OriginalKeyT, OriginalValueT> converter, Optional<CleanAble> cleanAble)
    {
        MapChangeListener<OriginalKeyT, OriginalValueT> listChangeListener = (change) ->
        {
            if (change == null || change.wasRemoved())
            {
                listPropertyConverted.clear();

                for (Map.Entry<OriginalKeyT, OriginalValueT> originalEntry : mapPropertyOriginal.entrySet())
                {
                    listPropertyConverted.add(converter.convert(originalEntry.getKey(), originalEntry.getValue()));
                }
            }
            else if (change.wasAdded())
            {
                listPropertyConverted.add(converter.convert(change.getKey(), change.getValueAdded()));
            }
        };

        mapPropertyOriginal.addListener(listChangeListener);
        listChangeListener.onChanged(null);
        cleanAble.ifPresent(c -> c.addMapBindingForCleanUp(mapPropertyOriginal, listChangeListener));
    }

    /**
     * Das Interface stellt einen allgemeinen Converter dar, welcher angegeben werden muss, um aus einem Objekt vom Typ OriginalT
     * ein Objekt vom Typ ConvertedT zu erstellen.
     *
     * @param <ConvertedT> Der Typ der Elemente in der listPropertyConverted Liste
     * @param <OriginalT>  Der Typ der Elemente in der listPropertyOriginal Liste
     */
    @FunctionalInterface
    public interface ListConverter<ConvertedT, OriginalT>
    {
        /**
         * Erstellt ein Objekt vom Typ ConvertedT aus einem Objekt vom Typ OriginalT.
         *
         * @param original Das Objekt vom Typ OriginalT aus dem ein ConvertedT erstellt wird
         * @return Ein Objekt vom Typ ConvertedT
         */
        ConvertedT convert(OriginalT original);
    }

    /**
     * Das Interface stellt einen allgemeinen Converter dar, welcher angegeben werden muss, um aus einem Key/Value Paar vom Typ OriginalT
     * ein Objekt vom Typ ConvertedT zu erzeugen.
     *
     * @param <ConvertedT>     Der Typ des konvertierten Objekts.
     * @param <OriginalKeyT>   Der Typ des Keys des originalen Objekts.
     * @param <OriginalValueT> Der Typ des Values des originalen Objekts.
     */
    @FunctionalInterface
    public interface MapConverter<ConvertedT, OriginalKeyT, OriginalValueT>
    {
        /**
         * Erstellt ein Objekt vom Typ ConvertedT aus einem Key/Value Paar vom Typ OriginalT.
         *
         * @param originalKey    Der Key vom Typ OriginalKeyT.
         * @param originalValueT Der Value vom Typ originalValueT.
         * @return Ein Objekt vom Typ ConvertedT.
         */
        ConvertedT convert(OriginalKeyT originalKey, OriginalValueT originalValueT);
    }
}
