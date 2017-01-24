package sep.fimball.general.util;

import javafx.collections.ListChangeListener;
import javafx.collections.MapChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;

import java.util.ArrayList;
import java.util.List;

/**
 * Diese Klasse speichert ListChangeListener und MapChangeListener und kann diese bei Bedarf von den Maps und Listen, die sie beobachten, entfernen. So können mehrere ChangeListener gemeinsam entfernt werden.
 */
public class CleanAble
{
    /**
     * Die Liste der ChangeListener, die bei Bedarf gemeinsam entfernt werden sollen.
     */
    private List<RemoveAble> listBindings;

    /**
     * Erzeugt eine neue, leere Instanz.
     */
    public CleanAble()
    {
        listBindings = new ArrayList<>();
    }

    /**
     * Fügt einen ListChangeListener zu diesem Objekt hinzu.
     *
     * @param listProperty Die Liste, die {@code changeListener} beobachtet.
     * @param changeListener Der ListChangeListener, der gespeichert werden soll.
     * @param <T> Der Typ der Elemente, die der ListChangeListener beobachtet.
     */
    public <T> void addListBindingForCleanUp(ObservableList<? extends T> listProperty, ListChangeListener<T> changeListener)
    {
        listBindings.add(new ListBinding<>(listProperty, changeListener));
    }

    /**
     * Fügt einen MapChangeListener zu diesem Objekt hinzu.
     *
     * @param mapProperty Die Map, die {@code changeListener} beobachtet.
     * @param changeListener Der MapChangeListener, der gespeichert werden soll.
     * @param <K> Der Typ der Schlüsselwerte der Map.
     * @param <T> Der Typ der Werte der Map.
     */
    public <K, T> void addMapBindingForCleanUp(ObservableMap<K, T> mapProperty, MapChangeListener<K, T> changeListener)
    {
        listBindings.add(new MapBinding<>(mapProperty, changeListener));
    }

    /**
     * Entfernt alle gespeicherten ChangeListener von den Listen und Maps, die sie beobachten.
     */
    public void cleanUp()
    {
        for (RemoveAble removeAble : listBindings)
        {
            removeAble.remove();
        }
    }

    /**
     * Diese Klasse speichert einen MapChangeListener und die Map, die dieser beobachtet und ermöglicht es, den MapChangeListener zu entfernen.
     *
     * @param <K> Der Typ der Schlüsselwerte der Map.
     * @param <T> Der Typ der Werte der Map.
     */
    class MapBinding<K, T> implements RemoveAble
    {
        /**
         * Die Map, die der MapChangeListener beobachtet.
         */
        ObservableMap<K, T> mapProperty;

        /**
         * Der MapChangeListener, der die Map beobachtet.
         */
        MapChangeListener<K, T> changeListener;

        /**
         * Erzeugt ein neues MapBinding.
         *
         * @param mapProperty Die ObservableMap, die {@code changeListener} beobachtet.
         * @param changeListener Der MapChangeListener, der {@code mapProperty} beobachtet.
         */
        public MapBinding(ObservableMap<K, T> mapProperty, MapChangeListener<K, T> changeListener)
        {
            this.mapProperty = mapProperty;
            this.changeListener = changeListener;
        }

        /**
         * {@inheritDoc}
         */
        public void remove()
        {
            mapProperty.removeListener(changeListener);
        }
    }

    /**
     * Diese Klasse speichert einen ListChangeListener zusammen mit der ObservableList, auf der er aktiv ist und ermöglicht es, den ListChangeListener zu einem beliebigen Zeitpunkt zu entfernen.
     *
     * @param <T> Ein Obertyp der Elemente, die sich in der Liste befinden.
     */
    class ListBinding<T> implements RemoveAble
    {
        /**
         * Die Liste, die der ListChangeListener beobachtet.
         */
        ObservableList<? extends T> listProperty;

        /**
         * Der ListChangeListener, der die Liste beobachtet.
         */
        ListChangeListener<T> changeListener;

        /**
         * Erzeugt ein neues ListBinding.
         *
         * @param listProperty Die ObservableList, die {@code changeListener} beobachtet.
         * @param changeListener Der ListChangeListener, der {@code listProperty} beobachtet.
         */
        public ListBinding(ObservableList<? extends T> listProperty, ListChangeListener<T> changeListener)
        {
            this.changeListener = changeListener;
            this.listProperty = listProperty;
        }

        /**
         * {@inheritDoc}
         */
        public void remove()
        {
            listProperty.removeListener(changeListener);
        }
    }

    /**
     * Dieses Interface stellt eine Methode zur Entfernung bestehender ChangeListener bereit.
     */
    interface RemoveAble
    {
        /**
         * Entfernt einen bestehenden ChangeListener.
         */
        void remove();
    }
}
