package sep.fimball.viewmodel;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.input.KeyEvent;
import sep.fimball.model.blueprint.settings.Settings;
import sep.fimball.viewmodel.dialog.DialogType;
import sep.fimball.viewmodel.dialog.DialogViewModel;
import sep.fimball.viewmodel.dialog.none.EmptyViewModel;
import sep.fimball.viewmodel.window.SplashScreenViewModel;
import sep.fimball.viewmodel.window.WindowViewModel;

/**
 * Das SceneManagerViewModel steuert die View und bestimmt, welches Fenster und
 * welcher Dialog angezeigt werden, indem das ViewModel an die View übergeben
 * wird. Es gewährleistet weiterhin, dass Nutzereingaben problemlos an das Model
 * weitergeleitet werden können.
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
     * Gibt an, ob das Fenster im Vollbildmodus dargestellt werden soll.
     */
    private BooleanProperty fullscreen;

    /**
     * Erstellt ein neues SceneManagerViewModel, das ein MainMenuViewModel 
     * verwaltet.
     */
    public SceneManagerViewModel()
    {
        windowViewModel = new SimpleObjectProperty<>();
        dialogViewModel = new SimpleObjectProperty<>();
        fullscreen = new SimpleBooleanProperty();
        setWindow(new SplashScreenViewModel());
        setDialog(new EmptyViewModel());
        fullscreen.bind(Settings.getSingletonInstance().fullscreenProperty());
    }

    /**
     * Leitet das Key-Event an das aktuell aktive WindowViewModel bzw.
     * DialogViewModel weiter.
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
     * Ändert das aktuelle WindowViewModel auf das, das der Methode übergeben
     * wird und löscht den aktuell offenen Dialog, falls vorhanden.
     *
     * @param windowViewModel Das neue WindowViewModel.
     */
    public void setWindow(WindowViewModel windowViewModel)
    {
        windowViewModel.setSceneManager(this);
        this.windowViewModel.set(windowViewModel);
        this.windowViewModel.get().changeBackgroundMusic();
        this.dialogViewModel.set(new EmptyViewModel());
    }

    /**
     * Ändert das aktuelle DialogViewModel auf das, das der Methode übergeben
     * wird.
     *
     * @param dialogViewModel Das neue DialogViewModel.
     */
    public void setDialog(DialogViewModel dialogViewModel)
    {
        dialogViewModel.setSceneManager(this);
        this.dialogViewModel.set(dialogViewModel);
        this.dialogViewModel.get().changeBackgroundMusic();
    }

    /**
     * Stellt das aktuelle WindowViewModel als ObjectProperty zur Verfügung.
     *
     * @return Das aktuelle WindowViewModel.
     */
    public ObjectProperty<WindowViewModel> windowViewModelProperty()
    {
        return windowViewModel;
    }

    /**
     * Stellt das aktuelle DialogViewModel als ObjectProperty zur Verfügung.
     *
     * @return Das aktuelle DialogViewModel.
     */
    public ObjectProperty<DialogViewModel> dialogViewModelProperty()
    {
        return dialogViewModel;
    }

    /**
     * Stellt der View die Information zu Verfügung, ob das Programm im Vollbildmodus dargstellt werden soll.
     * @return {@code true}, wenn das Programm im Vollbildmodus gestartet werden soll, {@code false} sonst.
     */
    public BooleanProperty fullscreenProperty() {
        return fullscreen;
    }
}
