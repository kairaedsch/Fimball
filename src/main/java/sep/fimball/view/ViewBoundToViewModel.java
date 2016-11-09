package sep.fimball.view;

/**
 * Das ViewBoundToViewModel-Interface wird von Klassen der View implementiert, welche an ein einziges ViewModel gebunden sind, welches dann durch eine Methode gesetzt werden kann.
 */
public interface ViewBoundToViewModel<ViewModelT>
{
    /**
     * Injeziert das ViewModel.
     * @param viewModelT
     */
    public void setViewModel(ViewModelT viewModelT);
}
