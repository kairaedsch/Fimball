package sep.fimball.general.util;

import javafx.collections.ListChangeListener;
import javafx.collections.MapChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kaira on 24.01.2017.
 */
public class CleanAble
{
    private List<RemoveAble> listBindings;

    public CleanAble()
    {
        listBindings = new ArrayList<>();
    }

    public <T> void addListBindingForCleanUp(ObservableList<? extends T> listProperty, ListChangeListener<T> changeListener)
    {
        listBindings.add(new ListBinding<>(listProperty, changeListener));
    }

    public <K, T> void addMapBindingForCleanUp(ObservableMap<K, T> mapProperty, MapChangeListener<K, T> changeListener)
    {
        listBindings.add(new MapBinding<>(mapProperty, changeListener));
    }

    public void cleanUp()
    {
        for (RemoveAble removeAble : listBindings)
        {
            removeAble.remove();
        }
    }

    class MapBinding<K, T> implements RemoveAble
    {
        ObservableMap<K, T> mapProperty;

        MapChangeListener<K, T> changeListener;

        public MapBinding(ObservableMap<K, T> mapProperty, MapChangeListener<K, T> changeListener)
        {
            this.mapProperty = mapProperty;
            this.changeListener = changeListener;
        }

        public void remove()
        {
            mapProperty.removeListener(changeListener);
        }
    }

    class ListBinding<T> implements RemoveAble
    {
        ObservableList<? extends T> listProperty;

        ListChangeListener<T> changeListener;

        public ListBinding(ObservableList<? extends T> listProperty, ListChangeListener<T> changeListener)
        {
            this.changeListener = changeListener;
            this.listProperty = listProperty;
        }

        public void remove()
        {
            listProperty.removeListener(changeListener);
        }
    }

    interface RemoveAble
    {
        void remove();
    }
}
