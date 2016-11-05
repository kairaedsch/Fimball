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

    public ReadOnlyObjectProperty<WindowType> getWindowTypeProperty()
    {
        return windowType;
    }

    public ReadOnlyObjectProperty<DialogType> getDialogTypeProperty()
    {
        return dialogType;
    }

    public void setWindow(WindowType WindowTypeNew)
    {
        dialogType.set(DialogType.NONE);
        windowType.set(WindowTypeNew);
    }

    public void setDialog(DialogType dialogtypeNew)
    {
        dialogType.set(dialogtypeNew);
    }

    public void setWindowAndDialog(WindowType WindowTypeNew, DialogType dialogtypeNew)
    {
        windowType.set(WindowTypeNew);
        dialogType.set(dialogtypeNew);
    }
}
