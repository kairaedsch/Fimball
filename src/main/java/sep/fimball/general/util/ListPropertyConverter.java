package sep.fimball.general.util;

import javafx.application.Platform;
import javafx.collections.ListChangeListener;
import javafx.collections.MapChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;

import java.util.Comparator;
import java.util.Map;

/**
 * Stellt Funktionen zum Konvertieren einer Liste sowie einer Map in eine andere Liste mit Elementen eines unterschiedlichen Typ bereit.
 */
public class ListPropertyConverter
{
    /**
     * Sortiert die Liste automatisch, wenn sie sich verändert.
     *
     * @param list       Die zu sortierende Liste.
     * @param comparator Der Vergleichsoperator für die Liste.
     * @param <T>        Der Typ der Listelemente.
     */
    public static <T> void autoSort(ObservableList<T> list, Comparator<T> comparator)
    {
        ListChangeListener<T> listChangeListener = new ListChangeListener<T>()
        {
            /**
             * Gibt an, ob die Liste gerade sortiert wird.
             */
            boolean sorting = false;

            @Override
            public void onChanged(Change<? extends T> change)
            {
                if (!sorting)
                {
                    sorting = true;

                    Platform.runLater(() ->
                    {
                        list.sort(comparator);
                        sorting = false;
                    });
                }
            }
        };

        list.addListener(listChangeListener);
        listChangeListener.onChanged(null);
    }

    /**
     * Synchronisiert die Werte der {@code listPropertyConverted} mit den korrespondierenden Werten in der {@code listPropertyOriginal}, wenn sich die Werte in der {@code listPropertyOriginal} ändern.
     *
     * @param listPropertyConverted Die Liste, in welcher die konvertierten Objekte gespeichert werden
     * @param listPropertyOriginal  Die Liste, welche bei Änderung in die Liste listPropertyConverted konvertiert wird
     * @param converter             Ein Converter welcher angibt wie zwischen den Typen OriginalT und ConvertedT konvertiert wird
     * @param <ConvertedT>          Der Typ der Elemente in der listPropertyConverted-Liste
     * @param <OriginalT>           Der Typ der Elemente in der listPropertyOriginal-Liste
     */
    public static <ConvertedT, OriginalT> void bindAndConvertList(ObservableList<ConvertedT> listPropertyConverted, ObservableList<? extends OriginalT> listPropertyOriginal, ListConverter<ConvertedT, OriginalT> converter)
    {
        ListChangeListener<OriginalT> listChangeListener = (change) ->
        {
            if (change == null || listPropertyOriginal.isEmpty())
            {
                listPropertyConverted.clear();
                for (OriginalT original : listPropertyOriginal)
                {
                    listPropertyConverted.add(converter.convert(original));
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
                            listPropertyConverted.remove(change.getFrom());
                        }
                        else
                        {
                            for (int p = change.getFrom(); p < change.getTo(); p++)
                            {
                                listPropertyConverted.remove(change.getFrom());
                            }
                        }
                    }
                    if (change.wasAdded())
                    {
                        for (int p = change.getFrom(); p < change.getTo(); p++)
                        {
                            listPropertyConverted.add(p, converter.convert(listPropertyOriginal.get(p)));
                        }
                    }
                    if (change.wasPermutated())
                    {
                        for (int p = change.getFrom(); p <= change.getTo(); p++)
                        {
                            int newPos = change.getPermutation(p);
                            ConvertedT temp = listPropertyConverted.get(p);
                            listPropertyConverted.set(p, listPropertyConverted.get(newPos));
                            listPropertyConverted.set(newPos, temp);
                        }
                    }
                }
            }
        };

        listPropertyOriginal.addListener(listChangeListener);
        listChangeListener.onChanged(null);
    }

    /**
     * Synchronisiert die Elemente der {@code listPropertyConverted} mit den korrespondierenden Werten in der {@code listPropertyOriginal}, wobei Elemente durch den {@code filter} von der Synchronisation ausgeschlossen werden können.
     *
     * @param listPropertyConverted Die Liste, in welche die gefilterten Elemente gespeichert werden.
     * @param listPropertyOriginal  Die originale Liste.
     * @param filter                Der Filter, der angewendet wird.
     * @param <ElementT>            Der Typ der Elemente der konvertierten Liste.
     */
    public static <ElementT> void bindAndFilterList(ObservableList<ElementT> listPropertyConverted, ObservableList<? extends ElementT> listPropertyOriginal, ListFilter<ElementT> filter)
    {
        ListChangeListener<ElementT> listChangeListener = (change) ->
        {
            listPropertyConverted.clear();
            for (ElementT original : listPropertyOriginal)
            {
                if (filter.shouldKeep(original))
                    listPropertyConverted.add(original);
            }
        };

        listPropertyOriginal.addListener(listChangeListener);
        listChangeListener.onChanged(null);
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

    public static <ConvertedT, OriginalKeyT, OriginalValueT> void bindAndConvertMap(ObservableList<ConvertedT> listPropertyConverted, ObservableMap<OriginalKeyT, OriginalValueT> mapPropertyOriginal, MapConverter<ConvertedT, OriginalKeyT, OriginalValueT> converter)
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

    /**
     * Das Interface stellt einen allgemeinen Filter dar, der angibt, ob Elemente aus einer Liste behalten werden sollen.
     *
     * @param <ElementT> Der Typ der Elemente der Liste, die gefiltert werden soll.
     */
    @FunctionalInterface
    public interface ListFilter<ElementT>
    {
        /**
         * Gibt an, ob das Element behalten werden soll oder nicht.
         *
         * @param original Das Element, das überprüft werden soll.
         * @return {@code true}, wenn das Element behalten werden soll, {@code false} sonst.
         */
        boolean shouldKeep(ElementT original);
    }
}
