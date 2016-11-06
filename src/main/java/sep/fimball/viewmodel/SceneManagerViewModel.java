package sep.fimball.viewmodel;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.input.KeyEvent;
import sep.fimball.viewmodel.dialog.DialogViewModel;
import sep.fimball.viewmodel.dialog.none.NoneViewModel;
import sep.fimball.viewmodel.window.WindowViewModel;
import sep.fimball.viewmodel.window.mainmenu.MainMenuViewModel;

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

    private ObjectProperty<WindowViewModel> windowViewModel;
    private ObjectProperty<DialogViewModel> dialogViewModel;

    private InputManagerViewModel inputManager;

    private SceneManagerViewModel()
    {
        windowViewModel = new SimpleObjectProperty<>(new MainMenuViewModel());
        dialogViewModel = new SimpleObjectProperty<>(new NoneViewModel());
        inputManager = new InputManagerViewModel();
    }

    public void onKeyEvent(KeyEvent event)
    {
        //inputManager.onKeyEvent(event, windowType.get());
    }

    public void setWindow(WindowViewModel windowViewModel)
    {
        this.windowViewModel.set(windowViewModel);
        this.dialogViewModel.set(null);
    }

    public void setDialog(DialogViewModel dialogViewModel)
    {
        this.dialogViewModel.set(dialogViewModel);
    }

    public ObjectProperty<WindowViewModel> windowViewModelProperty()
    {
        return windowViewModel;
    }

    public ObjectProperty<DialogViewModel> dialogViewModelProperty()
    {
        return dialogViewModel;
    }
}
