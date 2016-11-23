package sep.fimball.viewmodel;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.input.KeyEvent;
import sep.fimball.viewmodel.dialog.DialogType;
import sep.fimball.viewmodel.dialog.DialogViewModel;
import sep.fimball.viewmodel.dialog.none.EmptyViewModel;
import sep.fimball.viewmodel.window.WindowViewModel;
import sep.fimball.viewmodel.window.mainmenu.MainMenuViewModel;

/**
 * Das SceneManagerViewModel steuert die View und bestimmt, welches Fenster und welcher Dialog angezeigt werden, indem das ViewModel an die View übergeben wird. Es gewährleistet weiterhin, dass Nutzereingaben problemlos an das Model weitergeleitet werden können.
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
     * Erstellt ein neues SceneManagerViewModel.
     */
    public SceneManagerViewModel()
    {
        windowViewModel = new SimpleObjectProperty<>();
        dialogViewModel = new SimpleObjectProperty<>();
        setWindow(new MainMenuViewModel());
        setDialog(new EmptyViewModel());
    }

    /**
     * Leitet das Key-Event an das zuständige WindowViewModel bzw. DialogViewModel weiter.
     *
     * @param event Das Key-Event, das weitergeleitet werden soll.
     */
    public void onKeyEvent(KeyEvent event)
    {
        if (dialogViewModel.get().getDialogType() == DialogType.NONE)
        {
            windowViewModel.get().handleKeyEvent(event);
        }
        else
        {
            dialogViewModel.get().handleKeyEvent(event);
        }
    }

    /**
     * Ändert das aktuelle WindowViewModel auf das, das der Methode übergeben wird.
     *
     * @param windowViewModel Das neue WindowViewModel.
     */
    public void setWindow(WindowViewModel windowViewModel)
    {
        windowViewModel.setSceneManager(this);
        this.windowViewModel.set(windowViewModel);
        this.dialogViewModel.set(new EmptyViewModel());
    }

    /**
     * Ändert das aktuelle DialogViewModel auf das, das der Methode übergeben wird.
     *
     * @param dialogViewModel Das neue DialogViewModel.
     */
    public void setDialog(DialogViewModel dialogViewModel)
    {
        dialogViewModel.setSceneManager(this);
        this.dialogViewModel.set(dialogViewModel);
    }

    /**
     * Stellt der View das aktuelle WindowViewModel zur Verfügung.
     *
     * @return Das aktuelle WindowViewModel.
     */
    public ObjectProperty<WindowViewModel> windowViewModelProperty()
    {
        return windowViewModel;
    }

    /**
     * Stellt der View das aktuelle DialogViewModel zur Verfügung.
     *
     * @return Das aktuelle DialogViewModel.
     */
    public ObjectProperty<DialogViewModel> dialogViewModelProperty()
    {
        return dialogViewModel;
    }
}
