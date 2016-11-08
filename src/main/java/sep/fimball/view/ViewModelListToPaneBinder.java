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
public class ViewModelListToPaneBinder
{
    public static <ViewModelT> void bindViewModelsToViews(Pane parentNode, ObservableList<ViewModelT> viewModelList, ViewType viewType)
    {
        ViewModelListToPaneBinder.<ViewBoundToViewModel<ViewModelT>, ViewModelT>bindViewModelsToViews(parentNode, viewModelList, viewType, ViewBoundToViewModel::setViewModel);
    }

    public static <ViewT, ViewModelT> void bindViewModelsToViews(Pane parentNode, ObservableList<ViewModelT> viewModelList, ViewType viewType, ViewAndViewModelCaller<ViewT, ViewModelT> caller)
    {
        ViewModelListToPaneBinder.bindViewModelsToViews(parentNode, viewModelList, (viewModel) ->
        {
            ViewLoader<ViewT> viewLoader = new ViewLoader<>(viewType);
            caller.call(viewLoader.getView(), viewModel);
            return viewLoader.getRootNode();
        });
    }

    public static <ViewModelT> void bindViewModelsToViews(Pane parentNode, ObservableList<ViewModelT> viewModelList, ViewModelToNodeConverter<ViewModelT> viewModelToNodeConverter)
    {
        ListChangeListener<ViewModelT> listChangeListener = (change) ->
        {
            parentNode.getChildren().clear();

            for(ViewModelT viewModel : viewModelList)
            {
                parentNode.getChildren().add(viewModelToNodeConverter.convert(viewModel));
            }
        };

        viewModelList.addListener(listChangeListener);
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
