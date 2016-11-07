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
    public static <ViewModelT> void bindListToSimpleBoundParent(Pane parentNode, ObservableList<ViewModelT> listPropertyViewModel, ViewType viewType)
    {
        ParentNodeBinder.<ViewBoundToViewModel<ViewModelT>, ViewModelT>bindList(parentNode, listPropertyViewModel, viewType, ViewBoundToViewModel::setViewModel);
    }

    public static <ViewT, ViewModelT> void bindList(Pane parentNode, ObservableList<ViewModelT> listPropertyViewModel, ViewType viewType, ViewAndViewModelCaller<ViewT, ViewModelT> caller)
    {
        ParentNodeBinder.bindList(parentNode, listPropertyViewModel, (viewModel) ->
        {
            ViewLoader<ViewT> viewLoader = new ViewLoader<>(viewType);
            caller.call(viewLoader.getView(), viewModel);
            return viewLoader.getRootNode();
        });
    }

    public static <ViewModelT> void bindList(Pane parentNode, ObservableList<ViewModelT> listPropertyViewModel, ViewModelToNodeConverter<ViewModelT> viewModelToNodeConverter)
    {
        ListChangeListener<ViewModelT> listChangeListener = (change) ->
        {
            parentNode.getChildren().clear();

            for(ViewModelT viewModel : listPropertyViewModel)
            {
                parentNode.getChildren().add(viewModelToNodeConverter.convert(viewModel));
            }
        };

        listPropertyViewModel.addListener(listChangeListener);
        listChangeListener.onChanged(null);
    }

    public static <ViewModelKeyT, ViewModelT> void bindMap(Pane parentNode, ObservableMap<ViewModelKeyT, ViewModelT> MapPropertyViewModel, ViewModelToNodeConverter<ViewModelT> viewModelToNodeConverter)
    {
        MapChangeListener<ViewModelKeyT, ViewModelT> listChangeListener = (change) ->
        {
            parentNode.getChildren().clear();

            for(Map.Entry<ViewModelKeyT, ViewModelT> b : MapPropertyViewModel.entrySet())
            {
                parentNode.getChildren().add(viewModelToNodeConverter.convert(b.getValue()));
            }
        };

        MapPropertyViewModel.addListener(listChangeListener);
        listChangeListener.onChanged(null);
    }

    public interface ViewModelToNodeConverter<ViewModelT>
    {
        Node convert(ViewModelT viewModel);
    }

    public interface ViewAndViewModelCaller<ViewT, ViewModelT>
    {
        void call(ViewT view, ViewModelT viewModel);
    }
}
