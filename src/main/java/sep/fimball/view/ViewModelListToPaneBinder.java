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
     * Bindet die Einträge aus der {@code viewModelList} an die {@code parentNode} unter Nutzung der in dem {@code viewType} beschriebenen fxml-Datei, welche die View als fx::Controller eingetragen hat. Hierbei muss die View zwingend das ViewBoundToViewModel-Interface implementieren.
     * @param parentNode Die Node, an die die Einträge aus der {@code viewModelList} gehängt und gebunden werden sollen.
     * @param viewModelList Die Liste, deren Einträge an die {@code parentNode} gehängt werden sollen.
     * @param viewType Der Typ der View der zu anhängenden Einträge.
     * @param <ViewModelT> Das ViewModel der Einträge in {@code viewModelList}.
     */
    public static <ViewModelT> void bindViewModelsToViews(Pane parentNode, ObservableList<ViewModelT> viewModelList, ViewType viewType)
    {
        ViewModelListToPaneBinder.<ViewBoundToViewModel<ViewModelT>, ViewModelT>bindViewModelsToViews(parentNode, viewModelList, viewType, ViewBoundToViewModel::setViewModel);
    }

    /**
     * Bindet die Einträge aus der {@code viewModelList} an die {@code parentNode} unter Nutzung der in dem {@code viewType} beschriebene fxml-Datei, welche die View als fx::Controller eingetragen hat. Mithilfe des {@code caller} kann dann bei der Node- und ViewGenerierung beliebige Logik mit der View und dem ViewModel ausgeführt werden.
     * @param parentNode Die Node, an die die Einträge aus der {@code viewModelList} gehängt und gebunden werden sollen.
     * @param viewModelList Die Liste, deren Einträge an die {@code parentNode} gehängt werden sollen.
     * @param viewType Der Typ der View der zu anhängenden Einträge.
     * @param caller Caller, der beliebige Logik mit der View und dem ViewModel ausgeführt.
     * @param <ViewT> Die View  der Listeneinträge, die vom Typ {@code viewType} ist.
     * @param <ViewModelT> Das ViewModel der Einträge in {@code viewModelList}.
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
     * Bindet die Einträge aus der @code viewModelList} an die {@code parentNode}, indem einzelne Nodes mithilfe des {@code viewModelToNodeConverter} erzeugt werden.
     * @param parentNode Die Node, an die die Einträge aus der {@code viewModelList} gehängt und gebunden werden sollen.
     * @param viewModelList Die Liste, deren Einträge an die {@code parentNode} gehängt werden sollen.
     * @param viewModelToNodeConverter Der Converter, mit dessen Hilfe die einzelnen Nodes erzeugt werden.
     * @param <ViewModelT> Das ViewModel der Einträge in {@code viewModelList}.
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
     * @param parentNode Die Node, an die die Einträge aus der {@code viewModelList} gehängt und gebunden werden sollen.
     * @param MapPropertyViewModel Die Map, deren Einträge an die {@code parentNode} gehängt werden sollen.
     * @param viewModelToNodeConverter  Der Converter, mit dessen Hilfe die einzelnen Nodes erzeugt werden.
     * @param <ViewModelKeyT> TODO
     * @param <ViewModelT> Das ViewModel der Einträge in {@code MapPropertyViewModel}.
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
     * @param <ViewModelT>  Das ViewModel, aus dem die Node erstellt werden soll.
     */
    public interface ViewModelToNodeConverter<ViewModelT>
    {
        /**
         * Erzeugt ein Node aus einem ViewModel und gibt das Node-Objekt zurück.
         * @param viewModel Das ViewModel, aus dem die Node erstellt werden soll.
         * @return Die erzeugte Node.
         */
        Node convert(ViewModelT viewModel);
    }

    /**
     * Das ViewAndViewModelCaller-Interface stellt eine Methode zu Verfügung, in der sowohl das View-Objekt als auch das ViewModel-Objekt als Parameter übergeben werden. In dem Aufruf kann man dann beliebiges mit den Objekten mache, wie zum Beispiel das ViewModel in die View injezieren.
     * @param <ViewT> Die zu manipulierende View.
     * @param <ViewModelT> Das zu manipulierende ViewModel.
     */
    public interface ViewAndViewModelCaller<ViewT, ViewModelT>
    {
        /**
         * Erlaubt das manipulieren der View und des ViewModels, wie zum Beispiel das Injezieren des ViewModel in die View.
         * @param view Die zu manipulierende View.
         * @param viewModel Das zu manipulierende ViewModel.
         */
        void call(ViewT view, ViewModelT viewModel);
    }
}
