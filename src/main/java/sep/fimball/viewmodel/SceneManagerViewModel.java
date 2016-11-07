package sep.fimball.viewmodel;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.input.KeyEvent;
import sep.fimball.viewmodel.dialog.DialogViewModel;
import sep.fimball.viewmodel.dialog.none.EmptyViewModel;
import sep.fimball.viewmodel.window.WindowViewModel;
import sep.fimball.viewmodel.window.mainmenu.MainMenuViewModel;

/**
 * Created by kaira on 01.11.2016.
 */
public class SceneManagerViewModel
{
    private ObjectProperty<WindowViewModel> windowViewModel;
    private ObjectProperty<DialogViewModel> dialogViewModel;

    private InputManagerViewModel inputManager;

    public SceneManagerViewModel()
    {
        windowViewModel = new SimpleObjectProperty<>();
        dialogViewModel = new SimpleObjectProperty<>();
        setWindow(new MainMenuViewModel());
        setDialog(new EmptyViewModel());
        inputManager = new InputManagerViewModel();
    }

    public void onKeyEvent(KeyEvent event)
    {
        inputManager.onKeyEvent(event, windowViewModel.get().getWindowType());
    }

    public void setWindow(WindowViewModel windowViewModel)
    {
        windowViewModel.setSceneManager(this);
        this.windowViewModel.set(windowViewModel);
        this.dialogViewModel.set(null);
    }

    public void setDialog(DialogViewModel dialogViewModel)
    {
        dialogViewModel.setSceneManager(this);
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
