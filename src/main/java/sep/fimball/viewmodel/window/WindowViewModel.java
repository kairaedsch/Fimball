package sep.fimball.viewmodel.window;

import sep.fimball.viewmodel.ViewModel;

/**
 * Das WindowViewModel ist die Oberklasse aller Window-ViewModel und speichert den Window-Typ.
 */
public abstract class WindowViewModel extends ViewModel
{
    /**
     * Der Window-Typ des WindowViewModel.
     */
    private WindowType windowType;

    /**
     * Erstellt ein neues WindowViewModel.
     * @param windowType
     */
    public WindowViewModel(WindowType windowType)
    {
        this.windowType = windowType;
    }

    /**
     * Gibt den Window-Typ das DialogViewModels zurück.
     * @return
     */
    public WindowType getWindowType()
    {
        return windowType;
    }
}
