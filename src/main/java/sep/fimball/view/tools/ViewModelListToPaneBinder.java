package sep.fimball.view.tools;

import javafx.beans.property.ReadOnlyIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.layout.Pane;
import sep.fimball.general.util.ListPropertyConverter;
import sep.fimball.view.ViewBoundToViewModel;
import sep.fimball.view.ViewType;

/**
 * ViewModelListToPaneBinder stellt Funktionen zum Binden einer Liste von ViewModels an ein Pane-Node bereit, sodass für jedes Element in der Liste ein Kinder-Node im Pane-Node eingefügt wird.
 */
public class ViewModelListToPaneBinder
{
    /**
     * Bindet die Einträge aus der {@code viewModelList} an die {@code parentNode} unter Nutzung der in der {@code viewType} beschriebenen fxml-Datei, die die View als fx::Controller eingetragen hat. Hierbei muss die View zwingend das ViewBoundToViewModel-Interface implementieren.
     *
     * @param parentNode    Die Node, an die die Einträge aus der {@code viewModelList} gehängt und gebunden werden sollen.
     * @param viewModelList Die Liste, deren Einträge an die {@code parentNode} gehängt werden sollen.
     * @param viewType      Der Typ der View der zu anhängenden Einträge.
     * @param <ViewModelT>  Das ViewModel der Einträge in {@code viewModelList}.
     */
    public static <ViewModelT> void bindViewModelsToViews(Pane parentNode, ObservableList<ViewModelT> viewModelList, ViewType viewType)
    {
        ViewModelListToPaneBinder.<ViewBoundToViewModel<ViewModelT>, ViewModelT>bindViewModelsToViews(parentNode, viewModelList, viewType, ViewBoundToViewModel::setViewModel);
    }

    /**
     * Bindet eine View vom Typ {@code viewType} {@code amount} mal an einen {@code parentNode}.
     *
     * @param parentNode Der Node, an der die Views gehängt werden sollen.
     * @param amount     Wie oft die View an den Node gehängt werden sollen.
     * @param viewType   Der Typ der View welche an den Node gehängt werden soll.
     * @param <ViewT>    Die generische View, die vom Typ {@code viewType} ist.
     */
    public static <ViewT> void bindAmountToViews(Pane parentNode, ReadOnlyIntegerProperty amount, ViewType viewType)
    {
        ChangeListener<? super Number> changeListener = ((observable, oldValue, newValue) ->
        {
            parentNode.getChildren().clear();

            for (int i = 0; i < newValue.intValue(); i++)
            {
                ViewLoader<ViewT> viewLoader = new ViewLoader<>(viewType);
                parentNode.getChildren().add(viewLoader.getRootNode());
            }
        });

        amount.addListener(changeListener);
        changeListener.changed(null, null, amount.get());
    }

    /**
     * Bindet die Einträge aus der {@code viewModelList} an die {@code parentNode} unter Nutzung der in der {@code viewType} beschriebenen fxml-Datei, die die View als fx::Controller eingetragen hat. Mithilfe des {@code caller} kann dann bei der Node- und View-Generierung beliebige Logik mit der View und dem ViewModel ausgeführt werden.
     *
     * @param parentNode    Die Node, an die die Einträge aus der {@code viewModelList} gehängt und gebunden werden sollen.
     * @param viewModelList Die Liste, deren Einträge an die {@code parentNode} gehängt werden sollen.
     * @param viewType      Der Typ der View der zu anhängenden Einträge.
     * @param caller        Caller, der beliebige Logik mit der View und dem ViewModel ausführt.
     * @param <ViewT>       Die View der Listeneinträge, die vom Typ {@code viewType} ist.
     * @param <ViewModelT>  Das ViewModel der Einträge in {@code viewModelList}.
     */
    private static <ViewT, ViewModelT> void bindViewModelsToViews(Pane parentNode, ObservableList<ViewModelT> viewModelList, ViewType viewType, ViewAndViewModelCaller<ViewT, ViewModelT> caller)
    {
        ListPropertyConverter.bindAndConvertList(parentNode.getChildren(), viewModelList, (viewModel) ->
        {
            ViewLoader<ViewT> viewLoader = new ViewLoader<>(viewType);
            caller.call(viewLoader.getView(), viewModel);
            return viewLoader.getRootNode();
        });
    }

    /**
     * Das ViewAndViewModelCaller-Interface stellt eine Methode zur Verfügung, in der sowohl das View-Objekt als auch das ViewModel-Objekt als Parameter übergeben werden. In dem Aufruf kann man dann beliebigen Code auf den Objekten ausführen, wie zum Beispiel das ViewModel in die View injizieren.
     *
     * @param <ViewT>      Die zu manipulierende View.
     * @param <ViewModelT> Das zu manipulierende ViewModel.
     */
    public interface ViewAndViewModelCaller<ViewT, ViewModelT>
    {
        /**
         * Erlaubt das manipulieren der View und des ViewModels, wie zum Beispiel das Injizieren des ViewModel in die View.
         *
         * @param view      Die zu manipulierende View.
         * @param viewModel Das zu manipulierende ViewModel.
         */
        void call(ViewT view, ViewModelT viewModel);
    }
}
