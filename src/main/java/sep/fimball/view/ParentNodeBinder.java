package sep.fimball.view;

import javafx.collections.ListChangeListener;
import javafx.collections.MapChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.scene.Node;
import javafx.scene.layout.Pane;

import java.util.Map;

/**
 * Created by kaira on 05.11.2016.
 */
public class ParentNodeBinder
{
    public static <B> void bindListToSimpleBindedParent(Pane parentNode, ObservableList<B> listPropertyB, ViewType viewType)
    {
        ParentNodeBinder.bindList(parentNode, listPropertyB, (b) ->
        {
            SimpleFxmlLoader simpleFxmlLoader = new SimpleFxmlLoader(viewType);
            ((SimpleBindedToViewModel<B>) simpleFxmlLoader.getFxController()).bindToViewModel(b);
            return simpleFxmlLoader.getRootNode();
        });
    }

    public static <B> void bindList(Pane parentNode, ObservableList<B> listPropertyB, ViewType viewType, Caller<B> converter)
    {
        ParentNodeBinder.bindList(parentNode, listPropertyB, (b) ->
        {
            SimpleFxmlLoader simpleFxmlLoader = new SimpleFxmlLoader(viewType);
            converter.call(simpleFxmlLoader.getFxController(), b);
            return simpleFxmlLoader.getRootNode();
        });
    }

    public static <B> void bindList(Pane parentNode, ObservableList<B> listPropertyB, Converter<B> converter)
    {
        ListChangeListener<B> listChangeListener = (change) ->
        {
            parentNode.getChildren().clear();

            for(B b : listPropertyB)
            {
                parentNode.getChildren().add(converter.convert(b));
            }
        };

        listPropertyB.addListener(listChangeListener);
        listChangeListener.onChanged(null);
    }

    public static <K, B> void bindMap(Pane parentNode, ObservableMap<K, B> MapPropertyB, Converter<B> converter)
    {
        MapChangeListener<K, B> listChangeListener = (change) ->
        {
            parentNode.getChildren().clear();

            for(Map.Entry<K, B> b : MapPropertyB.entrySet())
            {
                parentNode.getChildren().add(converter.convert(b.getValue()));
            }
        };

        MapPropertyB.addListener(listChangeListener);
        listChangeListener.onChanged(null);
    }

    public interface Converter<B>
    {
        Node convert(B b);
    }

    public interface Caller<B>
    {
        void call(Object view, B b);
    }
}
