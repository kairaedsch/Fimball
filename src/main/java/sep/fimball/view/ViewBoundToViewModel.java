package sep.fimball.view;

/**
 * Das ViewBoundToViewModel-Interface wird von Klassen der View implementiert, die an ein einziges ViewModel gebunden sind, das dann durch eine Methode gesetzt werden kann.
 */
public interface ViewBoundToViewModel<ViewModelT>
{
    /**
     * Setzt das ViewModel, das zur View gehören soll und bindet sich an dessen Properties wenn nötig.
     *
     * @param viewModelT Das ViewModel, das gesetzt werden soll.
     */
    public void setViewModel(ViewModelT viewModelT);
}
