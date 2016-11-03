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
    public static <A, B> void bindList(ListProperty<A> listPropertyA, ObservableList<B> listPropertyB, Converter<A, B> converter)
    {
        ListChangeListener listChangeListener = new ListChangeListener<B>()
        {
            @Override
            public void onChanged(Change<? extends B> change)
            {
                listPropertyA.clear();

                for(B b : listPropertyB)
                {
                    listPropertyA.add(converter.convert(b));
                }
            }
        };

        listPropertyB.addListener(listChangeListener);
        listChangeListener.onChanged(null);
    }

    public static <A, K, B> void bindMap(ListProperty<A> listPropertyA, ObservableMap<K, B> MapPropertyB, Converter<A, B> converter)
    {
        MapChangeListener<K, B> listChangeListener = new MapChangeListener<K, B>()
        {
            @Override
            public void onChanged(Change<? extends K, ? extends B> change)
            {
                listPropertyA.clear();

                for(Map.Entry<K, B> b : MapPropertyB.entrySet())
                {
                    listPropertyA.add(converter.convert(b.getValue()));
                }
            }
        };

        MapPropertyB.addListener(listChangeListener);
        listChangeListener.onChanged(null);
    }

    public interface Converter<A, B>
    {
        A convert(B b);
    }
}
