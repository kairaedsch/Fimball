package sep.fimball.viewmodel;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.input.KeyEvent;
import sep.fimball.viewmodel.dialog.DialogType;
import sep.fimball.viewmodel.window.WindowType;

/**
 * Created by kaira on 01.11.2016.
 */
public class SceneManagerViewModel
{
    private static SceneManagerViewModel singletonInstance;

    public static SceneManagerViewModel getInstance()
    {
        if(singletonInstance == null) singletonInstance = new SceneManagerViewModel();
        return singletonInstance;
    }

    private ObjectProperty<WindowType> windowType;
    private ObjectProperty<DialogType> dialogType;

    private ViewModel windowViewModel;
    private ViewModel dialogViewModel;

    private InputManagerViewModel inputManager;

    private SceneManagerViewModel()
    {
        windowType = new SimpleObjectProperty<WindowType>(WindowType.MAIN_MENU);
        dialogType = new SimpleObjectProperty<DialogType>(DialogType.NONE);
        inputManager = new InputManagerViewModel();
    }

    public void onKeyEvent(KeyEvent event)
    {
        inputManager.onKeyEvent(event, windowType.get());
    }

    public void setWindow(WindowType WindowTypeNew, ViewModel viewModel)
    {
        windowViewModel = viewModel;
        windowType.set(WindowTypeNew);

        dialogViewModel = null;
        dialogType.set(DialogType.NONE);
    }

    public void setDialog(DialogType dialogtypeNew, ViewModel viewModel)
    {
        dialogViewModel = viewModel;
        dialogType.set(dialogtypeNew);
    }

    public ReadOnlyObjectProperty<WindowType> windowTypeProperty()
    {
        return windowType;
    }

    public ReadOnlyObjectProperty<DialogType> dialogTypeProperty()
    {
        return dialogType;
    }

    public ViewModel getWindowViewModel()
    {
        return windowViewModel;
    }

    public ViewModel getDialogViewModel()
    {
        return dialogViewModel;
    }
}
