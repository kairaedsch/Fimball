package sep.fimball.general.util;

import javafx.beans.property.ListProperty;
import javafx.collections.ListChangeListener;
import javafx.collections.MapChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;

import java.util.List;
import java.util.Map;

/**
 * Stellt Funktionen zum konvertieren einer Liste in eine andere bereit.
 */
public class ListPropertyConverter
{
    /**
     * Löscht die Werte der listPropertySlave und füllt diese mit den Werten der listPropertyMaster, wenn sich letztere ändert.
     * TODO
     *
     * @param listPropertySlave  Die Liste in welcher die konvertierten Objekte gespeichert werden
     * @param listPropertyMaster Die Liste welche bei Änderung in die Liste listPropertySlave konvertiert wird
     * @param converter          Ein Converter welcher angibt wie zwischen den Typen MasterT und SlaveT konvertiert wird
     * @param <SlaveT>           Der Typ der listPropertySlave Liste
     * @param <MasterT>          Der Typ der listPropertyMaster Liste
     */
    public static <SlaveT, MasterT> void bindAndConvertList(ListProperty<SlaveT> listPropertySlave, ObservableList<MasterT> listPropertyMaster, ListConverter<SlaveT, MasterT> converter)
    {
        ListChangeListener<MasterT> listChangeListener = (change) ->
        {
            if (change == null)
            {
                listPropertySlave.clear();
                for (MasterT master : listPropertyMaster)
                {
                    listPropertySlave.add(converter.convert(master));
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
                            listPropertySlave.remove(change.getFrom());
                        }
                    }
                    if (change.wasAdded())
                    {
                        List<? extends MasterT> newElementsList = change.getAddedSubList();

                        for (MasterT master : newElementsList)
                        {
                            listPropertySlave.add(converter.convert(master));
                        }
                    }
                    if (change.wasPermutated())
                    {
                        for (int p = change.getFrom(); p <= change.getTo(); p++)
                        {
                            int newPos = change.getPermutation(p);
                            SlaveT temp = listPropertySlave.get(p);
                            listPropertySlave.set(p, listPropertySlave.get(newPos));
                            listPropertySlave.set(newPos, temp);
                        }
                    }
                }
            }
        };

        listPropertyMaster.addListener(listChangeListener);
        listChangeListener.onChanged(null);
    }

    /**
     * Löscht die Werte der listPropertySlave und füllt diese mit den Werten der MapPropertyMaster, wenn sich letztere ändert.
     * TODO
     *
     * @param listPropertySlave Die Liste, die neu befüllt werden soll
     * @param MapPropertyMaster Die Map, deren Werte in die {@code listPropertySlave} eingefügt werden sollen.
     * @param converter Der Converter, der angibt, wie ein Wert vom Paar MasterKeyT, MasterValueT in ein SlaveT konvertiert werden sollen.
     * @param <SlaveT> Die Klasse der Elemente in der Slave-Liste.
     * @param <MasterKeyT> Die Klasse der Key in der Master-Map.
     * @param <MasterValueT> Die Klasse der Values in der Master-Map.
     */

    public static <SlaveT, MasterKeyT, MasterValueT> void bindAndConvertMap(ListProperty<SlaveT> listPropertySlave, ObservableMap<MasterKeyT, MasterValueT> MapPropertyMaster, MapConverter<SlaveT, MasterKeyT, MasterValueT> converter)
    {
        MapChangeListener<MasterKeyT, MasterValueT> listChangeListener = (change) ->
        {
            listPropertySlave.clear();

            for (Map.Entry<MasterKeyT, MasterValueT> masterEntry : MapPropertyMaster.entrySet())
            {
                listPropertySlave.add(converter.convert(masterEntry.getKey(), masterEntry.getValue()));
            }
        };

        MapPropertyMaster.addListener(listChangeListener);
        listChangeListener.onChanged(null);
    }

    /**
     * Das Interface stellt einen allgemeinen Converter da welcher angegeben werden muss um aus einem Objekt vom Typ MasterT
     * ein Objekt vom Typ SlaveT zu erstellen
     *
     * @param <SlaveT>  Der Typ der listPropertySlave Liste
     * @param <MasterT> Der Typ der listPropertyMaster Liste
     */
    @FunctionalInterface
    public interface ListConverter<SlaveT, MasterT>
    {
        /**
         * Erstellt ein Objekt vom Typ SlaveT aus einem Objekt vom Typ MasterT
         *
         * @param master Das Objekt vom Typ MasterT aus dem ein SlaveT erstellt wird
         * @return Ein Objekt vom Typ SlaveT
         */
        SlaveT convert(MasterT master);
    }

    /**
     * Das Interface stellt einen allgemeinen Converter da welcher angegeben werden muss um aus einem Key/Value Paar vom Typ MasterT
     * ein Objekt vom Typ SlaveT zu erzeugen
     *
     * @param <SlaveT>
     * @param <MasterKeyT>
     * @param <MasterValueT>
     */
    @FunctionalInterface
    public interface MapConverter<SlaveT, MasterKeyT, MasterValueT>
    {
        /**
         * Erstellt ein Objekt vom Typ SlaveT aus einem Key/Value Paar vom Typ MasterT.
         *
         * @param masterKey Der Kay vom Typ MasterKeyT.
         * @param masterValueT Der Value vom Typ masterValueT.
         * @return Ein Objekt vom Typ SlaveT.
         */
        SlaveT convert(MasterKeyT masterKey, MasterValueT masterValueT);
    }
}
