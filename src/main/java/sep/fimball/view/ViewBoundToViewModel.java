package sep.fimball.view;

/**
 * Das ViewBoundToViewModel-Interface wird von Klassen der View implementiert, die an ein einziges ViewModel gebunden sind, das dann durch eine Methode gesetzt werden kann.
 */
public interface ViewBoundToViewModel<ViewModelT>
{
    /**
     * Injiziert das ViewModel.
     *
     * @param viewModelT Das ViewModel, das gesetzt werden soll.
     */
    public void setViewModel(ViewModelT viewModelT);
}
