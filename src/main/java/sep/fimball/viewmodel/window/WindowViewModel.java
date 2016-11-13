package sep.fimball.viewmodel.window;

import sep.fimball.viewmodel.ViewModel;

/**
 * Das WindowViewModel ist die Oberklasse aller ViewModel, die Fenster repräsentieren, und speichert den Window-Typ.
 */
public abstract class WindowViewModel extends ViewModel
{
    /**
     * Der Typ des WindowViewModel.
     */
    private WindowType windowType;

    /**
     * Erstellt ein neues WindowViewModel.
     *
     * @param windowType Der Typ des WindowViewModels.
     */
    public WindowViewModel(WindowType windowType)
    {
        this.windowType = windowType;
    }

    /**
     * Gibt den Typ dieses Objekts zurück.
     *
     * @return Der Typ dieses WindowViewModels.
     */
    public WindowType getWindowType()
    {
        return windowType;
    }
}
