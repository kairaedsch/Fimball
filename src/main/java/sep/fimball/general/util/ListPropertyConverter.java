package sep.fimball.general.util;

import javafx.beans.property.ListProperty;
import javafx.collections.ListChangeListener;
import javafx.collections.MapChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;

import java.util.Map;

/**
 * Created by kaira on 03.11.2016.
 */
public class ListPropertyConverter
{
    public static <SlaveT, MasterT> void bindAndConvertList(ListProperty<SlaveT> listPropertySlave, ObservableList<MasterT> listPropertyMaster, ListConverter<SlaveT, MasterT> converter)
    {
        ListChangeListener<MasterT> listChangeListener = (change) ->
        {
            listPropertySlave.clear();

            for(MasterT master : listPropertyMaster)
            {
                listPropertySlave.add(converter.convert(master));
            }
        };

        listPropertyMaster.addListener(listChangeListener);
        listChangeListener.onChanged(null);
    }

    public static <SlaveT, MasterKeyT, MasterValueT> void bindAndConvertMap(ListProperty<SlaveT> listPropertySlave, ObservableMap<MasterKeyT, MasterValueT> MapPropertyMaster, MapConverter<SlaveT, MasterKeyT, MasterValueT> converter)
    {
        MapChangeListener<MasterKeyT, MasterValueT> listChangeListener = (change) ->
        {
            listPropertySlave.clear();

            for(Map.Entry<MasterKeyT, MasterValueT> masterEntry : MapPropertyMaster.entrySet())
            {
                listPropertySlave.add(converter.convert(masterEntry.getKey(), masterEntry.getValue()));
            }
        };

        MapPropertyMaster.addListener(listChangeListener);
        listChangeListener.onChanged(null);
    }

    public interface ListConverter<SlaveT, MasterT>
    {
        SlaveT convert(MasterT master);
    }

    public interface MapConverter<SlaveT, MasterKeyT, MasterValueT>
    {
        SlaveT convert(MasterKeyT masterKey, MasterValueT masterValueT);
    }
}
