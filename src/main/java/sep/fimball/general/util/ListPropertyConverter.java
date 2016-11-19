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
     * @param listPropertySlave
     * @param listPropertyMaster
     * @param converter
     * @param <SlaveT>
     * @param <MasterT>
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
     * @param listPropertySlave
     * @param MapPropertyMaster
     * @param converter
     * @param <SlaveT>
     * @param <MasterKeyT>
     * @param <MasterValueT>
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
     * TODO
     *
     * @param <SlaveT>
     * @param <MasterT>
     */
    public interface ListConverter<SlaveT, MasterT>
    {
        SlaveT convert(MasterT master);
    }

    /**
     * TODO
     *
     * @param <SlaveT>
     * @param <MasterKeyT>
     * @param <MasterValueT>
     */
    public interface MapConverter<SlaveT, MasterKeyT, MasterValueT>
    {
        SlaveT convert(MasterKeyT masterKey, MasterValueT masterValueT);
    }
}
