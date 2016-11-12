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
     * Leitet das Key-Event an den inputManager weiter.
     * @param event Das Key-Event, das weitergeleitet werden soll.
     */
    public void onKeyEvent(KeyEvent event)
    {
        inputManager.onKeyEvent(event, windowViewModel.get().getWindowType());
    }

    /**
     * Ändert das aktuelle WindowViewModel auf ein neuea, welches übergeben wird.
     * @param windowViewModel Das neue WindowViewModel.
     */
    public void setWindow(WindowViewModel windowViewModel)
    {
        windowViewModel.setSceneManager(this);
        this.windowViewModel.set(windowViewModel);
        this.dialogViewModel.set(null);
    }

    /**
     * Ändert das aktuelle DialogViewModel auf ein neues, welches übergeben wird.
     * @param dialogViewModel Das neue DialogViewModel.
     */
    public void setDialog(DialogViewModel dialogViewModel)
    {
        dialogViewModel.setSceneManager(this);
        this.dialogViewModel.set(dialogViewModel);
    }

    /**
     * Stellt das aktuelle WindowViewModel für die View zu Verfügung.
     * @return Das aktuelle WindowViewModel.
     */
    public ObjectProperty<WindowViewModel> windowViewModelProperty()
    {
        return windowViewModel;
    }

    /**
     * Stellt das aktuelle DialogViewModel für die View zu Verfügung.
     * @return Das aktuelle DialogViewModel.
     */
    public ObjectProperty<DialogViewModel> dialogViewModelProperty()
    {
        return dialogViewModel;
    }
}
