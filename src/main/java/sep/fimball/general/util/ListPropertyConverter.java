package sep.fimball.general.util;

import javafx.beans.property.ListProperty;
import javafx.collections.ListChangeListener;
import javafx.collections.MapChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;

import java.util.List;
import java.util.Map;

/**
 * Stellt Funktionen zum konvertieren einer Liste sowie einer Map in eine andere bereit.
 */
public class ListPropertyConverter
{
    /**
     * Synchronisiert die Werte der {@code listPropertyConverted} mit den korrespondierenden Werten in der {@code listPropertyOriginal}, wenn sich die Werte in der {@code listPropertyOriginal} ändern.
     *
     * @param listPropertyConverted  Die Liste, in welcher die konvertierten Objekte gespeichert werden
     * @param listPropertyOriginal Die Liste, welche bei Änderung in die Liste listPropertyConverted konvertiert wird
     * @param converter          Ein Converter welcher angibt wie zwischen den Typen OriginalT und ConvertedT konvertiert wird
     * @param <ConvertedT>           Der Typ der listPropertyConverted-Liste
     * @param <OriginalT>          Der Typ der listPropertyOriginal-Liste
     */
    public static <ConvertedT, OriginalT> void bindAndConvertList(ListProperty<ConvertedT> listPropertyConverted, ObservableList<OriginalT> listPropertyOriginal, ListConverter<ConvertedT, OriginalT> converter)
    {
        ListChangeListener<OriginalT> listChangeListener = (change) ->
        {
            if (change == null)
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
                        for (int p = change.getFrom(); p <= change.getTo(); p++)
                        {
                            listPropertyConverted.remove(change.getFrom());
                        }
                    }
                    if (change.wasAdded())
                    {
                        List<? extends OriginalT> newElementsList = change.getAddedSubList();

                        for (OriginalT original : newElementsList)
                        {
                            listPropertyConverted.add(converter.convert(original));
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

    public static <OriginalT> void bindAndFilterList(ListProperty<OriginalT> listPropertyConverted, ObservableList<OriginalT> listPropertyOriginal, ListFilter<OriginalT> filter)
    {
        ListChangeListener<OriginalT> listChangeListener = (change) ->
        {
            listPropertyConverted.clear();
            for (OriginalT original : listPropertyOriginal)
            {
                if(filter.shouldKeep(original)) listPropertyConverted.add(original);
            }
        };

        listPropertyOriginal.addListener(listChangeListener);
        listChangeListener.onChanged(null);
    }

    /**
     * Synchronisiert die Werte der {@code listPropertyConverted} mit den korrespondierenden Werten in der {@code mapPropertyOriginal}, wenn sich die Werte in der {@code mapPropertyOriginal} ändern.
     *
     *
     * @param listPropertyConverted Die Liste, die neu befüllt werden soll
     * @param mapPropertyOriginal Die Map, deren Werte in die {@code listPropertyConverted} eingefügt werden sollen.
     * @param converter Der Converter, der angibt, wie ein Wert vom Paar OriginalKeyT, OriginalValueT in ein ConvertedT konvertiert werden sollen.
     * @param <ConvertedT> Die Klasse der Elemente in der konvertierten List.
     * @param <OriginalKeyT> Die Klasse der Key in der Original-Map.
     * @param <OriginalValueT> Die Klasse der Values in der Original-Map.
     */

    public static <ConvertedT, OriginalKeyT, OriginalValueT> void bindAndConvertMap(ListProperty<ConvertedT> listPropertyConverted, ObservableMap<OriginalKeyT, OriginalValueT> mapPropertyOriginal, MapConverter<ConvertedT, OriginalKeyT, OriginalValueT> converter)
    {
        MapChangeListener<OriginalKeyT, OriginalValueT> listChangeListener = (change) ->
        {
            listPropertyConverted.clear();

            for (Map.Entry<OriginalKeyT, OriginalValueT> originalEntry : mapPropertyOriginal.entrySet())
            {
                listPropertyConverted.add(converter.convert(originalEntry.getKey(), originalEntry.getValue()));
            }
        };

        mapPropertyOriginal.addListener(listChangeListener);
        listChangeListener.onChanged(null);
    }

    /**
     * Das Interface stellt einen allgemeinen Converter da welcher angegeben werden muss um aus einem Objekt vom Typ OriginalT
     * ein Objekt vom Typ ConvertedT zu erstellen
     *
     * @param <ConvertedT>  Der Typ der listPropertyConverted Liste
     * @param <OriginalT> Der Typ der listPropertyOriginal Liste
     */
    @FunctionalInterface
    public interface ListConverter<ConvertedT, OriginalT>
    {
        /**
         * Erstellt ein Objekt vom Typ ConvertedT aus einem Objekt vom Typ OriginalT
         *
         * @param original Das Objekt vom Typ OriginalT aus dem ein ConvertedT erstellt wird
         * @return Ein Objekt vom Typ ConvertedT
         */
        ConvertedT convert(OriginalT original);
    }

    /**
     * Das Interface stellt einen allgemeinen Converter da welcher angegeben werden muss um aus einem Key/Value Paar vom Typ OriginalT
     * ein Objekt vom Typ ConvertedT zu erzeugen
     *
     * @param <ConvertedT>
     * @param <OriginalKeyT>
     * @param <OriginalValueT>
     */
    @FunctionalInterface
    public interface MapConverter<ConvertedT, OriginalKeyT, OriginalValueT>
    {
        /**
         * Erstellt ein Objekt vom Typ ConvertedT aus einem Key/Value Paar vom Typ OriginalT.
         *
         * @param originalKey Der Kay vom Typ OriginalKeyT.
         * @param originalValueT Der Value vom Typ originalValueT.
         * @return Ein Objekt vom Typ ConvertedT.
         */
        ConvertedT convert(OriginalKeyT originalKey, OriginalValueT originalValueT);
    }

    @FunctionalInterface
    public interface ListFilter<OriginalT>
    {
        boolean shouldKeep(OriginalT original);
    }
}
