package sep.fimball.view;

/**
 * Das ViewBoundToViewModel-Interface wird von Klassen der View implementiert, damit diese automatisch an ein ViewModel gebunden werden können.
 *
 * @param <ViewModelT> Die Klasse des ViewModels. Hierbei ist zu beachten, dass das ViewModelT nicht eine Unterklasse von sep.fimball.viewmodel.ViewModel sein muss, da:
 *                     <br/>1. SubViewModels wie zum Beispiel sep.fimball.viewmodel.PinballMachineInfoSubViewModel nicht von sep.fimball.viewmodel.ViewModel erben, da sie nur "Sub" sinds.
 *                     <br/>2. Auch primitivere Klassen wie sep.fimball.general.data.highscore als ViewModel agieren können.
 */
public interface ViewBoundToViewModel<ViewModelT>
{
    /**
     * Setzt das ViewModel, das zur dieser View gehören soll, sodass sich die View z.B. an die Properties des ViewModels binden kann.
     *
     * @param viewModelT Das ViewModel, das gesetzt werden soll.
     */
    void setViewModel(ViewModelT viewModelT);
}
