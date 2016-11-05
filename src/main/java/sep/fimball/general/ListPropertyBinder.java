package sep.fimball.general;

import javafx.beans.property.ListProperty;
import javafx.collections.ListChangeListener;
import javafx.collections.MapChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;

import java.util.Map;

/**
 * Created by kaira on 03.11.2016.
 */
public class ListPropertyBinder
{
    public static <SlaveT, MasterT> void bindList(ListProperty<SlaveT> listPropertySlave, ObservableList<MasterT> listPropertyMaster, Converter<SlaveT, MasterT> converter)
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

    public static <SlaveT, MasterKeyT, MasterValueT> void bindMap(ListProperty<SlaveT> listPropertySlave, ObservableMap<MasterKeyT, MasterValueT> MapPropertyMaster, Converter<SlaveT, MasterValueT> converter)
    {
        MapChangeListener<MasterKeyT, MasterValueT> listChangeListener = (change) ->
        {
            listPropertySlave.clear();

            for(Map.Entry<MasterKeyT, MasterValueT> masterEntry : MapPropertyMaster.entrySet())
            {
                listPropertySlave.add(converter.convert(masterEntry.getValue()));
            }
        };

        MapPropertyMaster.addListener(listChangeListener);
        listChangeListener.onChanged(null);
    }

    public interface Converter<A, B>
    {
        A convert(B b);
    }
}
