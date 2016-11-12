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
     * @param windowType Der Typ des WindowViewModels.
     */
    public WindowViewModel(WindowType windowType)
    {
        this.windowType = windowType;
    }

    /**
     * Gibt den Window-Typ das DialogViewModels zurück.
     * @return Der Typ des WindowViewModels.
     */
    public WindowType getWindowType()
    {
        return windowType;
    }
}
