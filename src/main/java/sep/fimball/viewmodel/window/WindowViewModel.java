package sep.fimball.viewmodel.window;

import sep.fimball.viewmodel.ViewModel;

/**
 * Created by kaira on 06.11.2016.
 */
public abstract class WindowViewModel extends ViewModel
{
    private WindowType windowType;

    public WindowViewModel(WindowType windowType)
    {
        this.windowType = windowType;
    }

    public WindowType getWindowType()
    {
        return windowType;
    }
}
