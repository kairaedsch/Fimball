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
    public static <ViewModel> void bindListToSimpleBindedParent(Pane parentNode, ObservableList<ViewModel> listPropertyViewModel, ViewType viewType)
    {
        ParentNodeBinder.bindList(parentNode, listPropertyViewModel, viewType, (view, viewModel) ->
        {
            if(view instanceof SimpleBoundToViewModel)
            {
                ((SimpleBoundToViewModel<ViewModel>) view).bindToViewModel(viewModel);
            }
            else
            {
                throw new RuntimeException("View needs to be implement the SimpleBoundToViewModel Interface");
            }
        });
    }

    public static <ViewModel> void bindList(Pane parentNode, ObservableList<ViewModel> listPropertyViewModel, ViewType viewType, ViewAndViewModelCaller<ViewModel> converter)
    {
        ParentNodeBinder.bindList(parentNode, listPropertyViewModel, (viewModel) ->
        {
            SimpleFxmlLoader simpleFxmlLoader = new SimpleFxmlLoader(viewType);
            converter.call(simpleFxmlLoader.getFxController(), viewModel);
            return simpleFxmlLoader.getRootNode();
        });
    }

    public static <ViewModel> void bindList(Pane parentNode, ObservableList<ViewModel> listPropertyViewModel, ViewModelToNodeConverter<ViewModel> viewModelToNodeConverter)
    {
        ListChangeListener<ViewModel> listChangeListener = (change) ->
        {
            parentNode.getChildren().clear();

            for(ViewModel b : listPropertyViewModel)
            {
                parentNode.getChildren().add(viewModelToNodeConverter.convert(b));
            }
        };

        listPropertyViewModel.addListener(listChangeListener);
        listChangeListener.onChanged(null);
    }

    public static <ViewModelKey, ViewModel> void bindMap(Pane parentNode, ObservableMap<ViewModelKey, ViewModel> MapPropertyViewModel, ViewModelToNodeConverter<ViewModel> viewModelToNodeConverter)
    {
        MapChangeListener<ViewModelKey, ViewModel> listChangeListener = (change) ->
        {
            parentNode.getChildren().clear();

            for(Map.Entry<ViewModelKey, ViewModel> b : MapPropertyViewModel.entrySet())
            {
                parentNode.getChildren().add(viewModelToNodeConverter.convert(b.getValue()));
            }
        };

        MapPropertyViewModel.addListener(listChangeListener);
        listChangeListener.onChanged(null);
    }

    public interface ViewModelToNodeConverter<ViewModel>
    {
        Node convert(ViewModel viewModel);
    }

    public interface ViewAndViewModelCaller<ViewModel>
    {
        void call(Object view, ViewModel viewModel);
    }
}
