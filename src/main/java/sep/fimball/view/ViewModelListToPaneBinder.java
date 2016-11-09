package sep.fimball.view;

import javafx.collections.ListChangeListener;
import javafx.collections.MapChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.scene.Node;
import javafx.scene.layout.Pane;

import java.util.Map;

/**
 * ViewModelListToPaneBinder stellt Funktionen zum Binden einer Liste von ViewModels an ein Pane-Node bereit, sodass für jedes Element in der Liste ein Kinder-Node im Pane-Node eingefügt wird.
 */
public class ViewModelListToPaneBinder
{
    /**
     * Bindet die Einträge aus dem listPropertViewModel an die parent Node unter Nutzung der in dem viewType beschriebene fxml-Datei, welche die View als fx::Controller eingetragen hat. Hierbei muss die View zwingend das ViewBoundToViewModel-Interface implementieren.
     * @param parentNode
     * @param viewModelList
     * @param viewType
     * @param <ViewModelT>
     */
    public static <ViewModelT> void bindViewModelsToViews(Pane parentNode, ObservableList<ViewModelT> viewModelList, ViewType viewType)
    {
        ViewModelListToPaneBinder.<ViewBoundToViewModel<ViewModelT>, ViewModelT>bindViewModelsToViews(parentNode, viewModelList, viewType, ViewBoundToViewModel::setViewModel);
    }

    /**
     * Bindet die Einträge aus dem listPropertyViewModel an die parent Node unter Nutzung der in dem viewType beschriebene fxml-Datei, welche die View als fx::Controller eingetragen hat. Mithilfe des callers kann dann bei der Node- und ViewGenerierung beliebige Logik mit der View und dem ViewModel ausgeführt werden.
     * @param parentNode
     * @param viewModelList
     * @param viewType
     * @param caller
     * @param <ViewT>
     * @param <ViewModelT>
     */
    public static <ViewT, ViewModelT> void bindViewModelsToViews(Pane parentNode, ObservableList<ViewModelT> viewModelList, ViewType viewType, ViewAndViewModelCaller<ViewT, ViewModelT> caller)
    {
        ViewModelListToPaneBinder.bindViewModelsToViews(parentNode, viewModelList, (viewModel) ->
        {
            ViewLoader<ViewT> viewLoader = new ViewLoader<>(viewType);
            caller.call(viewLoader.getView(), viewModel);
            return viewLoader.getRootNode();
        });
    }

    /**
     * Bindet die Einträge aus dem listPropertyViewModel an die parentNode, indem einzelne Nodes mithilfe des viewModelToNodeConverter erzeugt werden.
     * @param parentNode
     * @param viewModelList
     * @param viewModelToNodeConverter
     * @param <ViewModelT>
     */
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

    /**
     * Bindet die Einträge aus dem MapPropertyViewModel an die parentNode, indem einzelne Nodes mithilfe des viewModelToNodeConverter erzeugt werden.
     * @param parentNode
     * @param MapPropertyViewModel
     * @param viewModelToNodeConverter
     * @param <ViewModelKeyT>
     * @param <ViewModelT>
     */
    public static <ViewModelKeyT, ViewModelT> void bindViewModelsToViews(Pane parentNode, ObservableMap<ViewModelKeyT, ViewModelT> MapPropertyViewModel, ViewModelToNodeConverter<ViewModelT> viewModelToNodeConverter)
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

    /**
     * Das ViewModelToNodeConverter-Interface ermöglicht es, ein Node aus einem ViewModel-Object zu erstellen.
     * @param <ViewModelT>
     */
    public interface ViewModelToNodeConverter<ViewModelT>
    {
        /**
         * Erzeugt ein Node aus einem ViewModel und gibt das Node-Objekt zurück.
         * @param viewModel
         * @return
         */
        Node convert(ViewModelT viewModel);
    }

    /**
     * Das ViewAndViewModelCaller-Interface stellt eine Methode zu Verfügung, in der sowohl das View-Objekt als auch das ViewModel-Objekt als Parameter übergeben werden. In dem Aufruf kann man dann beliebiges mit den Objekten mache, wie zum Beispiel das ViewModel in die View injezieren.
     * @param <ViewT>
     * @param <ViewModelT>
     */
    public interface ViewAndViewModelCaller<ViewT, ViewModelT>
    {
        /**
         * Erlaubt das manipulieren der View und des ViewModels, wie zum Beispiel das Injezieren des ViewModel in die View.
         * @param view
         * @param viewModel
         */
        void call(ViewT view, ViewModelT viewModel);
    }
}
