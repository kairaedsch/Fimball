package sep.fimball.viewmodel;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.input.KeyEvent;
import sep.fimball.viewmodel.dialog.DialogViewModel;
import sep.fimball.viewmodel.dialog.none.EmptyViewModel;
import sep.fimball.viewmodel.window.WindowViewModel;
import sep.fimball.viewmodel.window.mainmenu.MainMenuViewModel;

/**
 * TODO längerer Beschreibung
 * Das SceneManagerViewModel steuert die View und bestimmt, welches Window und welcher Dialog angezeigt werden, indem das Viewmodel an die View übergeben wird.
 */
public class SceneManagerViewModel
{
    /**
     * Das aktuelle WindowViewModel.
     */
    private ObjectProperty<WindowViewModel> windowViewModel;

    /**
     * Das aktuelle DialogViewModel.
     */
    private ObjectProperty<DialogViewModel> dialogViewModel;

    /**
     * Der Inputmanager, welcher Inputevents von der View entgegen nimmt.
     */
    private InputManagerViewModel inputManager;

    /**
     * Erstellt ein neues SceneManagerViewModel.
     */
    public SceneManagerViewModel()
    {
        windowViewModel = new SimpleObjectProperty<>();
        dialogViewModel = new SimpleObjectProperty<>();
        setWindow(new MainMenuViewModel());
        setDialog(new EmptyViewModel());
        inputManager = new InputManagerViewModel();
    }

    /**
     * Leitet ein Key-Event an den inputManager weiter.
     * @param event
     */
    public void onKeyEvent(KeyEvent event)
    {
        inputManager.onKeyEvent(event, windowViewModel.get().getWindowType());
    }

    /**
     * 
     * @param windowViewModel
     */
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
